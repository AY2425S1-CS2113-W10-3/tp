package command.programme;

import command.CommandResult;
import history.History;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.Day;
import programme.ProgrammeList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ListProgrammeCommandTest {

    private ProgrammeList programmeList;
    private History history;
    private ListProgrammeCommand command;

    @BeforeEach
    void setUp() {
        programmeList = new ProgrammeList();
        history = new History();
        command = new ListProgrammeCommand();
        
        // Add some programmes to the list for testing
        ArrayList<Day> days1 = new ArrayList<>();
        days1.add(new Day("Day 1", new ArrayList<>()));
        programmeList.insertProgramme("Programme 1", days1);

        ArrayList<Day> days2 = new ArrayList<>();
        days2.add(new Day("Day 2", new ArrayList<>()));
        programmeList.insertProgramme("Programme 2", days2);
    }

    @Test
    void execute_returnsListOfProgrammes() {
        CommandResult result = command.execute(programmeList, history);
        assertNotNull(result);
        String expectedMessage = String.format(
            ListProgrammeCommand.SUCCESS_MESSAGE_FORMAT, 
            programmeList
        );
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void execute_returnsEmptyListWhenNoProgrammes() {
        programmeList = new ProgrammeList(); // Reset to empty list
        CommandResult result = command.execute(programmeList, history);
        assertNotNull(result);
        String expectedMessage = String.format(
            ListProgrammeCommand.SUCCESS_MESSAGE_FORMAT,
            "No programmes found."
        );
        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void execute_handlesNullProgrammeList() {
        assertThrows(AssertionError.class, () -> {
            command.execute(null, history);
        });
    }
}

