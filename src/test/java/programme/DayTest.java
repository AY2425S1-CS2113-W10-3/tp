// @@author nirala-ts
package programme;

import exceptions.IndexOutOfBoundsBuffBuddyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayTest {

    private Day day;
    private Exercise exercise1;
    private Exercise exercise2;

    @BeforeEach
    void setUp() {
        exercise1 = new Exercise(3, 10, 50, 160, "Bench_Press");
        exercise2 = new Exercise(3, 12, 20, 100, "Triceps_Extension");
        day = new Day("Push");
    }

    @Test
    void testConstructorWithEmptyExerciseList() {
        Day expectedDay = new Day("Push", new ArrayList<>());
        assertEquals(expectedDay, day);
    }

    @Test
    void testConstructorWithPredefinedExerciseList() {
        ArrayList<Exercise> exercises = new ArrayList<>(Arrays.asList(exercise1, exercise2));
        Day predefinedDay = new Day("Push", exercises);
        Day expectedDay = new Day("Push", exercises);

        assertEquals(expectedDay, predefinedDay);
    }

    @Test
    void testInsertExercise() {
        day.insertExercise(exercise1);
        day.insertExercise(exercise2);

        ArrayList<Exercise> expectedExercises = new ArrayList<>(Arrays.asList(exercise1, exercise2));
        Day expectedDay = new Day("Push", expectedExercises);

        assertEquals(expectedDay, day);
    }

    @Test
    void testGetExerciseInvalidIndex() {
        assertThrows(IndexOutOfBoundsBuffBuddyException.class, () -> day.getExercise(0));
    }

    @Test
    void testDeleteExercise() {
        day.insertExercise(exercise1);
        day.insertExercise(exercise2);

        day.deleteExercise(0);

        ArrayList<Exercise> expectedExercises = new ArrayList<>(Collections.singletonList(exercise2));
        Day expectedDay = new Day("Push", expectedExercises);

        assertEquals(expectedDay, day);
    }

    @Test
    void testDeleteExerciseInvalidIndex() {
        assertThrows(IndexOutOfBoundsBuffBuddyException.class, () -> day.deleteExercise(0));
    }

    @Test
    void testGetTotalCaloriesBurnt() {
        day.insertExercise(exercise1);
        day.insertExercise(exercise2);

        int totalCalories = day.getTotalCaloriesBurnt();

        assertEquals(260, totalCalories); // 160 + 100
    }

    @Test
    void testToString() {
        day.insertExercise(exercise1);
        day.insertExercise(exercise2);

        String expectedOutput = """
                Push
                1. Bench_Press: 3 sets of 10 at 50 | Burnt 160 cals\
                
                2. Triceps_Extension: 3 sets of 12 at 20 | Burnt 100 cals
                """;
        assertEquals(expectedOutput, day.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        ArrayList<Exercise> exercises = new ArrayList<>(Arrays.asList(exercise1, exercise2));
        Day day1 = new Day("Push", exercises);
        Day day2 = new Day("Push", new ArrayList<>(Arrays.asList(exercise1, exercise2)));

        assertEquals(day1, day2);
        assertEquals(day1.hashCode(), day2.hashCode());
    }

    @Test
    void testNotEquals() {
        Day otherDay = new Day("Pull", new ArrayList<>(Arrays.asList(exercise1, exercise2)));
        assertNotEquals(day, otherDay);
    }
}
