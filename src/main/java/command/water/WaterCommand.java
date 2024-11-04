//@@author Bev-Low
package command.water;

import command.Command;
import command.CommandResult;
import history.History;
import programme.ProgrammeList;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class WaterCommand extends Command {

    private static final Logger logger = Logger.getLogger(WaterCommand.class.getName());

    protected LocalDate date;

    public WaterCommand(LocalDate date) {
        assert date != null : "Date cannot be null";

        this.date = date;

        logger.log(Level.INFO, "WaterCommand initialized with date: {0}", date);
    }

    public abstract CommandResult execute(History history);

    @Override
    public CommandResult execute(ProgrammeList programmes, History history) {
        logger.log(Level.INFO, "Executing WaterCommand with ProgrammeList and History.");
        return execute(history);
    }
}
