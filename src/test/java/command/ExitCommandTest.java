package command;

import history.History;
import org.junit.jupiter.api.Test;
import programme.ProgrammeList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class ExitCommandTest {

    @Test
    public void testExecute_doesNotThrowException() {
        ProgrammeList mockProgrammes = mock(ProgrammeList.class);
        History mockHistory = mock(History.class);

        ExitCommand exitCommand = new ExitCommand();

        assertDoesNotThrow(() -> exitCommand.execute(mockProgrammes, mockHistory));
    }

    @Test
    public void testExecute_withNullProgrammeList() {
        History mockHistory = mock(History.class);

        ExitCommand exitCommand = new ExitCommand();

        assertDoesNotThrow(() -> exitCommand.execute(null, mockHistory));
    }

    @Test
    public void testExecute_withNullHistory() {
        ProgrammeList mockProgrammes = mock(ProgrammeList.class);

        ExitCommand exitCommand = new ExitCommand();

        assertDoesNotThrow( () -> exitCommand.execute(mockProgrammes, null));
    }
}
