// @@author Atulteja
package command.meals;

import command.CommandResult;
import history.DailyRecord;
import history.History;
import meal.Meal;

import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to delete a meal from a specific date's daily record.
 */
public class DeleteMealCommand extends MealCommand {
    public static final String COMMAND_WORD = "delete";
    private static final Logger logger = Logger.getLogger(DeleteMealCommand.class.getName());

    private final int indexMealToDelete;

    /**
     * Constructs a DeleteMealCommand with the specified meal index and date.
     *
     * @param index the index of the meal to delete from the daily record
     * @param date  the date from which the meal should be deleted
     * @throws AssertionError if the index is negative
     */
    public DeleteMealCommand(int index, LocalDate date) {
        super(date);

        assert index >= 0 : "Index to delete cannot be negative";

        this.indexMealToDelete = index;

        logger.log(Level.INFO, "DeleteMealCommand created for index: {0} on date: {1}",
                new Object[]{index, date});
    }

    /**
     * Executes the DeleteMealCommand, deleting the specified meal from the daily record for the given date.
     *
     * @param history the history containing daily records where the meal will be deleted
     * @return a CommandResult indicating the success of the operation
     * @throws AssertionError if the daily record for the specified date is not found
     */
    public CommandResult execute(History history) {
        DailyRecord dailyRecord = history.getRecordByDate(date);
        assert dailyRecord != null : "Daily record not found";
        Meal deletedMeal = dailyRecord.deleteMealFromRecord(indexMealToDelete);

        return new CommandResult(deletedMeal + " has been deleted");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeleteMealCommand)) {
            return false;
        }
        DeleteMealCommand that = (DeleteMealCommand) o;
        return indexMealToDelete == that.indexMealToDelete &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexMealToDelete, date);
    }
}
