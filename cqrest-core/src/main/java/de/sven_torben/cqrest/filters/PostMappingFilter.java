package de.sven_torben.cqrest.filters;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import de.sven_torben.cqrest.HttpMethods;

@Provider
@PreMatching
@Priority(MethodMappingFilter.PRIORITY)
public class PostMappingFilter extends MethodMappingFilter {

	public PostMappingFilter() {
		super(HttpMethod.POST, HttpMethods.COMMAND);
	}

}
