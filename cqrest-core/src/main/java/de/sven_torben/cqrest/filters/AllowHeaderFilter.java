package de.sven_torben.cqrest.filters;

import de.sven_torben.cqrest.HttpMethods;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

/**
 * {@linkplain ContainerResponseFilter} that replaces the default HTTP Allow header with the values
 * from {@linkplain HttpMethods#ALL}.
 */
@Provider
@Priority(Priorities.HEADER_DECORATOR + 1)
public class AllowHeaderFilter implements ContainerResponseFilter {

  public void filter(final ContainerRequestContext req, final ContainerResponseContext res)
      throws IOException {
    res.getHeaders().remove(HttpHeaders.ALLOW);
    res.getHeaders().put(HttpHeaders.ALLOW, new ArrayList<Object>(HttpMethods.ALL));
  }
}
