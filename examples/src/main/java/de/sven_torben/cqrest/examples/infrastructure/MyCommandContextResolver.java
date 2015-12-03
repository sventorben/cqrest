package de.sven_torben.cqrest.examples.infrastructure;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.ext.Provider;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import de.sven_torben.cqrest.CommandContextResolver;
import de.sven_torben.cqrest.MediaTypes;
import de.sven_torben.cqrest.examples.domain.commands.Command;

@Provider
@Consumes(MediaTypes.CQREST_COMMAND_JSON)
public class MyCommandContextResolver extends CommandContextResolver<Command> {

    public MyCommandContextResolver() {
        super(Command.class);
    }
    
	@Override
	protected Set<Class<? extends Command>> getCommandTypes() {
		final Reflections reflections = new Reflections(
                "de.sven_torben.cqrest.examples.domain.commands", 
		        new SubTypesScanner(), 
		        new TypeAnnotationsScanner());
		return reflections.getSubTypesOf(Command.class);
	}

}
