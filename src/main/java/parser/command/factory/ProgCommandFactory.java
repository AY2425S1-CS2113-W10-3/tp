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

    /**
     * Parses the provided argument string to identify and create the appropriate command.
     *
     * @author nirala-ts
     * @param argumentString The command's argument string, which contains the subcommand and arguments.
     * @return The created {@code Command} object based on the subcommand specified in the argument string.
     */
    public Command parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        /*
         * Splits the full command input into the primary command and its associated arguments,
         * enabling identification of the command category.
         */
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
        case DeleteProgrammeCommand.COMMAND_WORD ->  prepareDeleteCommand(arguments);
        case LogProgrammeCommand.COMMAND_WORD -> prepareLogCommand(arguments);
        case DeleteLogProgrammeCommand.COMMAND_WORD -> prepareDeleteLogCommand(arguments);
        default -> new InvalidCommand();
        };
    }


    private Command prepareEditCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";
        FlagParser flagParser = new FlagParser(argumentString, NAME_FLAG, REPS_FLAG,SETS_FLAG,WEIGHT_FLAG,EXERCISE_FLAG,MEAL_CALORIES);
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

    private EditExerciseCommand prepareEditExerciseCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(DAY_FLAG);
        String editString = flagParser.getStringByFlag(UPDATE_EXERCISE_FLAG);

        String[] editParts = splitArguments(editString);
        int exerciseIndex = parseIndex(editParts[0]);
        String exerciseString = editParts[1];

        return new EditExerciseCommand(
            flagParser.getIndexByFlag(PROGRAMME_FLAG),
            flagParser.getIndexByFlag(DAY_FLAG),
            exerciseIndex,
            parseExercise(exerciseString)
        );
    }

    private CreateExerciseCommand prepareCreateExerciseCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(DAY_FLAG);
        String exerciseString = flagParser.getStringByFlag(ADD_EXERCISE_FLAG);
        return new CreateExerciseCommand(
            flagParser.getIndexByFlag(PROGRAMME_FLAG),
            flagParser.getIndexByFlag(DAY_FLAG),
            parseExercise(exerciseString)
        );
    }

    private DeleteExerciseCommand prepareDeleteExerciseCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(DAY_FLAG, REMOVE_EXERCISE_FLAG);
        return new DeleteExerciseCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                flagParser.getIndexByFlag(DAY_FLAG),
                flagParser.getIndexByFlag(REMOVE_EXERCISE_FLAG)
        );
    }

    private CreateDayCommand prepareCreateDayCommand(FlagParser flagParser) {
        String dayString = flagParser.getStringByFlag(ADD_DAY_INDEX);
        return new CreateDayCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                parseDay(dayString)
        );
    }

    private DeleteDayCommand prepareDeleteDayCommand(FlagParser flagParser) {
        flagParser.validateRequiredFlags(REMOVE_DAY_INDEX);
        return new DeleteDayCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                flagParser.getIndexByFlag(REMOVE_DAY_INDEX)
        );
    }
  
    /**
     * Prepares and returns a {@link CreateProgrammeCommand} to create a new program with a specified name and days.
     *
     * @author nirala-ts
     * @param argumentString The argument string containing program details, including days and exercises.
     * @return A {@link CreateProgrammeCommand} object that represents the request to create a new program.
     */

    private Command prepareCreateCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        ArrayList<Day> days = new ArrayList<>();
        String[] progParts = argumentString.split(DAY_FLAG);
        String progName = progParts[0].trim();
        if (progName.isEmpty()) {
            logger.log(Level.WARNING, "Programme name is empty");
            throw new IllegalArgumentException("Programme name cannot be empty. Please enter a name.");
        }

        for (int i = 1; i < progParts.length; i++) {
            String dayString = progParts[i].trim();
            Day day = parseDay(dayString);
            days.add(day);
        }

        logger.log(Level.INFO, "Successfully prepared CreateCommand with programme: {0}", progName);
        return new CreateProgrammeCommand(progName, days);
    }

    /**
     * Prepares and returns a {@link ViewProgrammeCommand} to view a specific program by its index.
     *
     * @author nirala-ts
     * @param argumentString The string containing the program index to view.
     * @return A {@link ViewProgrammeCommand} initialized with the specified program index.
     */
    private Command prepareViewCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        int progIndex = parseIndex(argumentString);

        logger.log(Level.INFO, "Successfully prepared ViewCommand");
        return new ViewProgrammeCommand(progIndex);
    }

    /**
     * Prepares and returns a {@link StartProgrammeCommand} to activate a specific program by its index.
     *
     * @author nirala-ts
     * @param argumentString The string containing the program index to start.
     * @return A {@link StartProgrammeCommand} initialized with the specified program index.
     */
    private Command prepareStartCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        int progIndex = parseIndex(argumentString);

        logger.log(Level.INFO, "Successfully prepared StartCommand");
        return new StartProgrammeCommand(progIndex);
    }

    /**
     * Prepares and returns a {@link DeleteProgrammeCommand} to remove a specific program by its index.
     *
     * @author nirala-ts
     * @param argumentString The string containing the program index to delete.
     * @return A {@link DeleteProgrammeCommand} initialized with the specified program index.
     */
    private Command prepareDeleteCommand(String argumentString){
        assert argumentString != null : "Argument string must not be null";

        int progIndex = parseIndex(argumentString);

        logger.log(Level.INFO, "Successfully prepared DeleteCommand");
        return new DeleteProgrammeCommand(progIndex);
    }

    /**
     * Prepares and returns a {@link LogProgrammeCommand} to log activity in a specific program on a particular date.
     *
     * @author nirala-ts
     * @param argumentString The string containing flags for the date, program index, and day index.
     * @return A {@link LogProgrammeCommand} initialized with the specified date and indices.
     * @throws IllegalArgumentException If required flags are missing.
     */
