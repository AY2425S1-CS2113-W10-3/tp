// @@author Atulteja
package command.meals;

import command.CommandResult;
import common.Utils;
import history.DailyRecord;
import history.History;
import meal.MealList;
import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a command to view meals for a specific date.
 */
public class ViewMealCommand extends MealCommand {
    public static final String COMMAND_WORD = "view";
    private static final Logger logger = Logger.getLogger(ViewMealCommand.class.getName());

    /**
     * Constructs a ViewMealCommand for the specified date.
     *
     * @param date the date for which meals should be viewed
     */
    public ViewMealCommand(LocalDate date) {
        super(date);

        logger.log(Level.INFO, "ViewMealCommand created for date: {0}", date);
    }

    /**
     * Executes the ViewMealCommand, retrieving the meals from the daily record for the specified date.
     *
     * @param history the history containing daily records where the meal list will be retrieved
     * @return a CommandResult containing a string representation of the meals for the given date
     * @throws AssertionError if the daily record for the specified date is not found
     */
    public CommandResult execute(History history) {
        logger.log(Level.INFO, "Executing ViewMealCommand for date: {0}", date);
        String formattedDate = Utils.formatDate(date);

        DailyRecord dailyRecord = history.getRecordByDate(date);
        assert dailyRecord != null : "Daily record not found";
        MealList meals = dailyRecord.getMealListFromRecord();

        logger.log(Level.INFO, "Retrieved MealList for date {0}: {1}", new Object[]{date, meals});

        return new CommandResult("Meals for " + formattedDate +  ": \n\n" + meals.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewMealCommand that)) {
            return false;
        }
        return Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
