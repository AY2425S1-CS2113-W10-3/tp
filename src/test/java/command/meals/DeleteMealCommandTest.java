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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class DeleteMealCommandTest {

    private DeleteMealCommand deleteMealCommand;
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

        // Set up DailyRecord mock to return the sample meal when deleting by index
        when(mockDailyRecord.deleteMealFromRecord(0)).thenReturn(sampleMeal);

        deleteMealCommand = new DeleteMealCommand(0, date);
    }

    @Test
    public void testExecuteHappyPath() {
        // Arrange
        CommandResult expectedResult = new CommandResult(sampleMeal + " has been deleted");

        // Act
        CommandResult result = deleteMealCommand.execute(mockHistory);

        // Assert
        verify(mockDailyRecord).deleteMealFromRecord(0);
        assertEquals(expectedResult, result, "Execution should " +
                "return a CommandResult with the correct success message.");
    }

    @Test
    public void testExecuteEdgeCaseNullDailyRecord() {
        // Set up History mock to return null for the DailyRecord
        when(mockHistory.getRecordByDate(date)).thenReturn(null);

        assertThrows(AssertionError.class, () -> deleteMealCommand.execute(mockHistory), "Executing " +
                "DeleteMealCommand without a valid DailyRecord should throw an AssertionError.");
    }

    @Test
    public void testExecuteEdgeCaseInvalidIndex() {
        // Set up DailyRecord mock to throw IndexOutOfBoundsException when an invalid index is used
        when(mockDailyRecord.deleteMealFromRecord(5)).thenThrow(new IndexOutOfBoundsException("Invalid index"));

        DeleteMealCommand invalidIndexCommand = new DeleteMealCommand(5, date);

        assertThrows(IndexOutOfBoundsException.class, () -> invalidIndexCommand.execute(mockHistory), "Executing " +
                "DeleteMealCommand with an invalid index should throw IndexOutOfBoundsException.");
    }

    @Test
    public void testConstructorEdgeCaseNegativeIndex() {
        // Attempting to create a command with a negative index should throw an AssertionError
        assertThrows(AssertionError.class, () -> new DeleteMealCommand(-1, date), "Creating " +
                "DeleteMealCommand with negative index should throw AssertionError.");
    }
}
