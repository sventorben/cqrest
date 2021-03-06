package de.sven_torben.cqrest.examples.infrastructure;

import de.sven_torben.cqrest.CommandContextResolver;
import de.sven_torben.cqrest.MediaTypes;
import de.sven_torben.cqrest.examples.domain.commands.Command;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.ext.Provider;

@Provider
@Consumes(MediaTypes.CQREST_COMMAND_JSON)
public class MyCommandContextResolver extends CommandContextResolver<Command> {

  public MyCommandContextResolver() {
    super(Command.class);
  }

  @Override
  protected Set<Class<? extends Command>> getCommandTypes() {
    final Reflections reflections = new Reflections(
        "de.sven_torben.cqrest.examples.domain.commands", new SubTypesScanner());
    return reflections.getSubTypesOf(Command.class);
  }

}
