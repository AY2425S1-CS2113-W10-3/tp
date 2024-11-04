// @@author nirala-ts
package parser.command.factory;

import command.Command;
import command.ExitCommand;
import command.InvalidCommand;

/**
 * The {@code CommandFactory} class is responsible for creating instances of various {@code Command} objects
 * based on the specified command string. It serves as a central factory that distributes command creation
 * requests to specific factories for subcommands like "prog," "history," and others.
 * For unsupported commands, it returns an {@code InvalidCommand} to handle erroneous input.
 *
 * <p>Supported Commands:</p>
 * <ul>
 *     <li>Program Commands - handled by {@code ProgCommandFactory}</li>
 *     <li>Meal Commands - handled by {@code MealCommandFactory}</li>
 *     <li>Water Commands - handled by {@code WaterCommandFactory}</li>
 *     <li>History Commands - handled by {@code HistoryCommandFactory}</li>
 *     <li>Exit Command - creates an {@code ExitCommand} directly</li>
 *     <li>Invalid Command - returns an {@code InvalidCommand} for unknown inputs</li>
 * </ul>
 *
 * @author nirala-ts
 */
public class CommandFactory {
    private final ProgCommandFactory progFactory;
    private final MealCommandFactory mealFactory;
    private final WaterCommandFactory waterFactory;
    private final HistoryCommandFactory historyFactory;

    /**
     * Constructs a {@code CommandFactory} and initializes subcommand factories for handling
     * specific types of commands.
     */
    public CommandFactory() {
        this.progFactory = new ProgCommandFactory();
        this.mealFactory = new MealCommandFactory();
        this.waterFactory = new WaterCommandFactory();
        this.historyFactory = new HistoryCommandFactory();
    }

    /**
     * Constructor for dependency injection, allowing custom instances of subcommand factories
     * for testing with mock objects.
     *
     * @param progFactory the {@code ProgCommandFactory} instance to handle "prog" commands
     * @param mealFactory the {@code MealCommandFactory} instance to handle "meal" commands
     * @param waterFactory the {@code WaterCommandFactory} instance to handle "water" commands
     * @param historyFactory the {@code HistoryCommandFactory} instance to handle "history" commands
     */
    public CommandFactory(ProgCommandFactory progFactory, MealCommandFactory mealFactory,
                          WaterCommandFactory waterFactory, HistoryCommandFactory historyFactory) {
        this.progFactory = progFactory;
        this.mealFactory = mealFactory;
        this.waterFactory = waterFactory;
        this.historyFactory = historyFactory;
    }

    /**
     * Creates and returns the appropriate {@code Command} object based on the provided command string.
     * Delegates command parsing to the relevant subcommand factory when available. Returns an
     * {@code ExitCommand} for exit requests and an {@code InvalidCommand} for unsupported commands.
     *
     * @param commandString The main command word indicating the type of command.
     * @param argumentString The arguments provided for the command, if any.
     * @return A {@code Command} object corresponding to the parsed command.
     */
    public Command createCommand(String commandString, String argumentString) {
        assert commandString != null;

        return switch (commandString) {
        case ProgCommandFactory.COMMAND_WORD -> progFactory.parse(argumentString);
        case MealCommandFactory.COMMAND_WORD -> mealFactory.parse(argumentString);
        case WaterCommandFactory.COMMAND_WORD -> waterFactory.parse(argumentString);
        case HistoryCommandFactory.COMMAND_WORD -> historyFactory.parse(argumentString);
        case ExitCommand.COMMAND_WORD -> new ExitCommand();
        default -> new InvalidCommand();
        };
    }
}
