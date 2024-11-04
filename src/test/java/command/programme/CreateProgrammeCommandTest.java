package command.programme;

import command.CommandResult;
import history.History;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.Day;
import programme.Programme;
import programme.ProgrammeList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateProgrammeCommandTest {

    private static final String VALID_PROGRAMME_NAME = "New Programme";
    private static final String EMPTY_PROGRAMME_NAME = "";
    private static final ArrayList<Day> VALID_PROGRAMME_CONTENTS = new ArrayList<>();
    private static final ArrayList<Day> NULL_PROGRAMME_CONTENTS = null;
    private ProgrammeList programmeList;
    private History history;
    private CreateProgrammeCommand command;

    @BeforeEach
    void setUp() {
        programmeList = new ProgrammeList();
        history = new History();
    }

    @Test
    void constructor_initializesWithValidParameters(){
        assertDoesNotThrow(() -> new CreateProgrammeCommand(VALID_PROGRAMME_NAME, VALID_PROGRAMME_CONTENTS));
    }

    // Edge case test: Programme list is null
    @Test
    void constructor_throwsAssertionErrorIfProgrammesIsNull() {
        assertThrows(AssertionError.class, () -> new CreateProgrammeCommand(
                VALID_PROGRAMME_NAME, NULL_PROGRAMME_CONTENTS)
        );
    }

    // Edge case test: Programme name is empty
    @Test
    void constructor_createsProgrammeWithEmptyName() {
        assertThrows(AssertionError.class, () -> new CreateProgrammeCommand(
                EMPTY_PROGRAMME_NAME, VALID_PROGRAMME_CONTENTS)
        );
    }

    // Happy path test for the "execute" method
    @Test
    void execute_createsProgrammeSuccessfully_returnsSuccessMessage() {
        CreateProgrammeCommand command = new CreateProgrammeCommand(VALID_PROGRAMME_NAME, VALID_PROGRAMME_CONTENTS);
        String expectedMessage = String.format(CreateProgrammeCommand.SUCCESS_MESSAGE_FORMAT,
                new Programme(VALID_PROGRAMME_NAME, VALID_PROGRAMME_CONTENTS)
        );
        CommandResult expectedResult = new CommandResult(expectedMessage);

        CommandResult actualResult = command.execute(programmeList, history);
        assertEquals(expectedResult, actualResult);
    }


}
