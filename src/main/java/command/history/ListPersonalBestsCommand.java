// @@author andreusxcarvalho
package command.history;

import command.Command;
import command.CommandResult;
import programme.ProgrammeList;
import history.History;

/**
 * Command to list personal bests for all exercises.
 */
public class ListPersonalBestsCommand extends Command {
    public static final String COMMAND_WORD = "pb";

    @Override
    public CommandResult execute(ProgrammeList pList, History history) {
        // Directly retrieve the formatted personal bests message from History
        return new CommandResult(history.getFormattedPersonalBests());
    }
}

