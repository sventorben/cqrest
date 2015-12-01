package de.sven_torben.cqrest;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.ext.ContextResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;

import de.sven_torben.cqrest.representations.Command;

public abstract class CommandContextResolver implements ContextResolver<ObjectMapper> {

    private static final Logger LOG = LoggerFactory.getLogger(CommandContextResolver.class);

    private final ObjectMapper objectMapper;

    public CommandContextResolver() {
        this.objectMapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.objectMapper.registerSubtypes(this.determineCommands());
    }

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
        if (Command.class.isAssignableFrom(objectType)) {
            return this.objectMapper;
        }
        return null;
    }

    protected abstract Set<Class<? extends Command>> getCommandTypes();

    private NamedType[] determineCommands() {
        final Set<Class<? extends Command>> commandTypes = this.getCommandTypes();
        final Set<NamedType> namedTypes;
        if (commandTypes == null) {
            namedTypes = Collections.emptySet();
        } else {
            namedTypes = commandTypes.stream()
                .map(cmdType -> new NamedType(cmdType, cmdType.getSimpleName()))
                .collect(Collectors.toSet());
            namedTypes.forEach(nt -> LOG.info(
                String.format("Registering command of type %s for name %s", 
                    nt.getType().getName(), 
                    nt.getName())));
        }
        return namedTypes.toArray(new NamedType[namedTypes.size()]);
    }
}
