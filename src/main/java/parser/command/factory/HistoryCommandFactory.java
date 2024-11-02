// @@author andreusxcarvalho

package parser.command.factory;

import command.Command;
import command.history.HistoryCommand;
import command.history.ViewPersonalBestCommand;
import command.history.ListPersonalBestsCommand;
import command.history.WeeklySummaryCommand;
import command.history.DeleteRecordCommand;
import command.InvalidCommand;
import parser.FlagParser;

import java.time.LocalDate;

import static parser.ParserUtils.parseDate;
import static parser.ParserUtils.splitArguments;

public class HistoryCommandFactory {
    public static final String COMMAND_WORD = "history";

    public Command parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        // Handle empty argumentString by defaulting to HistoryCommand
        String parsedArgument = argumentString.isEmpty() ? HistoryCommand.COMMAND_WORD : argumentString;
        String[] inputArguments = splitArguments(parsedArgument);
        String subCommandString = inputArguments[0];
        String arguments = inputArguments.length > 1 ? inputArguments[1] : "";

        return switch (subCommandString) {
        case HistoryCommand.COMMAND_WORD -> new HistoryCommand();
        case ListPersonalBestsCommand.COMMAND_WORD -> prepareListPersonalBestsCommand();
        case WeeklySummaryCommand.COMMAND_WORD -> prepareWeeklySummaryCommand();
        case ViewPersonalBestCommand.COMMAND_WORD -> prepareViewPersonalBestCommand(arguments);
        case DeleteRecordCommand.COMMAND_WORD -> prepareDeleteRecordCommand(arguments);
        default -> new InvalidCommand();
        };
    }

    private Command prepareWeeklySummaryCommand() {
        return new WeeklySummaryCommand();
    }

    private Command prepareViewPersonalBestCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);
        flagParser.validateRequiredFlags("/e");

        String exerciseName = flagParser.getStringByFlag("/e");

        return new ViewPersonalBestCommand(exerciseName);
    }

    private Command prepareListPersonalBestsCommand() {
        return new ListPersonalBestsCommand();
    }

    // @@author TVageesan
    /**
     * Prepares a command to delete a record for a specific date.
     * <p>
     * Parses the provided date string and creates a {@code DeleteRecordCommand}
     * with the parsed date as the target date for deletion.
     * </p>
     *
     * @param argumentString the string containing the date to be deleted
     * @return a {@code DeleteRecordCommand} configured with the parsed date
     * @throws IllegalArgumentException if the date cannot be parsed from {@code argumentString}
     */
    private Command prepareDeleteRecordCommand(String argumentString) {
        LocalDate toDelete = parseDate(argumentString);
        return new DeleteRecordCommand(toDelete);
    }
}



