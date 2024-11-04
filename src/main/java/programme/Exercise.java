// @@author Atulteja
package programme;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static common.Utils.isNull;

public class Exercise {
    private static final Logger logger = Logger.getLogger(Exercise.class.getName());

    private int sets;
    private int reps;
    private int weight;
    private int calories;
    private String name;

    public Exercise(int sets, int reps, int weight, int calories, String name) {
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.calories = calories;
        this.name = name;

        logger.log(Level.INFO, "Exercise created: {0}", this);
    }

    // @@author TVageesan

    /**
     * Updates the current Exercise's fields based on the non-null values in the provided ExerciseUpdate object.
     * <p>
     * For each non-null field in the UpdateExercise object, the corresponding field in this Exercise is updated.
     * </p>
     *
     * @param update the ExerciseUpdate containing fields to be updated in this Exercise.
     */
    public void updateExercise(ExerciseUpdate update) {
        assert update != null : "ExerciseUpdate object must be provided";

        updateSets(update.sets);
        updateReps(update.reps);
        updateWeight(update.weight);
        updateName(update.name);
        updateCalories(update.calories);
    }

    /**
     * Updates the number of sets for this exercise.
     *
     * @param newSets the new number of sets; if null, the sets are not updated
     */
    private void updateSets(Integer newSets) {
        if (isNull(newSets)) {
            return;
        }
        logger.log(Level.INFO, "Updating sets from {0} to {1}", new Object[]{sets, newSets});
        sets = newSets;
    }

    /**
     * Updates the number of reps for this exercise.
     *
     * @param newReps the new number of reps; if null, the reps are not updated
     */
    private void updateReps(Integer newReps) {
        if (isNull(newReps)) {
            return;
        }
        logger.log(Level.INFO, "Updating reps from {0} to {1}", new Object[]{reps, newReps});
        reps = newReps;
    }

    /**
     * Updates the weight for this exercise.
     *
     * @param newWeight the new weight; if null, the weight is not updated
     */
    private void updateWeight(Integer newWeight) {
        if (isNull(newWeight)) {
            return;
        }
        logger.log(Level.INFO, "Updating weight from {0} to {1}", new Object[]{weight, newWeight});
        weight = newWeight;
    }

    /**
     * Updates the name of this exercise.
     *
     * @param newName the new name; if null, the name is not updated
     */
    private void updateName(String newName) {
        if (isNull(newName)) {
            return;
        }
        logger.log(Level.INFO, "Updating name from {0} to {1}", new Object[]{name, newName});
        name = newName;
    }

    private void updateCalories(Integer newCalories) {
        if (isNull(newCalories)) {
            return;
        }
        logger.log(Level.INFO, "Updating calories from {0} to {1}", new Object[]{calories, newCalories});
        calories = newCalories;
    }

    // @@author Atulteja

    public int getCalories() {
        return calories;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return String.format("%s: %d sets of %d at %d | Burnt %d cals", name, sets, reps, weight, calories);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Exercise exercise = (Exercise) o;
        return sets == exercise.sets &&
                reps == exercise.reps &&
                weight == exercise.weight &&
                calories == exercise.calories &&
                Objects.equals(name, exercise.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sets, reps, weight, calories, name);
    }
}
