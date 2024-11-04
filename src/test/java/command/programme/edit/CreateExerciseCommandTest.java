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

class CreateExerciseCommandTest {

    private static final int VALID_PROGRAMME_ID = 0;
    private static final int VALID_DAY_ID = 0;

    private static final int INVALID_PROGRAMME_ID = -2;
    private static final int INVALID_DAY_ID = -1;
    private static final int OUT_OF_RANGE_PROGRAMME_ID = 999;
    private static final int OUT_OF_RANGE_DAY_ID = 999;

    private ProgrammeList programmeList;
    private Exercise exercise;
    private CreateExerciseProgrammeCommand command;

    @BeforeEach
    void setUp() {
        // Creates a ProgrammeList with a single programme and one day
        programmeList = new ProgrammeList();
        ArrayList<Day> days = new ArrayList<>();
        Day day = new Day("Day 1", new ArrayList<>());
        days.add(day);
        programmeList.insertProgramme("Mock Programme", days);

        // Initialize the Exercise and CreateExerciseCommand with valid IDs
        exercise = new Exercise(3, 10, 100, 200, "Deadlift");
        command = new CreateExerciseProgrammeCommand(VALID_PROGRAMME_ID, VALID_DAY_ID, exercise);
    }

    // Test for the constructor with valid programme and day IDs and exercise
    @Test
    void constructor_initializesWithValidParameters() {
        assertDoesNotThrow(() -> new CreateExerciseProgrammeCommand(VALID_PROGRAMME_ID, VALID_DAY_ID, exercise));
    }

    // Edge case for the constructor: Negative programme ID
    @Test
    void constructor_throwsAssertionErrorIfProgrammeIdIsInvalid() {
        assertThrows(AssertionError.class, () ->
                new CreateExerciseProgrammeCommand(INVALID_PROGRAMME_ID, VALID_DAY_ID, exercise)
        );
    }

    // Edge case for the constructor: Negative day ID
    @Test
    void constructor_throwsAssertionErrorIfdayIndexIsNegative() {
        assertThrows(AssertionError.class, () ->
                new CreateExerciseProgrammeCommand(VALID_PROGRAMME_ID, INVALID_DAY_ID, exercise)
        );
    }

    // Edge case for the constructor: Exercise is null
    @Test
    void constructor_throwsAssertionErrorIfExerciseIsNull() {
        assertThrows(AssertionError.class, () ->
                new CreateExerciseProgrammeCommand(VALID_PROGRAMME_ID, VALID_DAY_ID, null)
        );
    }

    // Test for the execute method with a valid programme and day ID
    @Test
    void execute_addsExerciseToDay_returnsSuccessMessage() {
        String expectedMessage = String.format(CreateExerciseProgrammeCommand.SUCCESS_MESSAGE_FORMAT, exercise);
        CommandResult expectedResult = new CommandResult(expectedMessage);

        CommandResult actualResult = command.execute(programmeList);
        assertEquals(expectedResult, actualResult);
    }

    // Edge case for the execute method: Programme list is null
    @Test
    void execute_throwsAssertionErrorIfProgrammesIsNull() {
        assertThrows(AssertionError.class, () -> command.execute(null));
    }

    // Edge case for the execute method: Nonexistent programme ID
    @Test
    void execute_throwsIndexOutOfBoundsIfProgrammeIdDoesNotExist() {
        CreateExerciseProgrammeCommand invalidCommand = new CreateExerciseProgrammeCommand(
                OUT_OF_RANGE_PROGRAMME_ID, VALID_DAY_ID, exercise
        );
        assertThrows(IndexOutOfBoundsException.class, () -> invalidCommand.execute(programmeList));
    }

    // Edge case for the execute method: Nonexistent day ID within an existing programme
    @Test
    void execute_throwsIndexOutOfBoundsIfdayIndexDoesNotExist() {
        CreateExerciseProgrammeCommand invalidCommand = new CreateExerciseProgrammeCommand(
                VALID_PROGRAMME_ID, OUT_OF_RANGE_DAY_ID, exercise
        );
        assertThrows(IndexOutOfBoundsException.class, () -> invalidCommand.execute(programmeList));
    }
}
