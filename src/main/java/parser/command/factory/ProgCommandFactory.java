// @@author nirala-ts
package parser.command.factory;

import command.Command;
import command.InvalidCommand;
import command.programme.CreateProgrammeCommand;
import command.programme.DeleteProgrammeCommand;
import command.programme.ViewProgrammeCommand;
import command.programme.ListProgrammeCommand;
import command.programme.StartProgrammeCommand;
import command.programme.LogProgrammeCommand;
import command.programme.DeleteLogProgrammeCommand;
import command.programme.edit.EditProgrammeCommand;
import command.programme.edit.EditExerciseProgrammeCommand;
import command.programme.edit.CreateExerciseProgrammeCommand;
import command.programme.edit.DeleteExerciseProgrammeCommand;
import command.programme.edit.CreateDayProgrammeCommand;
import command.programme.edit.DeleteDayProgrammeCommand;

import parser.FlagParser;
import programme.Day;
import programme.Exercise;
import programme.ExerciseUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static parser.FlagDefinitions.*;
import static parser.ParserUtils.parseIndex;
import static parser.ParserUtils.splitArguments;
import static parser.ParserUtils.parseDate;

/**
 * The {@code ProgCommandFactory} class is a factory responsible for creating all program-related commands.
 * This class includes helper methods such as {@code parseDay} and {@code parseExercise} to handle
 * common parsing tasks across program-related commands, centralizing command creation and
 * simplifying command handling.
 *
 * <p>Supported Commands:</p>
 * <ul>
 *     <li>Create program command - handled by {@link #prepareCreateCommand}</li>
 *     <li>View program command - handled by {@link #prepareViewCommand}</li>
 *     <li>Edit program and exercises command - handled by {@link #prepareEditCommand}</li>
 *     <li>Start program command - handled by {@link #prepareStartCommand}</li>
 *     <li>Delete program command - handled by {@link #prepareDeleteCommand}</li>
 *     <li>Log program activity command - handled by {@link #prepareLogCommand}</li>
 * </ul>
 *
 */
public class ProgCommandFactory {
    public static final String COMMAND_WORD = "prog";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Command parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        String[] inputArguments = splitArguments(argumentString);
        String subCommandString = inputArguments[0];
        String arguments = inputArguments[1];

        logger.log(Level.INFO, "Successfully parsed sub-command: {0}, with arguments: {1}",
                new Object[]{subCommandString, arguments});

