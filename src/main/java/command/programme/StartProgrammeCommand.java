package command.programme;

import command.CommandResult;
import programme.ProgrammeList;
import programme.Programme;
import history.History;

import java.util.logging.Level;

/**
 * Represents a command to start a specific programme.
 */
public class StartProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "start";
    public static final String SUCCESS_MESSAGE_FORMAT = "Started programme: %n%s";

    /**
     * Constructs a StartProgrammeCommand with the specified programme index.
     *
     * @param programmeIndex The index of the programme to start.
     */
    public StartProgrammeCommand(int programmeIndex) {
        super(programmeIndex);
        logger.log(Level.INFO, "StartCommand created with programme index: {0}", programmeIndex);
    }

    /**
     * Executes the command to start a specific programme.
     *
     * @param programmes The list of programmes.
     * @param history The history object to record the command execution.
     * @return A CommandResult object containing the result of the command execution.
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        assert programmes != null : "Programme list must not be null";

        Programme started = programmes.startProgramme(programmeIndex);
        assert started != null : "Programme must not be null";

        logger.log(Level.INFO, "StartCommand executed successfully.");
        
        String result = String.format(SUCCESS_MESSAGE_FORMAT, started);
        return new CommandResult(result);
    }
}
