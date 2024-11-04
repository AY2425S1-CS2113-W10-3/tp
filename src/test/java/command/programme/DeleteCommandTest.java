package command.programme;

import command.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.ProgrammeList;
import programme.Day;
import programme.Programme;
import history.History;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteProgrammeCommandTest {

    private static final int VALID_PROGRAMME_ID = 0;
    private static final int INVALID_PROGRAMME_ID = -2;
    private static final int OUT_OF_RANGE_PROGRAMME_ID = 999;

    private ProgrammeList programmeList;
    private Programme expectedProgramme;
    private Day day;
    private DeleteProgrammeCommand command;
    private History history;
    
    @BeforeEach
    void setUp() {
        // Set up a ProgrammeList with one programme and one day
        programmeList = new ProgrammeList();
        history = new History();
        day = new Day("Day 1", new ArrayList<>());
        ArrayList<Day> days = new ArrayList<>();
        days.add(day);
        programmeList.insertProgramme("Mock Programme", days);

        expectedProgramme = new Programme("Mock Programme", days);
        // Initialize DeleteCommand with valid IDs
        command = new DeleteProgrammeCommand(VALID_PROGRAMME_ID);
    }

    // Test for constructor with valid inputs
    @Test
    void constructor_initializesWithValidParameters() {
        assertDoesNotThrow(() -> new DeleteProgrammeCommand(VALID_PROGRAMME_ID));
    }

    // Edge case for constructor: invalid programme ID
    @Test
    void constructor_throwsAssertionErrorIfProgrammeIdIsNegative() {
        assertThrows(AssertionError.class, () -> new DeleteProgrammeCommand(INVALID_PROGRAMME_ID));
    }

    // Test for execute method: successfully deletes day and returns success message
    @Test
    void execute_deletesDayFromProgramme_returnsSuccessMessage() {
        String expectedMessage = String.format(DeleteProgrammeCommand.SUCCESS_MESSAGE_FORMAT, expectedProgramme);
        CommandResult expectedResult = new CommandResult(expectedMessage);

        CommandResult actualResult = command.execute(programmeList, history);
        assertEquals(expectedResult, actualResult);
    }

    // Edge case for execute: Nonexistent programme ID
    @Test
    void execute_throwsIndexOutOfBoundsIfProgrammeIdDoesNotExist() {
        DeleteProgrammeCommand invalidCommand = new DeleteProgrammeCommand(OUT_OF_RANGE_PROGRAMME_ID);
        assertThrows(IndexOutOfBoundsException.class, () -> invalidCommand.execute(programmeList, history));
    }
}
