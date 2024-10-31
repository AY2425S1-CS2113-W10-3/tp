package command.meals;

import command.CommandResult;
import history.DailyRecord;
import history.History;
import meal.Meal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AddMealCommandTest {

    private AddMealCommand addMealCommand;
    private Meal sampleMeal;
    private LocalDate date;

    @Mock
    private History mockHistory;

    @Mock
    private DailyRecord mockDailyRecord;

    @BeforeEach
    public void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        sampleMeal = new Meal("Sample Meal", 500);
        date = LocalDate.now();

        // Set up History mock to return a DailyRecord for the specified date
        when(mockHistory.getRecordByDate(date)).thenReturn(mockDailyRecord);

        addMealCommand = new AddMealCommand(sampleMeal, date);
    }

    @Test
    public void testExecute_HappyPath() {
        // Arrange
        CommandResult expectedResult = new CommandResult(sampleMeal.toString() + " has been added");

        // Act
        CommandResult result = addMealCommand.execute(mockHistory);

        // Assert
        verify(mockDailyRecord).addMealToRecord(sampleMeal);
        assertEquals(expectedResult, result, "Execution should return a CommandResult with the correct success message.");
    }

    @Test
    public void testExecute_EdgeCase_NullDailyRecord() {
        // Set up History mock to return null for the DailyRecord
        when(mockHistory.getRecordByDate(date)).thenReturn(null);

        assertThrows(AssertionError.class, () -> addMealCommand.execute(mockHistory), "Executing AddMealCommand without a valid DailyRecord should throw an AssertionError.");
    }

    @Test
    public void testExecute_EdgeCase_NullMeal() {
        // Attempting to create a command with a null meal should throw an AssertionError
        assertThrows(AssertionError.class, () -> new AddMealCommand(null, date), "Creating AddMealCommand with null Meal should throw AssertionError.");
    }
}