package command.programme.edit;

import command.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.Day;
import programme.ProgrammeList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateDayCommandTest {

    private static final int VALID_PROGRAMME_ID = 0;
    private static final int INVALID_PROGRAMME_ID = -2;
    private static final int OUT_OF_RANGE_PROGRAMME_ID = 999;
    private ProgrammeList programme;
    private Day day;
    private CreateDayProgrammeCommand command;

    @BeforeEach
    void setUp() {
        // Creates a programmeList with one programme with no days
        programme = new ProgrammeList();
        programme.insertProgramme("Mock programme",new ArrayList<>());
        day = new Day("Day 1", new ArrayList<>());
        command = new CreateDayProgrammeCommand(VALID_PROGRAMME_ID, day);
    }

    // Test for the constructor with a happy path case
    @Test
    void constructor_initializesWithValidProgrammeIdAndDay() {
        assertDoesNotThrow(() -> new CreateDayProgrammeCommand(VALID_PROGRAMME_ID, day));
    }

    // Edge case for the constructor: Negative programme ID
    @Test
    void constructor_throwsExceptionIfProgrammeIdIsNegative() {
        assertThrows(AssertionError.class, () -> new CreateDayProgrammeCommand(INVALID_PROGRAMME_ID, day));
    }

    // Edge case for the constructor: Day is null
    @Test
    void constructor_throwsExceptionIfDayIsNull() {
        assertThrows(AssertionError.class, () -> new CreateDayProgrammeCommand(VALID_PROGRAMME_ID, null));
    }

    // Test for the "execute" method with a happy path case
    @Test
    void execute_addsDayToProgrammeList_returnsSuccessMessage() {
        String expectedMessage = String.format(CreateDayProgrammeCommand.SUCCESS_MESSAGE_FORMAT, day);
        CommandResult expectedResult = new CommandResult(expectedMessage);

        CommandResult actualResult = command.execute(programme);
        assertEquals(expectedResult, actualResult);
    }

    // Edge case for the "execute" method: Programmes list is null
    @Test
    void execute_throwsAssertionErrorIfProgrammesIsNull() {
        assertThrows(AssertionError.class, () -> command.execute(null));
    }

    // Edge case for the "execute" method: Programme list does not contain programme ID
    @Test
    void execute_handlesNonexistentProgrammeIdGracefully() {
        CreateDayProgrammeCommand invalidCommand = new CreateDayProgrammeCommand(OUT_OF_RANGE_PROGRAMME_ID, day);
        assertThrows(IndexOutOfBoundsException.class, () -> invalidCommand.execute(programme));
    }
}
