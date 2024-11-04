//@@author Bev-Low
package history;

import meal.Meal;
import meal.MealList;
import programme.Day;
import water.Water;

import java.util.logging.Logger;

/**
 * Represents a daily record containing a {@link Day} for workouts, a {@link MealList} for meals,
 * and a {@link Water} record for water intake.
 * <p>
 * Each {@code DailyRecord} logs a day's activities, meals, and water intake, supporting methods
 * for managing these entries and calculating totals. Instances are stored in a {@code history}
 * map that associates each record with a specific date.
 * </p>
 */
public class DailyRecord {
    private static final Logger logger = Logger.getLogger(DailyRecord.class.getName());
    private Day day;
    private final MealList mealList;
    private final Water water;

    /**
     * Constructs a daily record with an empty meal list and water intake record.
     */
    public DailyRecord() {
        this.mealList = new MealList();
        this.water = new Water();
    }

    /**
     * Retrieves the day object representing the day's workout or activities.
     *
     * @return the {@code Day} from the record, or {@code null} if no day is recorded
     */
    public Day getDayFromRecord() {
        return day;
    }

    //@@author TVageesan
    /**
     * Deletes the current day record from the daily record.
     * <p>
     * If no day has been logged, this method throws an {@link IllegalStateException}.
     * </p>
     *
     * @return the deleted {@code Day} object
     * @throws IllegalStateException if there is no logged workout for the day
     */
    public Day deleteDayFromRecord() {
        if (this.day == null) {
            throw new IllegalStateException("No logged workout found for this day.");
        }

        Day deleted = this.day;
        this.day = null;
        return deleted;
    }
    //@@author

    /**
     * Retrieves the mealList object containing all meals recorded for the day.
     *
     * @return the {@code MealList} for the daily record
     */
    public MealList getMealListFromRecord() {
        return mealList;
    }

    /**
     * Retrieves the water object containing a water list.
     *
     * @return the {@code Water} intake record for the day
     */
    public Water getWaterFromRecord() {
        return water;
    }

    /**
     * Logs a new {@code Day} to the daily record, replacing any existing record for that day.
     * <p>
     * This method updates the day's activities with the given {@code Day} object,
     * ensuring it is non-null, and logs the update.
     * </p>
     *
     * @param newDay the {@code Day} object to log, which must not be {@code null}
     */
    public void logDayToRecord(Day newDay) { //this replaces any current day recorded
        assert newDay != null : "day must not be null";

        this.day = newDay;
        logger.info("Day updated: " + day);
    }

    /**
     * Adds a meal to the daily meal record.
     * <p>
     * This method appends the given meal to the {@code mealList} and logs the addition.
     * </p>
     *
     * @param meal the {@code Meal} to add to the record, which must not be {@code null}
     */
    public void addMealToRecord(Meal meal) {
        assert meal != null;

        mealList.addMeal(meal);
        logger.info("meal added: " + meal);
    }

    /**
     * Deletes a meal from the mealList at the specified index.
     * <p>
     * This method removes the meal at the given index from the {@code mealList}.
     * </p>
     *
     * @param index the index of the meal to delete, which must be non-negative
     * @return the {@link Meal} that was deleted from the record
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Meal deleteMealFromRecord(int index) {
        assert index >= 0;

        logger.info("meal deleted, index: " + index);
        return mealList.deleteMeal(index);
    }

    /**
     * Adds a specified amount of water to the daily water intake record.
     * <p>
     * This method appends the given amount of water to the water record, ensuring the
     * amount is non-negative.
     * </p>
     *
     * @param toAddWater the amount of water to add, which must be non-negative
     */
    public void addWaterToRecord(float toAddWater) {
        assert toAddWater >= 0;

        water.addWater(toAddWater);
        logger.info("Water added: " + toAddWater);
    }

    /**
     * Removes a water entry from the water intake record at the specified index.
     * <p>
     * This method deletes the water entry at the given index from the water intake record
     * and returns the removed water amount.
     * </p>
     *
     * @param index the index of the water entry to delete, which must be non-negative
     * @return the amount of water that was removed
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public float removeWaterFromRecord(int index) {
        logger.info("water deleted, index: " + index);
        return water.deleteWater(index);
    }

    /**
     * Calculates the total calories from all meals in the {@code mealList}.
     * <p>
     * This method iterates over each {@link Meal} in the {@code mealList}, ensuring each meal is
     * non-null, and sums up the calories from each meal to get the total caloric intake from meals.
     * </p>
     *
     * @return the total calories gained from meals
     */
    private int getCaloriesFromMeals() {
        int caloriesMeal = 0;
        for (Meal meal : mealList.getMeals()) {
            assert meal != null : "meal must not be null";
            caloriesMeal += meal.getCalories();
        }
        logger.info("Calories from meals calculated: " + caloriesMeal);
        return caloriesMeal;
    }

    /**
     * Calculates the total water intake from all recorded water entries in {@code water}.
     * <p>
     * This method iterates over each water entry in {@code water.getWaterList()}, ensuring each entry
     * is non-null, and accumulates the total amount of water consumed.
     * </p>
     *
     * @return the total water intake in liters
     */
    private float getTotalWaterIntake() {
        float totalWater = 0;
        for (Float waterAmount : water.getWaterList()) {
            assert waterAmount != null : "water must not be null";
            totalWater += waterAmount;
        }
        logger.info("total water: " + totalWater);
        return totalWater;
    }

    /**
     * Provides a detailed string representation of the daily record, including the day's
     * activities, meals consumed, water intake, and caloric balance.
     * <p>
     * This method compiles information from the {@code day}, {@code mealList}, and {@code water}
     * components to create a summary of the daily record. It includes:
     * </p>
     * <ul>
     *     <li>The day’s activities and total calories burned, if available.</li>
     *     <li>A list of meals and the total calories gained from meals, if any meals are recorded.</li>
     *     <li>The total water intake, if any water entries are recorded.</li>
     * </ul>
     * <p>
     * If any of the components (day, meals, or water) are absent, it indicates this in the output.
     * Additionally, it calculates and displays the caloric balance (calories gained minus calories burned).
     * </p>
     *
     * @return a formatted string representation of the daily record, detailing calories burned, calories
     *         gained from meals, water intake, and overall caloric balance.
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int caloriesBurnt = 0;
        int caloriesGained = getCaloriesFromMeals();

        result.append("Day: \n");
        if (day != null) {
            caloriesBurnt = day.getTotalCaloriesBurnt();
            result.append(day.toString()).append("\n");
            result.append("Total Calories burnt: ").append(caloriesBurnt).append(" kcal\n\n");
        } else {
            result.append("No Day.\n\n");
        }

        result.append("Meals: \n");
        if (!mealList.getMeals().isEmpty()) {
            result.append(mealList).append("\n");
            result.append("Total Calories from Meals: ").append(caloriesGained).append(" kcal\n\n");
        } else {
            result.append("No Meals.\n\n");
        }

        result.append("Water Intake: \n");
        if (!water.getWaterList().isEmpty()) {
            result.append(water).append("\n");
            result.append("Total Water Intake: ").append(getTotalWaterIntake()).append(" liters \n\n");
        } else {
            result.append("No Water.\n\n");
        }

        result.append("Caloric Balance: ").append(caloriesGained - caloriesBurnt).append(" kcal");
        return result.toString();
    }
}
