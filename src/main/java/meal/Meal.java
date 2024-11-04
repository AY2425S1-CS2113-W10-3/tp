// @@author Atulteja

package meal;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a meal with a name and calorie count.
 */
public class Meal {

    private static final Logger logger = Logger.getLogger(Meal.class.getName());

    private int calories;
    private  String name;

    /**
     * Constructs a Meal with the specified name and calorie count.
     *
     * @param name     the name of the meal
     * @param calories the calorie count of the meal
     * @throws AssertionError if the name is null, empty, or if the calories are negative
     */
    public Meal(String name, int calories) {
        assert name != null && !name.isEmpty() : "Meal name cannot be null or empty";
        assert calories >= 0 : "Calories cannot be negative";

        this.name = name;
        this.calories = calories;

        logger.log(Level.INFO, "Meal created: {0} with {1} kcal", new Object[]{name, calories});
    }

    public int getCalories() {
        return calories;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, name);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Meal meal = (Meal) o;
        return calories == meal.calories &&
                Objects.equals(name, meal.name);
    }

    /**
     * Overridden to return a string representation of the meal, including its name and calorie count.
     *
     * @return a string representation of the meal
     */
    @Override
    public String toString() {
        return name + " | " + calories + "kcal";
    }
}
