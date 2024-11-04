// @@author andreusxcarvalho

package command.programme;
import command.CommandResult;
import history.DailyRecord;
import programme.Programme;
import programme.ProgrammeList;
import programme.Day;
import history.History;

import java.time.LocalDate;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Represents a command to log a specific day of a programme into the history.
 */
public class LogProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "log";

    private final LocalDate date;

    /**
     * Constructs a LogProgrammeCommand with the specified programme index, day index, and date.
     *
     * @param programmeIndex The index of the programme.
     * @param dayIndex The index of the day.
     * @param date The date to log the day into the history.
     */
    public LogProgrammeCommand(int programmeIndex, int dayIndex, LocalDate date){
        super(programmeIndex, dayIndex);

        assert dayIndex >= 0 : "Day index must be non-negative";
        assert date != null : "Date must not be null";

        this.date = date;
        logger.log(Level.INFO,
                "LogCommand created with progIndex: {0}, dayIndex: {1}, date: {2}",
                new Object[]{programmeIndex, dayIndex, date}
        );
    }

    /**
     * Executes the command to log a specific day of a programme into the history.
     *
     * @param programmes The list of programmes.
     * @param history The history object to record the command execution.
     * @return A CommandResult object containing the result of the command execution.
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        logger.log(
                Level.INFO,
                "Executing LogCommand with progIndex: {0}, dayIndex: {1}, date: {2}",
                new Object[]{programmeIndex, dayIndex, date}
        );

        assert programmes != null : "ProgrammeList must not be null";
        assert history != null : "History must not be null";

        Programme selectedProgramme = programmes.getProgramme(programmeIndex);
        Day completed = selectedProgramme.getDay(dayIndex);

        DailyRecord dailyRecord = history.getRecordByDate(date);
        dailyRecord.logDayToRecord(completed);
        history.logRecord(date, dailyRecord);

        logger.log(Level.INFO, "LogCommand executed successfully for day: {0}", completed);

        String result =  String.format("Congrats! You've successfully completed:%n%s",completed);
        return new CommandResult(result);
    }

    /**
     * Checks if this command is equal to another object.
     *
     * @param o The other object to compare to.
     * @return true if the other object is equal to this command, false otherwise.
     */
    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }

        if (!(o instanceof LogProgrammeCommand that)){
            return false;
        }

        logger.log(
                Level.INFO,"Comparing LogCommand with this: {0}, that: {1}",
                new Object[]{this.programmeIndex, that.programmeIndex}
        );

        boolean isProgrammeIndexEqual =  (programmeIndex == that.programmeIndex);
        boolean isDayIndexEqual = (dayIndex == that.dayIndex);
        boolean isDateEqual = Objects.equals(date, that.date);

        return (isProgrammeIndexEqual && isDayIndexEqual && isDateEqual);
    }
}
