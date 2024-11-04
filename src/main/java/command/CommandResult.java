//@@author andreusxcarvalho
package command;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {
    private final String message;

    /**
     * Constructs a CommandResult with the specified message.
     *
     * @param message The message to be included in the command result.
     */
    public CommandResult(String message) {
        this.message = message;
    }

    /**
     * Returns the message of the command result.
     *
     * @return The message of the command result.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Checks if this CommandResult is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if this CommandResult is equal to the other object, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        CommandResult that = (CommandResult) other;
        return message.equals(that.message);
    }
}
