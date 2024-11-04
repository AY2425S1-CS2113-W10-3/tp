package parser;

import command.Command;
import command.InvalidCommand;
import parser.command.factory.CommandFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ParserTest {

    private Parser parser;
    private CommandFactory commandFactory;

    @BeforeEach
    void setUp() {
        parser = new Parser();
        commandFactory = mock(CommandFactory.class);
    }

    @Test
    void testParseValidCommand() {
        Command mockCommand = mock(Command.class);
        when(commandFactory.createCommand("test", "argument")).thenReturn(mockCommand);

        Command command = parser.parse("test argument");
        assertNotNull(command);
    }

    @Test
    void testParseInvalidCommand() {
        Command command = parser.parse("unknownCommand");
        assertInstanceOf(InvalidCommand.class, command, "Expected InvalidCommand for unknown command");
    }

    @Test
    void testParseEmptyCommand() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(""),
                "Should throw exception on empty command");
    }

    @Test
    void testParseOnlySpacesCommand() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("   "),
                "Should throw exception on empty command");
    }

    @Test
    void testParseNullCommand() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(null),
                "Should throw exception on empty command");
    }
}
