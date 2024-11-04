// @@author Atulteja

package meal;

import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents a list of meals, providing functionality to add, delete, and retrieve meals.
 */
public class MealList {
    private static final Logger logger = Logger.getLogger(MealList.class.getName());
    private ArrayList<Meal> meals;

    public MealList() {
        meals = new ArrayList<>();
        logger.log(Level.INFO, "MealList created with an empty list.");
    }

    public boolean isEmpty() {
        return meals.isEmpty();
    }

    public int getSize() {
        return meals.size();
    }

    /**
     * Adds a meal to the list.
     *
     * @param meal the meal to add to the list
     * @throws AssertionError if the meal is null
     */
    public void addMeal(Meal meal) {
        assert meal != null : "Meal cannot be null";


        meals.add(meal);
        logger.log(Level.INFO, "Added meal: {0}. Current list: {1}", new Object[]{meal, meals});
    }

    /**
     * Deletes a meal from the list at the specified index.
     *
     * @param index the index of the meal to delete
     * @return the meal that was deleted
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Meal deleteMeal(int index) {
        if (index < 0 || index >= meals.size()) {
            logger.log(Level.WARNING, "Invalid index for deletion: {0}", index);
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for the meal list.");
        }

        Meal mealToBeDeleted = meals.get(index);
        meals.remove(index);
        logger.log(Level.INFO, "Deleted meal: {0} at index {1}. Current list: {2}",
                new Object[]{mealToBeDeleted, index, meals});
        return mealToBeDeleted;
    }

    public ArrayList<Meal> getMeals() {
        logger.log(Level.INFO, "Retrieved meal list: {0}", meals);
        return meals;
    }

    /**
     * Overrides the toString to returns a string representation of the meal list.
     * Each meal is represented by its index and details on a new line.
     *
     * @return a string representation of the meal list
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int count = 1;

        for (Meal meal : meals) {
            output.append(count).append(": ").append(meal).append("\n");
            count++;
        }

        return output.toString().trim(); // Trim to remove trailing newline
    }

    @Override
    public int hashCode() {
        return Objects.hash(meals);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MealList meallist = (MealList) o;
        return meals == meallist.meals;
    }
}
