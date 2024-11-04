// @@author Bev-Low
package parser.command.factory;

import command.Command;
import command.InvalidCommand;
import command.water.AddWaterCommand;
import command.water.DeleteWaterCommand;
import command.water.ViewWaterCommand;
import parser.FlagParser;

import java.time.LocalDate;


import static parser.FlagDefinitions.*;
import static parser.ParserUtils.parseDate;
import static parser.ParserUtils.splitArguments;

public class WaterCommandFactory {
    public static final String COMMAND_WORD = "water";

    public Command parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        String[] inputArguments = splitArguments(argumentString);
        String subCommandString = inputArguments[0];
        String arguments = inputArguments[1];

        return switch (subCommandString) {
        case AddWaterCommand.COMMAND_WORD -> prepareAddCommand(arguments);
        case DeleteWaterCommand.COMMAND_WORD -> prepareDeleteCommand(arguments);
        case ViewWaterCommand.COMMAND_WORD -> prepareViewCommand(arguments);
        default -> new InvalidCommand();
        };
    }

    public Command prepareAddCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(VOLUME_FLAG);

        float water = flagParser.getFloatByFlag(VOLUME_FLAG);
        LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

        return new AddWaterCommand(water, date);
    }

    public Command prepareDeleteCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(WATER_INDEX);

        int waterIndexToDelete = flagParser.getIndexByFlag(VOLUME_FLAG);
        LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

        return new DeleteWaterCommand(waterIndexToDelete, date);
    }

public Command prepareViewCommand(String argumentString) {
    FlagParser flagParser = new FlagParser(argumentString);
    flagParser.validateRequiredFlags(DATE_FLAG);

    LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

    return new ViewWaterCommand(date);
  }
}
