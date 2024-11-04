//@@author Bev-Low
package command.water;

import command.CommandResult;
import common.Utils;
import history.DailyRecord;
import history.History;
import water.Water;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewWaterCommand extends WaterCommand {
    public static final String COMMAND_WORD = "view";
    private static final Logger logger = Logger.getLogger(ViewWaterCommand.class.getName());

    public ViewWaterCommand(LocalDate date) {
        super(date);

        logger.log(Level.INFO, "ViewWaterCommand created for date: {0}", date);
    }

    public CommandResult execute(History history) {
        String formattedDate = Utils.formatDate(date);

        DailyRecord dailyRecord = history.getRecordByDate(date);
        assert dailyRecord != null : "Daily record not found";
        Water water = dailyRecord.getWaterFromRecord();

        logger.log(Level.INFO, "Retrieved Water record for date: {0}, Water: {1}", new Object[]{date, water});
        return new CommandResult("Water intake for " + formattedDate +  ": \n\n" + water.toString());
    }
}
