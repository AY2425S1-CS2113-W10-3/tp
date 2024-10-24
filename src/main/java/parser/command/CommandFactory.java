package parser.command;

import command.Command;
import command.LogCommand;
import command.ExitCommand;
import command.InvalidCommand;
import parser.FlagParser;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    CommandFactory is a factory class that creates different commands
    based on argumentString and commandString
    Subcommands "prog","history" etc. are seperated into their own factory classes
    Which command factory distributes inputs into based on the command word
 */
public class CommandFactory {
    private final ProgCommandFactory progFactory;
    private final MealCommandFactory mealFactory;
    private final WaterCommandFactory waterFactory;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public CommandFactory() {
        this.progFactory = new ProgCommandFactory();  // Dependency injection for better testability
        this.mealFactory = new MealCommandFactory();
        this.waterFactory = new WaterCommandFactory();
    }

    public Command createCommand(String commandString, String argumentString) {
        return switch (commandString) {
        case ProgCommandFactory.COMMAND_WORD -> progFactory.parse(argumentString);
        case LogCommand.COMMAND_WORD -> prepareLogCommand(argumentString);
        case ExitCommand.COMMAND_WORD -> new ExitCommand();
        case MealCommandFactory.COMMAND_WORD -> mealFactory.parse(argumentString);
        case WaterCommandFactory.COMMAND_WORD -> waterFactory.parse(argumentString);
        default -> new InvalidCommand();
        };
    }

    private Command prepareLogCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags("/d");

        int progIndex = flagParser.getIndexByFlag("/p");
        int dayIndex = flagParser.getIndexByFlag("/d");
        LocalDate date = flagParser.getDateByFlag("/t");

        logger.log(Level.INFO, "LogCommand prepared with Programme index: {0}, Day index: {1}, Date: {2}",
                new Object[]{progIndex, dayIndex, date});

        return new LogCommand(progIndex, dayIndex, date);
    }
}
