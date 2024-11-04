// @@author Atulteja
package command.meals;

import command.Command;
import command.CommandResult;
import history.History;
import programme.ProgrammeList;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an abstract command related to meals, allowing various meal-related operations.
 * This class serves as a base for specific meal commands like adding or deleting a meal.
 */
public abstract class MealCommand extends Command {

    private static final Logger logger = Logger.getLogger(MealCommand.class.getName());

    protected LocalDate date;

    /**
     * Constructs a MealCommand with the specified date.
     *
     * @param date the date associated with this command
     * @throws AssertionError if the date is null
     */
    public MealCommand(LocalDate date) {
        assert date != null : "Date cannot be null";
        this.date = date;
        logger.log(Level.INFO, "MealCommand initialized with date: {0}", date);
    }

    /**
     * Executes the meal-related command using the provided history.
     * Subclasses should implement this method to perform their specific command logic.
     *
     * @param history the history containing daily records where the command will be executed
     * @return a CommandResult indicating the success or outcome of the operation
     */
    public abstract CommandResult execute(History history);

    /**
     * Executes the meal-related command using both the provided programme list and history.
     * This implementation only uses the history for meal commands.
     *
     * @param programmes the programme list, currently unused in this implementation
     * @param history the history containing daily records where the command will be executed
     * @return a CommandResult indicating the success or outcome of the operation
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history) {
        logger.log(Level.INFO, "Executing MealCommand with ProgrammeList and History.");
        return execute(history);
    }
}
