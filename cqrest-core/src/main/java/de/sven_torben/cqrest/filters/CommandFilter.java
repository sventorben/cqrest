package de.sven_torben.cqrest.filters;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import de.sven_torben.cqrest.HttpMethods;

@Provider
@Priority(Priorities.USER + 1)
public class CommandFilter implements ContainerResponseFilter {

    @Override
    public void filter(final ContainerRequestContext req, final ContainerResponseContext res) throws IOException {
        if (HttpMethods.COMMAND.asString().equalsIgnoreCase(req.getMethod())) {
            if (res.getStatusInfo().getFamily().equals(Status.Family.SUCCESSFUL) && res.hasEntity()) {
                throw new InternalServerErrorException("Commands must not return entities.");
            }
        }
    }

}
