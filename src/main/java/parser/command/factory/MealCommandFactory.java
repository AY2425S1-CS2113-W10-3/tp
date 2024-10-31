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
import static parser.ParserUtils.splitArguments;

public class MealCommandFactory {
    public static final String COMMAND_WORD = "meal";

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

    public Command prepareAddCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(MEAL_NAME, MEAL_CALORIES);

        String mealName = flagParser.getStringByFlag(MEAL_NAME);
        int mealCalories = flagParser.getIntegerByFlag(MEAL_CALORIES);
        LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

        Meal mealToAdd = new Meal(mealName, mealCalories);

        return new AddMealCommand(mealToAdd, date);
    }

    public Command prepareDeleteCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(MEAL_INDEX);

        int mealIndexToDelete = flagParser.getIndexByFlag(MEAL_INDEX);
        LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

        return new DeleteMealCommand(mealIndexToDelete, date);
    }

    public Command prepareViewCommand(String argumentString) {
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(DATE_FLAG);

        LocalDate date = flagParser.getDateByFlag(DATE_FLAG);

        return new ViewMealCommand(date);
    }
}
