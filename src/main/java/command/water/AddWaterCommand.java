package command.water;

import command.CommandResult;
import history.History;
import water.Water;

import java.time.LocalDate;

public class AddWaterCommand extends WaterCommand {
    public static final String COMMAND_WORD = "add";

    protected float waterToAdd;
    protected LocalDate date;

    public AddWaterCommand(float waterToAdd, LocalDate date) {
        this.waterToAdd = waterToAdd;
        this.date = date;
    }

    public CommandResult execute(History history) {
        Water water = getWaterList(history);
        water.addWater(waterToAdd);

        return new CommandResult(waterToAdd + " has been added");
    }

}
