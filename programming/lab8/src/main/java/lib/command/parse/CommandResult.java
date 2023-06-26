package lib.command.parse;

import java.io.Serializable;

public class CommandResult implements Serializable {
    private String message;
    private boolean success;

    public CommandResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public CommandResult(boolean success) {
        this("", success);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
