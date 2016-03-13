package de.sven_torben.cqrest.filters;

import de.sven_torben.cqrest.HttpMethods;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/**
 * This {@linkplain ContainerRequestFilter} ensures that clients which do not natively support the
 * COMMAND method can use default HTTP POST method as a fallback.
 */
@Provider
@PreMatching
@Priority(MethodMappingFilter.PRIORITY)
public class PostMappingFilter extends MethodMappingFilter {

  public PostMappingFilter() {
    super(HttpMethod.POST, HttpMethods.COMMAND);
  }

}
