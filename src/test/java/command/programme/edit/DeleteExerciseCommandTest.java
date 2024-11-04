package command.programme.edit;

import command.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.Day;
import programme.Exercise;
import programme.ProgrammeList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteExerciseCommandTest {

    private static final int VALID_PROGRAMME_ID = 0;
    private static final int VALID_DAY_ID = 0;
    private static final int VALID_EXERCISE_ID = 0;
    private static final int INVALID_PROGRAMME_ID = -2;
    private static final int INVALID_DAY_ID = -1;
    private static final int INVALID_EXERCISE_ID = -1;
    private static final int OUT_OF_RANGE_PROGRAMME_ID = 999;
    private static final int OUT_OF_RANGE_DAY_ID = 999;
    private static final int OUT_OF_RANGE_EXERCISE_ID = 999;

    private ProgrammeList programmeList;
    private Exercise exercise;
    private DeleteExerciseProgrammeCommand command;

    @BeforeEach
    void setUp() {
        // Set up a ProgrammeList with one programme, one day, and one exercise
        programmeList = new ProgrammeList();
        exercise = new Exercise(3, 10, 100, 200, "Deadlift");
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise);
        Day day = new Day("Day 1", exercises);

        ArrayList<Day> days = new ArrayList<>();
        days.add(day);
        programmeList.insertProgramme("Mock Programme", days);

        // Initialize DeleteExerciseCommand with valid IDs
        command = new DeleteExerciseProgrammeCommand(VALID_PROGRAMME_ID, VALID_DAY_ID, VALID_EXERCISE_ID);
    }

    // Test for constructor with valid inputs
    @Test
    void constructor_initializesWithValidParameters() {
        assertDoesNotThrow(() -> new DeleteExerciseProgrammeCommand(
                VALID_PROGRAMME_ID, VALID_DAY_ID, VALID_EXERCISE_ID)
        );
    }

    // Edge case for constructor: Negative programme ID
    @Test
    void constructor_throwsAssertionErrorIfProgrammeIdIsNegative() {
        assertThrows(AssertionError.class, () ->
                new DeleteExerciseProgrammeCommand(INVALID_PROGRAMME_ID, VALID_DAY_ID, VALID_EXERCISE_ID)
        );
    }

    // Edge case for constructor: Negative day ID
    @Test
    void constructor_throwsAssertionErrorIfdayIndexIsNegative() {
        assertThrows(AssertionError.class, () ->
                new DeleteExerciseProgrammeCommand(VALID_PROGRAMME_ID, INVALID_DAY_ID, VALID_EXERCISE_ID)
        );
    }

    // Edge case for constructor: Negative exercise ID
    @Test
    void constructor_throwsAssertionErrorIfexerciseIndexIsNegative() {
        assertThrows(AssertionError.class, () ->
                new DeleteExerciseProgrammeCommand(VALID_PROGRAMME_ID, VALID_DAY_ID, INVALID_EXERCISE_ID)
        );
    }

    // Test for execute method: successfully deletes exercise and returns success message
    @Test
    void execute_deletesExerciseFromDay_returnsSuccessMessage() {
        String expectedMessage = String.format(
                DeleteExerciseProgrammeCommand.SUCCESS_MESSAGE_FORMAT, VALID_EXERCISE_ID, exercise
        );
        CommandResult expectedResult = new CommandResult(expectedMessage);

        CommandResult actualResult = command.execute(programmeList);
        assertEquals(expectedResult, actualResult);
    }

    // Edge case for execute: Programme list is null
    @Test
    void execute_throwsAssertionErrorIfProgrammesIsNull() {
        assertThrows(AssertionError.class, () -> command.execute(null));
    }

    // Edge case for execute: Nonexistent programme ID
    @Test
    void execute_throwsIndexOutOfBoundsIfProgrammeIdDoesNotExist() {
        DeleteExerciseProgrammeCommand invalidCommand = new DeleteExerciseProgrammeCommand(
                OUT_OF_RANGE_PROGRAMME_ID, VALID_DAY_ID, VALID_EXERCISE_ID
        );
        assertThrows(IndexOutOfBoundsException.class, () -> invalidCommand.execute(programmeList));
    }

    // Edge case for execute: Nonexistent day ID within existing programme
    @Test
    void execute_throwsIndexOutOfBoundsIfdayIndexDoesNotExist() {
        DeleteExerciseProgrammeCommand invalidCommand = new DeleteExerciseProgrammeCommand(
                VALID_PROGRAMME_ID, OUT_OF_RANGE_DAY_ID, VALID_EXERCISE_ID
        );
        assertThrows(IndexOutOfBoundsException.class, () -> invalidCommand.execute(programmeList));
    }

    // Edge case for execute: Nonexistent exercise ID within existing day
    @Test
    void execute_handlesNonexistentexerciseIndexGracefully() {
        DeleteExerciseProgrammeCommand invalidCommand = new DeleteExerciseProgrammeCommand(
                VALID_PROGRAMME_ID, VALID_DAY_ID, OUT_OF_RANGE_EXERCISE_ID
        );
        assertThrows(IndexOutOfBoundsException.class, () -> invalidCommand.execute(programmeList));
    }
}
