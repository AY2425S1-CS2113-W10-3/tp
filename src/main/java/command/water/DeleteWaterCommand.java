//@@author Bev-Low
package command.water;

import command.CommandResult;
import history.DailyRecord;
import history.History;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteWaterCommand extends WaterCommand {
    public static final String COMMAND_WORD = "delete";
    private static final Logger logger = Logger.getLogger(DeleteWaterCommand.class.getName());

    protected int indexWaterToDelete;

    public DeleteWaterCommand(int indexOfWaterToDelete, LocalDate date) {
        super(date);

        assert indexOfWaterToDelete >= 0 : "Index to delete cannot be negative";

        this.indexWaterToDelete = indexOfWaterToDelete;
        logger.log(Level.INFO, "DeleteWaterCommand created for index: {0} on date: {1}",
                new Object[]{indexWaterToDelete, date});
    }

    public CommandResult execute(History history) {
        DailyRecord dailyRecord = history.getRecordByDate(date);
        assert dailyRecord != null : "Daily record not found";
        float deletedWater = dailyRecord.removeWaterFromRecord(indexWaterToDelete);

        return new CommandResult(deletedWater + " liters of water has been deleted");
    }
}
