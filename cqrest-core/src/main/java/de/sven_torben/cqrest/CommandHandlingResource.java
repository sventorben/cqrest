package de.sven_torben.cqrest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.sven_torben.cqrest.annotations.handler.CommandHandler;
import de.sven_torben.cqrest.annotations.methods.COMMAND;

public abstract class CommandHandlingResource<T> {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Class<? extends T> commandType;

	public CommandHandlingResource(final Class<? extends T> commandType) {
	    this.commandType = Objects.requireNonNull(commandType);
	}
	
	@COMMAND
	@Consumes(MediaTypes.CQREST_COMMAND_JSON)
	public void handleCommandRequest(@Suspended final AsyncResponse asyncResponse, final T command) {

		Executors.newSingleThreadExecutor().submit(() -> {
			try  {
				final Method handler = this.findCommandHandler(command);
				if (handler == null) {
				    this.handleCommand(asyncResponse, command);
				} else {
    				handler.setAccessible(true);
    				handler.invoke(this, asyncResponse, command);
				}
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				this.log.error("Handling command failed!", e); 
				asyncResponse.cancel();
			}
		});
	}

	@OPTIONS
	public Response options() {
		final Collection<? extends Method> cmdMethods = this.findCommandHandlers();
		final Set<CommandInfo> cmdInfos = this.createCommandInfos(cmdMethods);
		return Response.ok(new OptionsInfo(cmdInfos))
				.type(MediaTypes.CQREST_INFO_JSON_MT).build();
	}

	private void handleCommand(final AsyncResponse asyncResponse, final T command) {
		this.log.warn("No command handler registered for " + command.getClass().getName());
		asyncResponse.resume(Response.status(new UnsupportedCommandType(command.getClass())).build());
	}
	
	private Method findCommandHandler(final T command) throws NoSuchMethodException, SecurityException {
		final Optional<? extends Method> handler = this.findCommandHandlers().stream()
				.filter(m -> m.getParameterCount() == 2)
				.filter(m -> m.getParameterTypes()[0].equals(AsyncResponse.class))
				.filter(m -> m.getParameterTypes()[1].isAssignableFrom(command.getClass()))
				.findFirst();
		if (handler.isPresent()) {
			return handler.get();
		}
		return null;
	}
	
	private Set<CommandInfo> createCommandInfos(
			final Collection<? extends Method> cmdHandlers) {
		return cmdHandlers.stream()
		    .flatMap(handler -> Arrays.asList(handler.getParameterTypes()).stream())
		    .filter(paramType -> this.commandType.isAssignableFrom(paramType))
		    .map(commandType -> new CommandInfo(commandType))
		    .collect(Collectors.toSet());
	}
	
	private Collection<? extends Method> findCommandHandlers() {
	    return Arrays.asList(this.getClass().getMethods()).stream()
                .filter(method -> method.isAnnotationPresent(CommandHandler.class))
				.collect(Collectors.toSet());
	}

}