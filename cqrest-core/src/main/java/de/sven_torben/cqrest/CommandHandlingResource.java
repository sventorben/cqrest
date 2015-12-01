package de.sven_torben.cqrest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
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
import de.sven_torben.cqrest.representations.Command;

public abstract class CommandHandlingResource {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@COMMAND
	@Consumes(MediaTypes.CQREST_COMMAND_JSON)
	public void handleCommandRequest(@Suspended final AsyncResponse asyncResponse, final Command command) {

		Executors.newSingleThreadExecutor().submit(() -> {
			try  {
				final Method handler = this.findCommandHandler(command);
				handler.setAccessible(true);
				handler.invoke(this, command, asyncResponse);
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
				this.log.error("Handling command failed!", e); 
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

	protected void handleCommand(final AsyncResponse asyncResponse, final Command command) {
		this.log.warn("No command handler registered for " + command.getClass().getName());
		asyncResponse.resume(Response.status(new UnsupportedCommandType(command.getClass())).build());
	}
	
	private Method findCommandHandler(final Command command) throws NoSuchMethodException, SecurityException {
		final Optional<? extends Method> handler = this.findCommandHandlers().stream()
				.filter(m -> m.getParameterCount() == 2)
				.filter(m -> m.getParameterTypes()[0].equals(AsyncResponse.class))
				.filter(m -> m.getParameterTypes()[1].isAssignableFrom(command.getClass()))
				.findFirst();
		if (handler.isPresent()) {
			return handler.get();
		} else {
			return CommandHandlingResource.class.getMethod(
			        "handleCommand", AsyncResponse.class, Command.class);
		}
	}
	
	private Set<CommandInfo> createCommandInfos(
			final Collection<? extends Method> cmdHandlers) {
		return cmdHandlers.stream()
		    .flatMap(handler -> Arrays.asList(handler.getParameterTypes()).stream())
		    .filter(paramType -> Command.class.isAssignableFrom(paramType))
		    .map(commandType -> new CommandInfo(commandType.getSimpleName()))
		    .collect(Collectors.toSet());
	}
	
	private Collection<? extends Method> findCommandHandlers() {
	    return Arrays.asList(this.getClass().getMethods()).stream()
                .filter(method -> method.isAnnotationPresent(CommandHandler.class))
				.collect(Collectors.toSet());
	}

}