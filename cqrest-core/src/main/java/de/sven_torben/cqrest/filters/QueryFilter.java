package de.sven_torben.cqrest.filters;

import de.sven_torben.cqrest.HttpMethods;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

/**
 * This {@linkplain ContainerRequestFilter} ensures that QUERY request to not carry a payload.
 */
@Provider
@Priority(Priorities.USER + 1)
public class QueryFilter implements ContainerRequestFilter {

  @Override
  public void filter(final ContainerRequestContext req) throws IOException {
    if (HttpMethods.QUERY.asString().equalsIgnoreCase(req.getMethod())) {
      if (req.hasEntity()) {
        throw new InternalServerErrorException("Queries must not contain payloads.");
      }
    }
  }

}
