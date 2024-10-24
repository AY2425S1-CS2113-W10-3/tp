package command.water;

import command.CommandResult;
import history.History;
import water.Water;

import java.time.LocalDate;

public class ViewWaterCommand extends WaterCommand {
    public static final String COMMAND_WORD = "view";

    protected final LocalDate date;

    public ViewWaterCommand(LocalDate date) {
        this.date = date;
    }

    public CommandResult execute(History history) {
        Water waterList = getWaterList(history);
        return new CommandResult(waterList.toString());
    }

}
