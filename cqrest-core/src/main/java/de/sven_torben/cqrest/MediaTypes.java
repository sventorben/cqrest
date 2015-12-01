package de.sven_torben.cqrest;

import javax.ws.rs.core.MediaType;

public final class MediaTypes {
	
	public static final String CQREST_INFO_JSON = "application/cqrest-info+json";
	public static final MediaType CQREST_INFO_JSON_MT = MediaType.valueOf(CQREST_INFO_JSON);
	
	public static final String CQREST_COMMAND_JSON = "application/cqrest-command+json";
	public static final MediaType CQREST_COMMAND_JSON_MT = MediaType.valueOf(CQREST_COMMAND_JSON);
	
	private MediaTypes() {
	}
}
