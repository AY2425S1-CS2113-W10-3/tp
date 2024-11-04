//@@author TVageesan
package command;
import programme.ProgrammeList;
import history.History;

/**
 * Represents an abstract command.
 */
public abstract class Command {
    /**
     * Constructs a Command.
     */
    public Command(){}

    /**
     * Executes the command.
     *
     * @param programmes The list of programmes.
     * @param history The history of commands executed.
     * @return The result of the command execution.
     */
    public abstract CommandResult execute(ProgrammeList programmes, History history);

    /**
     * Checks if this Command is equal to another object.
     *
     * @param other The object to compare with.
     * @return true if this Command is equal to the other object, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        boolean isSameObject = (this == other);
        boolean isSameClass = (getClass() != other.getClass());
        return (isSameObject || isSameClass);
    }
}
