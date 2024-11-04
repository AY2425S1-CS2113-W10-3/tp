package command.programme;

import command.CommandResult;
import programme.ProgrammeList;
import history.History;

import java.util.logging.Level;

public class ListProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "list";
    public static final String SUCCESS_MESSAGE_FORMAT = "Listing programmes: %n%s";

    public ListProgrammeCommand() {
        logger.log(Level.INFO, "ListCommand created.");
    }
    
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        assert programmes != null : "Programme list must not be null";

        logger.log(Level.INFO, "ListCommand executed successfully.");

        String result = String.format(SUCCESS_MESSAGE_FORMAT, programmes);
        return new CommandResult(result);
    }
}
