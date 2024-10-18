package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.Day;
import programme.Exercise;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryTest {
    private History history;
    private Day day1;
    private Day day2;
    private LocalDateTime date1;
    private LocalDateTime date2;

    @BeforeEach
    public void setUp() {
        history = new History();

        // Create exercises for Day 1
        ArrayList<Exercise> exercisesDay1 = new ArrayList<>();
        exercisesDay1.add(new Exercise(3, 12, 50, "Bench_Press"));
        exercisesDay1.add(new Exercise(3, 12, 80, "Squat"));

        // Create exercises for Day 2
        ArrayList<Exercise> exercisesDay2 = new ArrayList<>();
        exercisesDay2.add(new Exercise(3, 10, 100, "Deadlift"));
        exercisesDay2.add(new Exercise(4, 8, 0, "Pull_Up"));  // No weight for Pull_Up

        // Create Day objects
        day1 = new Day("Day 1", exercisesDay1);
        day2 = new Day("Day 2", exercisesDay2);

        // Dates for logging the days
        date1 = LocalDateTime.of(2024, 10, 12, 14, 30);  // Example date
        date2 = LocalDateTime.of(2024, 10, 13, 16, 45);  // Example date
    }

    @Test
    public void testLogDayAndToString() {
        // Log the days into history
        history.logDay(day1, date1);
        history.logDay(day2, date2);

        // Expected output from history
        String expectedOutput = "Day 1\n\n"
                + "1. Bench Press: 3 sets of 12 reps at 50 kg\n"
                + "2. Squat: 3 sets of 12 reps at 80 kg\n\n"
                + "Completed On: 12/10/2024\n\n"
                + "Day 2\n\n"
                + "1. Deadlift: 3 sets of 10 reps at 100 kg\n"
                + "2. Pull Up: 4 sets of 8 reps at 0 kg\n\n"
                + "Completed On: 13/10/2024\n\n";

        // Assert that the toString method matches the expected output
        assertEquals(expectedOutput, history.toString());
    }

    @Test
    public void testEmptyHistory() {
        // Ensure that history returns "No workout history available" when empty
        assertEquals("No workout history available.", history.toString());
    }

    @Test
    public void testOverwriteDay() {
        // Log day1 with date1, then log another day with the same date to overwrite
        history.logDay(day1, date1);

        // Modify day1 with a different exercise
        Day modifiedDay = new Day("Day 1");
        modifiedDay.insertExercise(new Exercise(3, 12, 15, "Bicep_Curl"));

        // Log the modified day with the same date
        history.logDay(modifiedDay, date1);

        // Expected output for overwritten day with correct date format
        String expectedOutput = "Day 1\n\n"
                + "1. Bicep Curl: 3 sets of 12 reps at 15 kg\n\n"
                + "Completed On: 12/10/2024\n\n";

        // Assert that the history has the modified day
        assertEquals(expectedOutput, history.toString());
    }
}



