//@@author nirala-ts
package parser;

import command.Command;
import parser.command.factory.CommandFactory;

import java.util.logging.Logger;
import java.util.logging.Level;

import static parser.ParserUtils.splitArguments;

/**
 * The {@code Parser} class serves as an intermediary between user input and command execution
 * within the BuffBuddy application. It interprets user-provided strings and generates appropriate
 * {@code Command} objects for execution.
 *
 * @author nirala-ts
 */
public class Parser {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final CommandFactory commandFactory;

    /**
     * Constructs a new {@code Parser} instance, initializing its associated {@code CommandFactory}.
     */
    public Parser() {
        this.commandFactory = new CommandFactory();
    }

    /**
     * Parses the given command string and returns the corresponding {@code Command} object.
     *
     * @param fullCommand The complete user input, containing the command and any arguments.
     * @return A {@code Command} object that represents the parsed command.
     * @throws IllegalArgumentException if the input is null or empty.
     */
    public Command parse(String fullCommand) {
        if (fullCommand == null || fullCommand.trim().isEmpty()) {
            logger.log(Level.WARNING, "Command is empty");
            throw new IllegalArgumentException("Command cannot be empty. Please enter a valid command.");
        }

        /*
         * Splits the full command input into the primary command and its associated arguments,
         * enabling identification of the command category.
         */
        String[] inputArguments = splitArguments(fullCommand);
        String commandString = inputArguments[0];
        String argumentString = inputArguments[1];

        logger.log(Level.INFO, "Successfully parsed command: {0}, with arguments: {1}",
                new Object[]{commandString, argumentString});

        return commandFactory.createCommand(commandString, argumentString);
    }
}
