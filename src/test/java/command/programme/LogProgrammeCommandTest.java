// @@ author andreusxcarvalho

package command.programme;
import org.junit.jupiter.api.Test;

import command.CommandResult;
import history.History;
import history.DailyRecord;
import programme.Day;
import programme.ProgrammeList;
import programme.Programme;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class LogProgrammeCommandTest {

    @Test
    public void testExecute_logsDayIntoHistory() {
        ProgrammeList mockProgrammes = mock(ProgrammeList.class);
        History mockHistory = mock(History.class);
        Day mockDay = mock(Day.class);
        DailyRecord mockDailyRecord = mock(DailyRecord.class);
        Programme mockProgramme = mock(Programme.class);

        int progIndex = 0;
        int dayIndex = 0;
        LocalDate date = LocalDate.of(2024, 12, 12);

        // Mock ProgrammeList behavior to return the mock Programme when getProgramme() is called
        when(mockProgrammes.getProgramme(progIndex)).thenReturn(mockProgramme);
        when(mockProgramme.getDay(dayIndex)).thenReturn(mockDay);
        when(mockHistory.getRecordByDate(date)).thenReturn(mockDailyRecord);

        LogProgrammeCommand logCommand = new LogProgrammeCommand(progIndex, dayIndex, date);

        CommandResult result = logCommand.execute(mockProgrammes, mockHistory);

        verify(mockProgrammes).getProgramme(progIndex);
        verify(mockProgramme).getDay(dayIndex);
        verify(mockDailyRecord).logDayToRecord(mockDay);
        assertNotNull(result);
    }

    @Test
    public void testExecute_edgeCase_invalidProgrammeIndex() {
        ProgrammeList mockProgrammes = mock(ProgrammeList.class);
        History mockHistory = mock(History.class);
        int invalidProgIndex = -1;
        int dayIndex = 0;
        LocalDate date = LocalDate.of(2024, 12, 12);

        LogProgrammeCommand logCommand = new LogProgrammeCommand(invalidProgIndex, dayIndex, date);

        assertThrows(NullPointerException.class, () -> logCommand.execute(mockProgrammes, mockHistory));
    }

    @Test
    public void testExecute_edgeCase_nullHistory() {
        ProgrammeList mockProgrammes = mock(ProgrammeList.class);
        int progIndex = 0;
        int dayIndex = 0;
        LocalDate date = LocalDate.of(2024, 12, 12);

        LogProgrammeCommand logCommand = new LogProgrammeCommand(progIndex, dayIndex, date);

        assertThrows(AssertionError.class, () -> logCommand.execute(mockProgrammes, null));
    }
}
