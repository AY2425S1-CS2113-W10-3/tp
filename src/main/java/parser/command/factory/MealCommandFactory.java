// @@author Atulteja
package parser.command.factory;

import command.Command;
import command.InvalidCommand;
import command.meals.AddMealCommand;
import command.meals.DeleteMealCommand;
import command.meals.ViewMealCommand;
import meal.Meal;
import parser.FlagParser;

import java.time.LocalDate;


import static parser.FlagDefinitions.*;
import static parser.ParserUtils.parseDate;
import static parser.ParserUtils.splitArguments;


/**
 * Factory class for parsing meal-related commands.
 * This class processes command strings and creates appropriate meal command instances.
 */
public class MealCommandFactory {
    public static final String COMMAND_WORD = "meal";

    /**
     * Parses the argument string and returns the appropriate meal command based on the subcommand.
     *
     * @param argumentString the argument string containing the subcommand and flags
     * @return the corresponding Command object based on the subcommand;
     *          InvalidCommand if the subcommand is unrecognized
     * @throws AssertionError if the argument string is null
     */
    public Command parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        String[] inputArguments = splitArguments(argumentString);
        String subCommandString = inputArguments[0];
        String arguments = inputArguments[1];

        return switch (subCommandString) {
        case AddMealCommand.COMMAND_WORD -> prepareAddCommand(arguments);
        case DeleteMealCommand.COMMAND_WORD -> prepareDeleteCommand(arguments);
        case ViewMealCommand.COMMAND_WORD -> prepareViewCommand(arguments);
        default -> new InvalidCommand();
        };
    }

    /**
     * Prepares an AddMealCommand by parsing the argument string for the meal name, calories, and date.
     *
     * @param argumentString the argument string containing flags for name, calories, and date
     * @return an AddMealCommand containing the parsed meal details
     * @throws AssertionError if required flags are missing or invalid
     */
    public Command prepareAddCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(MEAL_NAME, MEAL_CALORIES);

        String mealName = flagParser.getStringByFlag(MEAL_NAME);
        int mealCalories = flagParser.getIntegerByFlag(MEAL_CALORIES);
        LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

        Meal mealToAdd = new Meal(mealName, mealCalories);

        return new AddMealCommand(mealToAdd, date);
    }

    /**
     * Prepares a DeleteMealCommand by parsing the argument string for the meal index and date.
     *
     * @param argumentString the argument string containing flags for meal index and date
     * @return a DeleteMealCommand containing the parsed meal index and date
     * @throws AssertionError if the required flag is missing or invalid
     */
    public Command prepareDeleteCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(MEAL_INDEX);

        int mealIndexToDelete = flagParser.getIndexByFlag(MEAL_INDEX);
        LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

        return new DeleteMealCommand(mealIndexToDelete, date);
    }

    /**
     * Prepares a ViewMealCommand by parsing the argument string for the date.
     * @param argumentString the argument string containing the date
     * @return a ViewMealCommand containing the parsed date
     */
 public Command prepareViewCommand(String argumentString) {
    FlagParser flagParser = new FlagParser(argumentString);

    // Validate that the required DATE_FLAG is present
    flagParser.validateRequiredFlags(DATE_FLAG);

    // Retrieve the date value associated with the DATE_FLAG
    LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

    return new ViewMealCommand(date);
    }
}
