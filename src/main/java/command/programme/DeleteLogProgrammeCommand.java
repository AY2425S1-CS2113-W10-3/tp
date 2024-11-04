// @@author TVageesan
package command.programme;

import command.CommandResult;
import history.DailyRecord;
import history.History;
import programme.Day;
import programme.ProgrammeList;

import java.time.LocalDate;
import java.util.logging.Level;

/**
 * Command to delete a workout log for a specific date.
 * <p>
 * This class encapsulates the functionality to remove a workout log identified by its
 * date from the history.
 * </p>
 */
public class DeleteLogProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "unlog";
    public static final String SUCCESS_MESSAGE_FORMAT = "Deleted:%n%s";

    private final LocalDate date;

    /**
     * Constructs a DeleteLogProgrammeCommand with the specified date.
     *
     * @param date the date of the workout log to be deleted
     */
    public DeleteLogProgrammeCommand(LocalDate date){
        assert date != null : "Date must not be null";
        this.date = date;
        logger.log(Level.INFO, "DeleteLogCommand created with date: {0}", date);
    }

    /**
     * Executes the command to delete the workout log for the specified date.
     *
     * @param programmes the ProgrammeList containing the programmes (not used in this command)
     * @param history the History object containing the workout logs
     * @return a CommandResult containing a success message
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        assert history != null : "History must not be null";

        DailyRecord dailyRecord = history.getRecordByDate(date);
        assert dailyRecord != null : "DailyRecord must not be null";

        Day deleted = dailyRecord.deleteDayFromRecord();

        logger.log(Level.INFO, "DeleteLogCommand executed successfully.");

        String result =  String.format(SUCCESS_MESSAGE_FORMAT, deleted);
        return new CommandResult(result);
    }
}
