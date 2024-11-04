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
 * Represents a command to add a meal to a specific date's daily record.
 */
public class AddMealCommand extends MealCommand {
    public static final String COMMAND_WORD = "add";
    private static final Logger logger = Logger.getLogger(AddMealCommand.class.getName());

    private final Meal mealToAdd;

    /**
     * Constructs an AddMealCommand with the specified meal and date.
     *
     * @param meal the meal to add to the daily record
     * @param date the date on which the meal should be added
     * @throws AssertionError if the meal is null
     */
    public AddMealCommand(Meal meal, LocalDate date) {
        super(date);
        assert meal != null : "Meal cannot be null";
        this.mealToAdd = meal;

        logger.log(Level.INFO, "AddMealCommand created with meal: {0} for date: {1}",
                new Object[]{meal, date});
    }

    /**
     * Executes the AddMealCommand, adding the specified meal to the daily record for the given date.
     *
     * @param history the history containing daily records where the meal will be added
     * @return a CommandResult indicating the success of the operation
     * @throws AssertionError if the daily record for the specified date is not found
     */
    public CommandResult execute(History history) {
        DailyRecord dailyRecord = history.getRecordByDate(date);
        assert dailyRecord != null : "Daily record not found";
        dailyRecord.addMealToRecord(mealToAdd);
        logger.log(Level.INFO, "Meal added: {0}", mealToAdd);

        return new CommandResult(mealToAdd.toString() + " has been added");
    }

    /**
     * Checks if this AddMealCommand is equal to another object.
     * Two AddMealCommand objects are considered equal if they have the same meal and date.
     *
     * @param o the object to compare with
     * @return true if the specified object is equal to this AddMealCommand, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AddMealCommand that)) {
            return false;
        }
        return Objects.equals(mealToAdd, that.mealToAdd) &&
                Objects.equals(date, that.date);
    }

    /**
     * Returns the hash code for AddMealCommand.
     * The hash code is based on the meal and date fields.
     *
     * @return the hash code value for this AddMealCommand
     */
    @Override
    public int hashCode() {
        return Objects.hash(mealToAdd, date);
    }
}
