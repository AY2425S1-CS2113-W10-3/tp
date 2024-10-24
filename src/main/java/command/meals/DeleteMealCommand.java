package command.meals;

import command.CommandResult;
import history.History;
import meal.MealList;

import java.time.LocalDate;

public class DeleteMealCommand extends MealCommand {
    public static final String COMMAND_WORD = "delete";

    protected final int indexMealToDelete;
    protected final LocalDate date;

    public DeleteMealCommand(int index, LocalDate date) {
        this.indexMealToDelete = index;
        this.date = date;
    }

    public CommandResult execute(History history) {
        MealList meals = getMealList(history);
        String mealToDeleteName = meals.deleteMeal(indexMealToDelete).toString();

        return new CommandResult( mealToDeleteName + " has been deleted");
    }

}
