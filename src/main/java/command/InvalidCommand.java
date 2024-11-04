//@@author andreusxcarvalho
package command;
import programme.ProgrammeList;
import history.History;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command that is invalid.
 */
public class InvalidCommand extends Command {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Executes the invalid command.
     *
     * @param programmes The list of programmes.
     * @param history The history of commands executed.
     * @return The result of the command execution.
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        logger.log(Level.INFO, "InvalidCommand executed successfully.");
        return new CommandResult("Invalid command.");
    }
}
