package command.water;

import command.CommandResult;
import history.History;
import water.Water;

import java.time.LocalDate;

public class DeleteWaterCommand extends WaterCommand {
    public static final String COMMAND_WORD = "delete";

    protected int indexWaterToDelete;
    protected final LocalDate date;

    public DeleteWaterCommand(int indexOfWaterToDelete, LocalDate date) {
        this.indexWaterToDelete = indexOfWaterToDelete;
        this.date = date;
    }

    public CommandResult execute(History history) {
        Water water = getWaterList(history);
        water.deleteWater(indexWaterToDelete);

        return new CommandResult(indexWaterToDelete + " has been added");
    }
}

