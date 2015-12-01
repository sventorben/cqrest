package de.sven_torben.cqrest;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import de.sven_torben.cqrest.representations.Command;

final class UnsupportedCommandType implements StatusType {

    private final Class<? extends Command> commandType;
    
    public UnsupportedCommandType(final Class<? extends Command> commandType) {
        this.commandType = commandType;
    }
    
    @Override
    public int getStatusCode() {
        return Status.BAD_REQUEST.getStatusCode();
    }

    @Override
    public Family getFamily() {
        return Family.CLIENT_ERROR;
    }

    @Override
    public String getReasonPhrase() {
        return String.format("Command %s is not supported by the resource.", commandType.getSimpleName());
    }

}
