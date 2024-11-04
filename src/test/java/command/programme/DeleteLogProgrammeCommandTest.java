package command.programme;

import command.CommandResult;
import history.DailyRecord;
import history.History;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.Day;
import programme.ProgrammeList;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeleteLogProgrammeCommandTest {

    private History history;
    private ProgrammeList programmes;
    private LocalDate testDate;
    private Day testDay;
    private DailyRecord dailyRecord;

    @BeforeEach
    void setUp() {
        history = new History();
        programmes = new ProgrammeList();
        testDate = LocalDate.of(2023, 10, 1);
        testDay = new Day("Test Day");
        dailyRecord = new DailyRecord();
        dailyRecord.logDayToRecord(testDay);
        history.logRecord(testDate, dailyRecord);
    }

    // Test for constructor with valid inputs
    @Test
    void constructor_initializesWithValidParameters() {
        assertDoesNotThrow(() -> new DeleteLogProgrammeCommand(testDate));
    }

    // Edge case for constructor: invalid programme ID
    @Test
    void constructor_initializesWithInvalidParameters() {
        assertThrows(AssertionError.class, () -> new DeleteLogProgrammeCommand(null));
    }

    @Test
    void execute_happyPath_logDeletedSuccessfully() {
        DeleteLogProgrammeCommand command = new DeleteLogProgrammeCommand(testDate);
        CommandResult result = command.execute(programmes, history);

        assertNotNull(result);
        assertNull(history.getRecordByDate(testDate).getDayFromRecord());
    }

    @Test
    void execute_edgeCase_noLogExists() {
        LocalDate nonExistentDate = LocalDate.of(2023, 10, 2);
        DeleteLogProgrammeCommand command = new DeleteLogProgrammeCommand(nonExistentDate);
        assertThrows(IllegalStateException.class, () -> command.execute(programmes, history));
    }

    @Test
    void execute_edgeCase_nullHistory() {
        DeleteLogProgrammeCommand command = new DeleteLogProgrammeCommand(testDate);
        assertThrows(AssertionError.class, () -> command.execute(programmes, null));
    }
}
