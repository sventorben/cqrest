package de.sven_torben.cqrest;

import java.util.Objects;

final class CommandInfo {
	
	private final String command;

	public CommandInfo(final String command) {
		this.command = Objects.requireNonNull(command);
	}

	public String getCommand() {
		return this.command;
	}
	
}