        return switch (subCommandString) {
            case CreateProgrammeCommand.COMMAND_WORD -> prepareCreateCommand(arguments);
            case ViewProgrammeCommand.COMMAND_WORD -> prepareViewCommand(arguments);
            case ListProgrammeCommand.COMMAND_WORD -> new ListProgrammeCommand();
            case EditProgrammeCommand.COMMAND_WORD -> prepareEditCommand(arguments);
            case StartProgrammeCommand.COMMAND_WORD -> prepareStartCommand(arguments);
            case DeleteProgrammeCommand.COMMAND_WORD -> prepareDeleteCommand(arguments);
            case LogProgrammeCommand.COMMAND_WORD -> prepareLogCommand(arguments);
            case DeleteLogProgrammeCommand.COMMAND_WORD -> prepareDeleteLogCommand(arguments);
            default -> new InvalidCommand();
        };
    }

    private Command prepareEditCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";
        FlagParser flagParser = new FlagParser(argumentString, NAME_FLAG, REPS_FLAG, SETS_FLAG, WEIGHT_FLAG, EXERCISE_FLAG, MEAL_CALORIES);
        if (flagParser.hasFlag(UPDATE_EXERCISE_FLAG)) {
            return prepareEditExerciseCommand(flagParser);
        }
        if (flagParser.hasFlag(ADD_EXERCISE_FLAG)) {
            return prepareCreateExerciseCommand(flagParser);
        }
        if (flagParser.hasFlag(REMOVE_EXERCISE_FLAG)) {
            return prepareDeleteExerciseCommand(flagParser);
        }
        if (flagParser.hasFlag(ADD_DAY_INDEX)) {
            return prepareCreateDayCommand(flagParser);
        }
        if (flagParser.hasFlag(REMOVE_DAY_INDEX)) {
            return prepareDeleteDayCommand(flagParser);
        }
        return new InvalidCommand();
    }

    private EditExerciseProgrammeCommand prepareEditExerciseCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(DAY_FLAG);
        String editString = flagParser.getStringByFlag(UPDATE_EXERCISE_FLAG);

        String[] editParts = splitArguments(editString);
        int exerciseIndex = parseIndex(editParts[0]);
        String exerciseString = editParts[1];

        return new EditExerciseProgrammeCommand(
            flagParser.getIndexByFlag(PROGRAMME_FLAG),
            flagParser.getIndexByFlag(DAY_FLAG),
            exerciseIndex,
            parseExerciseUpdate(exerciseString)
        );
    }

    private CreateExerciseProgrammeCommand prepareCreateExerciseCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(DAY_FLAG);
        String exerciseString = flagParser.getStringByFlag(ADD_EXERCISE_FLAG);
        return new CreateExerciseProgrammeCommand(
            flagParser.getIndexByFlag(PROGRAMME_FLAG),
            flagParser.getIndexByFlag(DAY_FLAG),
            parseExercise(exerciseString)
        );
    }

    private DeleteExerciseProgrammeCommand prepareDeleteExerciseCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(DAY_FLAG, REMOVE_EXERCISE_FLAG);
        return new DeleteExerciseProgrammeCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                flagParser.getIndexByFlag(DAY_FLAG),
                flagParser.getIndexByFlag(REMOVE_EXERCISE_FLAG)
        );
    }

    private CreateDayProgrammeCommand prepareCreateDayCommand(FlagParser flagParser) {
        String dayString = flagParser.getStringByFlag(ADD_DAY_FLAG);
        return new CreateDayProgrammeCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                parseDay(dayString)
        );
    }

    private DeleteDayProgrammeCommand prepareDeleteDayCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(REMOVE_DAY_FLAG);
        return new DeleteDayProgrammeCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                flagParser.getIndexByFlag(REMOVE_DAY_FLAG)
        );
    }
    
    private Day parseDay(String dayString) {
        assert dayString != null : "Day string must not be null";

        String[] dayParts = dayString.split(EXERCISE_FLAG);
        String dayName = dayParts[0].trim();
        if (dayName.isEmpty()) {
            throw new IllegalArgumentException("Day name cannot be empty. Please enter a valid day name.");
        }

        Day day = new Day(dayName);
        for (int j = 1; j < dayParts.length; j++) {
            String exerciseString = dayParts[j].trim();
            Exercise exercise = parseExercise(exerciseString);
            day.insertExercise(exercise);
        }

        logger.log(Level.INFO, "Parsed day successfully: {0}", dayName);
        return day;
    }

    private Exercise parseExercise(String argumentString) {
        assert argumentString != null : "Argument string must not be null";
        FlagParser flagParser = new FlagParser(argumentString);

        flagParser.validateRequiredFlags(SETS_FLAG, REPS_FLAG, WEIGHT_FLAG, CALORIES_FLAG, NAME_FLAG);

        String name = flagParser.getStringByFlag(NAME_FLAG);
        int sets = flagParser.getIntegerByFlag(SETS_FLAG);
        int reps = flagParser.getIntegerByFlag(REPS_FLAG);
        int weight = flagParser.getIntegerByFlag(WEIGHT_FLAG);
        int calories = flagParser.getIntegerByFlag(CALORIES_FLAG);

        logger.log(Level.INFO, "Successfully parsed exercise details - Name: {0}, Sets: {1}, Reps: {2}, " +
                "Weight: {3}, Calories: {4}", new Object[]{name, sets, reps, weight, calories});

        return new Exercise(sets, reps, weight, calories, name);
    }

    private ExerciseUpdate parseExerciseUpdate(String argumentString){
        assert argumentString != null : "Argument string must not be null";
        FlagParser flagParser = new FlagParser(argumentString);

        return new ExerciseUpdate(
                flagParser.getIntegerByFlag(SETS_FLAG),
                flagParser.getIntegerByFlag(REPS_FLAG),
                flagParser.getIntegerByFlag(WEIGHT_FLAG),
                flagParser.getIntegerByFlag(CALORIES_FLAG),
                flagParser.getStringByFlag(NAME_FLAG)
        );
    }
}

