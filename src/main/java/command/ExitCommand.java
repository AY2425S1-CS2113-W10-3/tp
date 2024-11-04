//@@author andreusxcarvalho
package command;
import programme.ProgrammeList;
import history.History;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents the command to exit the application.
 */
public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "bye";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Executes the exit command.
     *
     * @param programmes The list of programmes.
     * @param history The history of commands executed.
     * @return The result of the command execution.
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        logger.log(Level.INFO, "ExitCommand executed.");
        return new CommandResult("Exiting BuffBuddy...");
    }
}
