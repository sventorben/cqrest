package de.sven_torben.cqrest;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.ext.ContextResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.module.SimpleModule;

public abstract class CommandContextResolver<T> implements ContextResolver<ObjectMapper> {

    private static final Logger LOG = LoggerFactory.getLogger(CommandContextResolver.class);

    private final ObjectMapper objectMapper;
    private final Class<?> commandType;
    
    public CommandContextResolver(final Class<T> commandType) {
        this.commandType = Objects.requireNonNull(commandType);
        this.objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.objectMapper.registerModule(this.createCommandModule(this.determineCommands()));
    }

    @Override
    public ObjectMapper getContext(Class<?> type) {
        if (this.commandType.isAssignableFrom(type)) {
            return this.getObjectMapper();
        }
        return null;
    }

    protected ObjectMapper getObjectMapper() {
        return this.objectMapper;
    }
    
    protected abstract Set<Class<? extends T>> getCommandTypes();

    private Module createCommandModule(NamedType[] commandTypes) {
        final SimpleModule commandModule = new SimpleModule("CommandModule");
        commandModule.registerSubtypes(commandTypes);
        commandModule.setMixInAnnotation(this.commandType, CommandMixin.class);
        for (NamedType commandType : commandTypes) {
            commandModule.setMixInAnnotation(commandType.getType(), CommandMixin.class);
        }
        return commandModule;
    }
    
    private NamedType[] determineCommands() {
        final Set<Class<? extends T>> commandTypes = this.getCommandTypes();
        final Set<NamedType> namedTypes;
        if (commandTypes == null) {
            namedTypes = Collections.emptySet();
        } else {
            namedTypes = commandTypes.stream()
                .map(cmdType -> new NamedType(cmdType, CommandInfo.nameForType(cmdType)))
                .collect(Collectors.toSet());
            namedTypes.forEach(nt -> LOG.info(
                String.format("Registering command of type %s for name %s", 
                    nt.getType().getName(), 
                    nt.getName())));
        }
        return namedTypes.toArray(new NamedType[namedTypes.size()]);
    }
}
