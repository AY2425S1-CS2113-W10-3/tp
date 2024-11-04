package command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandResultTest {

    @Test
    public void testCommandResult_initialization() {
        String message = "Operation successful";
        CommandResult commandResult = new CommandResult(message);

        assertNotNull(commandResult, "CommandResult should be initialized");
        assertEquals(message, commandResult.getMessage(), "The message should match the initialized value");
    }

    @Test
    public void testCommandResult_emptyMessage() {
        CommandResult commandResult = new CommandResult("");

        assertNotNull(commandResult, "CommandResult should be initialized");
        assertEquals("", commandResult.getMessage(), "The message should be an empty string");
    }

    @Test
    public void testCommandResult_nullMessage() {
        CommandResult commandResult = new CommandResult(null);

        assertNotNull(commandResult, "CommandResult should be initialized");
        assertEquals(null, commandResult.getMessage(), "The message should be null");
    }
}
