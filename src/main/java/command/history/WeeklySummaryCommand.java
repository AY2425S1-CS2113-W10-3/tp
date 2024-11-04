// @@author andreusxcarvalho
package command.history;

import command.Command;
import command.CommandResult;
import programme.ProgrammeList;
import history.History;

public class WeeklySummaryCommand extends Command {
    public static final String COMMAND_WORD = "wk";

    @Override
    public CommandResult execute(ProgrammeList programmes, History history) {
        // Retrieve the weekly summary from the History class
        String weeklySummary = history.getWeeklyWorkoutSummary();
        return new CommandResult("Your weekly workout summary: \n" + weeklySummary);
    }
}

