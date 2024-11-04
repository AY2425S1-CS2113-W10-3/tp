//@@author Bev-Low
package command.water;

import command.CommandResult;
import history.DailyRecord;
import history.History;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddWaterCommand extends WaterCommand {
    public static final String COMMAND_WORD = "add";
    private static final Logger logger = Logger.getLogger(AddWaterCommand.class.getName());

    protected float waterToAdd;

    public AddWaterCommand(float waterToAdd, LocalDate date) {
        super(date);

        assert waterToAdd > 0 : "Water to add must be positive";

        this.waterToAdd = waterToAdd;
        logger.log(Level.INFO, "AddWaterCommand created to add {0} liters for date: {1}",
                new Object[]{waterToAdd, date});
    }

    public CommandResult execute(History history) {
        DailyRecord dailyRecord = history.getRecordByDate(date);
        assert dailyRecord != null : "Daily record not found";
        dailyRecord.addWaterToRecord(waterToAdd);
        logger.log(Level.INFO, "{0} liters of water added for date: {1}",
                new Object[]{waterToAdd, date});

        return new CommandResult(waterToAdd + " liters of water has been added");
    }
}
