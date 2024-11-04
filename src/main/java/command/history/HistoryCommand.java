// @@author andreusxcarvalho
package command.history;

import command.Command;
import command.CommandResult;
import history.History;
import programme.ProgrammeList;

/**
 * Represents a command to show the full history or perform history-related operations.
 */
public class HistoryCommand extends Command {
    public static final String COMMAND_WORD = "history";

    @Override
    public CommandResult execute(ProgrammeList pList, History history) {
        // Ensure history is available
        if (history.getHistory().isEmpty()) {
            return new CommandResult("No history available.");
        }

        // Display the entire history
        return new CommandResult(history.toString());
    }
}



