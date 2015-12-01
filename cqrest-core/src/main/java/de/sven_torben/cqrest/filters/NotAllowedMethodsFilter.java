package de.sven_torben.cqrest.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import de.sven_torben.cqrest.HttpMethods;

@Provider
@PreMatching
@Priority(MethodMappingFilter.PRIORITY + 1)
public class NotAllowedMethodsFilter implements ContainerRequestFilter {

    @Override
    public void filter(final ContainerRequestContext req) throws IOException {
        if (!HttpMethods.ALL.contains(req.getMethod())) {
            final Response response = Response.status(Status.METHOD_NOT_ALLOWED).allow(HttpMethods.ALL).build();
            throw new NotAllowedException(String.format("%s is not allowed.", req.getMethod()), response);
        }
    }

}
