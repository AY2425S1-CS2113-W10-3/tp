// @@author Bev-Low
package parser.command.factory;

import command.Command;
import command.InvalidCommand;
import command.water.AddWaterCommand;
import command.water.DeleteWaterCommand;
import command.water.ViewWaterCommand;
import parser.FlagParser;

import java.time.LocalDate;

import static parser.FlagDefinitions.VOLUME_FLAG;
import static parser.FlagDefinitions.WATER_INDEX;
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
        LocalDate date = flagParser.getDateByFlag("/t");

        return new AddWaterCommand(water, date);
    }

    public Command prepareDeleteCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(WATER_INDEX);

        int waterIndexToDelete = flagParser.getIndexByFlag(WATER_INDEX);
        LocalDate date = flagParser.getDateByFlag("/t");

        return new DeleteWaterCommand(waterIndexToDelete, date);
    }

    public Command prepareViewCommand(String argumentString) {
        LocalDate date = parseDate(argumentString);
        return new ViewWaterCommand(date);
    }
}
