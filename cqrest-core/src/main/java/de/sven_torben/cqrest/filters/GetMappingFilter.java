package de.sven_torben.cqrest.filters;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import de.sven_torben.cqrest.HttpMethods;

/**
 * This {@linkplain ContainerRequestFilter} ensures that clients which do not natively support the QUERY method can 
 * use default HTTP GET method as a fallback.
 */
@Provider
@PreMatching
@Priority(MethodMappingFilter.PRIORITY)
public class GetMappingFilter extends MethodMappingFilter {

    public GetMappingFilter() {
        super(HttpMethod.GET, HttpMethods.QUERY);
    }

}
