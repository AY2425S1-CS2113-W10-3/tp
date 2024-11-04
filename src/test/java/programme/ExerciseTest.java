package programme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExerciseTest {

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        // Set up an initial exercise object
        exercise = new Exercise(3, 10, 50, 160,"Bench_Press");
    }

    @Test
    void testUpdateExerciseAllFieldsNonNull() {
        ExerciseUpdate update = new ExerciseUpdate(4, 12, 60, 200,"Squat");
        //TODO: use exercise equals() to test
        exercise.updateExercise(update);
    }

    @Test
    void testUpdateExerciseOnlySomeFieldsNonNull() {
        ExerciseUpdate update = new ExerciseUpdate(-1, 12, 40, -1,"");
        //TODO: use exercise equals() to test
        exercise.updateExercise(update);
    }
}
