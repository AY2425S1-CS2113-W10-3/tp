package command;

import history.History;
import org.junit.jupiter.api.Test;
import programme.ProgrammeList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class CommandTest {

    private static class TestCommand extends Command {
        @Override
        public CommandResult execute(ProgrammeList programmes, History history) {
            return new CommandResult("Test executed");
        }
    }

    @Test
    public void testExecute_returnsCommandResult() {
        ProgrammeList mockProgrammes = mock(ProgrammeList.class);
        History mockHistory = mock(History.class);

        TestCommand testCommand = new TestCommand();
        CommandResult result = testCommand.execute(mockProgrammes, mockHistory);

        assertNotNull(result, "The execute method should return a non-null CommandResult");
    }
}
