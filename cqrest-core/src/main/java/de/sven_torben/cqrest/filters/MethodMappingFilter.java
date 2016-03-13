package de.sven_torben.cqrest.filters;

import de.sven_torben.cqrest.HttpMethods;

import java.io.IOException;
import java.util.Objects;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

/**
 * This {@linkplain ContainerRequestFilter} ensures that clients which do not natively support the
 * cqrest protocol can fallback to default HTTP methods.
 */
public abstract class MethodMappingFilter implements ContainerRequestFilter {

  public static final int PRIORITY = Priorities.HEADER_DECORATOR + 1;

  private final String from;
  private final HttpMethods to;

  public MethodMappingFilter(final String from, final HttpMethods to) {
    this.from = Objects.requireNonNull(from);
    this.to = Objects.requireNonNull(to);
  }

  @Override
  public void filter(final ContainerRequestContext req) throws IOException {
    if (from.equalsIgnoreCase(req.getMethod())) {
      req.setMethod(to.asString());
    }
  }

}