private Command prepareLogCommand(String argumentString) {
    FlagParser flagParser = new FlagParser(argumentString);

    flagParser.validateRequiredFlags(DAY_FLAG);

    LocalDate date = flagParser.getDateByFlag(DATE_FLAG);
    int progIndex = flagParser.getIndexByFlag(PROGRAMME_FLAG);
    int dayIndex = flagParser.getIndexByFlag(DAY_FLAG);

    logger.log(Level.INFO, "Successfully prepared LogCommand with Date: {0}, " +
            "Programme index: {1}, Day index: {2}", new Object[]{progIndex, dayIndex, date});

    return new LogProgrammeCommand(progIndex, dayIndex, date);
}

    // @@author TVageesan

    /**
     * Prepares a command to delete a log entry based on the specified date.
     * <p>
     * This method parses the given argument string to obtain a date, which is then used
     * to create a {@code DeleteLogProgrammeCommand} with the specified date.
     * </p>
     *
     * @param argumentString the string representing the date to be parsed and deleted
     * @return a {@code DeleteLogProgrammeCommand} configured with the parsed date
     * @throws IllegalArgumentException if the date format in {@code argumentString} is invalid
     */
    private Command prepareDeleteLogCommand(String argumentString){
        LocalDate date = parseDate(argumentString);
        return new DeleteLogProgrammeCommand(date);
    }

    /**
     * Prepares and returns an appropriate {@link EditProgrammeCommand} based on the flags parsed
     * from the provided argument string.
     *
     * @param argumentString A {@link String} containing arguments to parse.
     * @return The specific {@link EditProgrammeCommand} object that corresponds to the flag detected.
     * @throws IllegalArgumentException If no recognized edit command flag is present in the argument string.
     */
    private EditProgrammeCommand prepareEditCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        /*
         * Parse by flags except for all exercise related flags,
         * preserving exercise arguments in one string for later parsing if needed
         */
        FlagParser flagParser = new FlagParser (
                argumentString,
                NAME_FLAG, REPS_FLAG,SETS_FLAG,WEIGHT_FLAG,EXERCISE_FLAG,CALORIES_FLAG
        );

        if (flagParser.hasFlag(UPDATE_EXERCISE_FLAG)) {
            return prepareEditExerciseCommand(flagParser);
        }

        if (flagParser.hasFlag(ADD_EXERCISE_FLAG)) {
            return prepareCreateExerciseCommand(flagParser);
        }

        if (flagParser.hasFlag(REMOVE_EXERCISE_FLAG)) {
            return prepareDeleteExerciseCommand(flagParser);
        }

        if (flagParser.hasFlag(ADD_DAY_FLAG)) {
            return prepareCreateDayCommand(flagParser);
        }

        if (flagParser.hasFlag(REMOVE_DAY_FLAG)) {
            return prepareDeleteDayCommand(flagParser);
        }

        throw new IllegalArgumentException("Missing edit command flag. Please provide a valid command flag.");
    }

    /**
     * Creates and returns an {@link EditExerciseProgrammeCommand} for updating an exercise in a program.
     *
     * @author TVageesan
     * @param flagParser A {@link FlagParser} containing parsed flags and arguments.
     * @return An {@link EditExerciseProgrammeCommand} object to edit an existing exercise.
     * @throws IllegalArgumentException If required flags are missing.
     */
    private EditExerciseProgrammeCommand prepareEditExerciseCommand(FlagParser flagParser) {
        assert flagParser != null: "flagParser must not be null";

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

    /**
     * Creates and returns a {@link CreateExerciseProgrammeCommand} for adding a new exercise to a day in a program.
     *
     * @param flagParser A {@link FlagParser} containing parsed flags and arguments.
     * @return A {@link CreateExerciseProgrammeCommand} object to create a new exercise.
     * @throws IllegalArgumentException If required flags are missing.
     */
    private CreateExerciseProgrammeCommand prepareCreateExerciseCommand(FlagParser flagParser) {
        assert flagParser != null: "flagParser must not be null";

        flagParser.validateRequiredFlags(DAY_FLAG);
        String exerciseString = flagParser.getStringByFlag(ADD_EXERCISE_FLAG);
        return new CreateExerciseProgrammeCommand(
            flagParser.getIndexByFlag(PROGRAMME_FLAG),
            flagParser.getIndexByFlag(DAY_FLAG),
            parseExercise(exerciseString)
        );
    }

    /**
     * Creates and returns a {@link DeleteExerciseProgrammeCommand} for removing an exercise from a day in a program.
     *
     * @param flagParser A {@link FlagParser} containing parsed flags and arguments.
     * @return A {@link DeleteExerciseProgrammeCommand} object to delete an existing exercise.
     * @throws IllegalArgumentException If required flags are missing.
     */
    private DeleteExerciseProgrammeCommand prepareDeleteExerciseCommand(FlagParser flagParser) {
        assert flagParser != null: "flagParser must not be null";

        flagParser.validateRequiredFlags(DAY_FLAG, REMOVE_EXERCISE_FLAG);
        return new DeleteExerciseProgrammeCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                flagParser.getIndexByFlag(DAY_FLAG),
                flagParser.getIndexByFlag(REMOVE_EXERCISE_FLAG)
        );
    }

    /**
     * Creates and returns a {@link CreateDayProgrammeCommand} for adding a new day to a program.
     *
     * @param flagParser A {@link FlagParser} containing parsed flags and arguments.
     * @return A {@link CreateDayProgrammeCommand} object to create a new day.
     */
    private CreateDayProgrammeCommand prepareCreateDayCommand(FlagParser flagParser) {
        assert flagParser != null: "flagParser must not be null";

        String dayString = flagParser.getStringByFlag(ADD_DAY_FLAG);
        return new CreateDayProgrammeCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                parseDay(dayString)
        );
    }

    /**
     * Creates and returns a {@link DeleteDayProgrammeCommand} for removing a day from a program.
     *
     * @param flagParser A {@link FlagParser} containing parsed flags and arguments.
     * @return A {@link DeleteDayProgrammeCommand} object to delete an existing day.
     * @throws IllegalArgumentException If required flags are missing.
     */
    private DeleteDayProgrammeCommand prepareDeleteDayCommand(FlagParser flagParser) {
        assert flagParser != null: "flagParser must not be null";

        flagParser.validateRequiredFlags(REMOVE_DAY_FLAG);
        return new DeleteDayProgrammeCommand(
                flagParser.getIndexByFlag(PROGRAMME_FLAG),
                flagParser.getIndexByFlag(REMOVE_DAY_FLAG)
        );
    }

    /**
     * Parses a string of day related arguments and returns a Day object.
     *
     * @param dayString the input string representing a day and its exercises, not null.
     * @return a Day object representing the parsed day and its exercises.
     * @throws IllegalArgumentException if there are missing arguments to create a day.
     */
    private  Day parseDay(String dayString) {
        assert dayString != null : "Day string must not be null";

        String[] dayParts  = dayString.split(EXERCISE_FLAG);
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

    /**
     * Parses an exercise string to create an {@link Exercise} object with required attributes.
     *
     * @author nirala-ts
     * @param argumentString The string containing exercise details and flags.
     * @return An {@link Exercise} object initialized with the specified attributes.
     * @throws IllegalArgumentException If required flags are missing.
     * */
    private Exercise parseExercise(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        FlagParser flagParser = new FlagParser(argumentString);

// Ensure the exercise contains all necessary information before creating a new Exercise
        flagParser.validateRequiredFlags(SETS_FLAG, REPS_FLAG, WEIGHT_FLAG, CALORIES_FLAG, NAME_FLAG);

        String name = flagParser.getStringByFlag(NAME_FLAG);
        int sets = flagParser.getIntegerByFlag(SETS_FLAG);
        int reps = flagParser.getIntegerByFlag(REPS_FLAG);
        int weight = flagParser.getIntegerByFlag(WEIGHT_FLAG);
        int calories = flagParser.getIntegerByFlag(MEAL_CALORIES);

        int sets = flagParser.getIntegerByFlag(SETS_FLAG);
        int reps = flagParser.getIntegerByFlag(REPS_FLAG);
        int weight = flagParser.getIntegerByFlag(WEIGHT_FLAG);
        int calories = flagParser.getIntegerByFlag(CALORIES_FLAG);
        String name = flagParser.getStringByFlag(NAME_FLAG);

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

