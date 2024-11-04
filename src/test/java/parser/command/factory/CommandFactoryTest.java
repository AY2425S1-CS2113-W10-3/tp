package parser.command.factory;

import command.Command;
import command.ExitCommand;
import command.InvalidCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CommandFactoryTest {

    private CommandFactory commandFactory;
    private ProgCommandFactory progFactory;
    private MealCommandFactory mealFactory;
    private WaterCommandFactory waterFactory;

    @BeforeEach
    void setUp() {
        progFactory = mock(ProgCommandFactory.class);
        mealFactory = mock(MealCommandFactory.class);
        waterFactory = mock(WaterCommandFactory.class);
        HistoryCommandFactory historyFactory = mock(HistoryCommandFactory.class);

        commandFactory = new CommandFactory(progFactory, mealFactory, waterFactory, historyFactory);
    }

    @Test
    void testCreateExitCommand() {
        Command command = commandFactory.createCommand("bye", "");
        assertInstanceOf(ExitCommand.class, command, "Expected ExitCommand for 'bye' command");
    }

    @Test
    void testCreateProgCommand() {
        Command expectedCommand = mock(Command.class);
        when(progFactory.parse("create")).thenReturn(expectedCommand);

        Command command = commandFactory.createCommand("prog", "create");
        assertEquals(expectedCommand, command, "Expected command created by ProgCommandFactory");
    }

    @Test
    void testCreateMealCommand() {
        Command expectedCommand = mock(Command.class);
        when(mealFactory.parse("log")).thenReturn(expectedCommand);

        Command command = commandFactory.createCommand("meal", "log");
        assertEquals(expectedCommand, command, "Expected command created by MealCommandFactory");
    }

    @Test
    void testCreateWaterCommand() {
        Command expectedCommand = mock(Command.class);
        when(waterFactory.parse("track")).thenReturn(expectedCommand);

        Command command = commandFactory.createCommand("water", "track");
        assertEquals(expectedCommand, command, "Expected command created by WaterCommandFactory");
    }

    @Test
    void testCreateInvalidCommand() {
        Command command = commandFactory.createCommand("unknownCommand", "someArgs");
        assertInstanceOf(InvalidCommand.class, command, "Expected InvalidCommand for unknown command");
    }
}
