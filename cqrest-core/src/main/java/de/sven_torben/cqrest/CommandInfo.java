package de.sven_torben.cqrest;

import java.util.Objects;

final class CommandInfo {
	
	private final String command;

	public CommandInfo(final Class<?> commandType) {
		this.command = nameForType(Objects.requireNonNull(commandType));
	}

    public String getCommand() {
		return this.command;
	}

    public static String nameForType(final Class<?> commandType) {
        return commandType.getSimpleName().replaceAll("Command$", "");
    }
    
}
