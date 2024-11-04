package ui;

import command.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UiTest {

    private Ui ui;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        ui = new Ui();
    }

    @Test
    void testShowWelcome() {
        ui.showWelcome();
        String output = outputStream.toString();
        assertTrue(output.contains("Hello! I'm..."));
        outputStream.reset();
    }

    @Test
    void testShowFarewell() {
        ui.showFarewell();
        String output = outputStream.toString();
        assertTrue(output.contains("Bye. Hope to see you again soon!"));
        outputStream.reset();
    }

    @Test
    void testShowMessageWithString() {
        String testMessage = "This is a test message";
        ui.showMessage(testMessage);
        String output = outputStream.toString();
        assertTrue(output.contains(testMessage));
        outputStream.reset();
    }

    @Test
    void testShowMessageWithException() {
        Exception testException = new Exception("Test exception message");
        ui.showMessage(testException);
        String output = outputStream.toString();
        assertTrue(output.contains("Error: Test exception message")); // Verify it includes the ERROR_HEADER
        outputStream.reset();
    }

    @Test
    void testShowMessageWithCommandResult() {
        CommandResult testResult = new CommandResult("Test command result message");
        ui.showMessage(testResult);
        String output = outputStream.toString();
        assertTrue(output.contains("Test command result message"));
        outputStream.reset();
    }
}
