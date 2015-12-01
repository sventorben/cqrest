package de.sven_torben.cqrest.representations;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@type")
public interface Command {
}

