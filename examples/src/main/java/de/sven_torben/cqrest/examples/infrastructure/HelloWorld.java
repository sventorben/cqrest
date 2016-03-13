package de.sven_torben.cqrest.examples.infrastructure;

import de.sven_torben.cqrest.CommandHandlingResource;
import de.sven_torben.cqrest.annotations.handler.CommandHandler;
import de.sven_torben.cqrest.annotations.handler.QueryHandler;
import de.sven_torben.cqrest.examples.domain.commands.Command;
import de.sven_torben.cqrest.examples.domain.commands.SayGoodbyeCommand;
import de.sven_torben.cqrest.examples.domain.commands.SayHelloCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

@Path("/world")
public class HelloWorld extends CommandHandlingResource<Command> {

  private static final Logger LOG = LoggerFactory.getLogger(HelloWorld.class);

  public HelloWorld() {
    super(Command.class);
  }

  @QueryHandler
  @Produces({ "application/json" })
  public String getHelloWorldJson() {
    sleep(15, "sleep");
    return "{\"result\":\"hello world!\"}";
  }

  /**
   * Handles {@linkplain SayHelloCommand}
   * 
   * @param asyncResponse
   *          Async HTTP response.
   * @param cmd
   *          Command to be handled.
   */
  @CommandHandler
  public void handleCommand(final AsyncResponse asyncResponse, final SayHelloCommand cmd) {

    Executors.newSingleThreadExecutor().submit(() -> {
      sleep(15, "sleep");
      LOG.info("Hello " + cmd.name + "!");
      asyncResponse.resume(Response.accepted().build());
    });

  }

  /**
   * Handles {@linkplain SayGoodbyeCommand}
   * 
   * @param asyncResponse
   *          Async HTTP response.
   * @param cmd
   *          Command to be handled.
   */
  @CommandHandler
  public void handleCommand(final AsyncResponse asyncResponse, final SayGoodbyeCommand cmd) {

    Executors.newSingleThreadExecutor().submit(() -> {
      sleep(15, "sleep");
      LOG.info("Goodbye " + cmd.name + "!");
      asyncResponse.resume(Response.accepted().build());
    });

  }

  private static void sleep(final int secs, final String text) {
    try {
      for (int i = 0; i < secs; i++) {
        LOG.info(text + ": " + i);
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
      LOG.error("Someone interrupted my sleep ...", e);
    }
  }

}
