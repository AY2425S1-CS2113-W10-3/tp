package programme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayTest {

    private Day day;

    @BeforeEach
    void setUp() {

        Exercise exercise1 = new Exercise(3, 10, 50, 160,"Bench_Press");
        Exercise exercise2 = new Exercise(3, 12, 20, 100, "Triceps_Extension");
        Exercise exercise3 = new Exercise(3, 10, 50, 100,"Seated_Press");

        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(exercise1);
        exercises.add(exercise2);
        exercises.add(exercise3);

        day = new Day("Push", exercises);

    }

    @Test
    void testInsertExercises() {

        Exercise exercise4 = new Exercise(3, 12, 40, 120,"Chest_Fly");
        day.insertExercise(exercise4);

        assertEquals(4, day.getExercisesCount());
        assertEquals(exercise4, day.getExercise(3));

    }

    @Test
    void testDeleteExercise() {
        Exercise deletedExercise = day.deleteExercise(2);

        assertEquals(2, day.getExercisesCount());
        //TODO: use equals() to test
    }

}


