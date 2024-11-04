package command.programme;

import command.CommandResult;

import programme.ProgrammeList;
import programme.Programme;
import history.History;

import java.util.logging.Level;

/**
 * Represents a command to view a specific programme.
 */
public class ViewProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "view";
    public static final String SUCCESS_MESSAGE_FORMAT = "Viewing programme: %n%s";

    /**
     * Constructs a ViewProgrammeCommand with the specified programme index.
     *
     * @param programmeIndex The index of the programme to view.
     */
    public ViewProgrammeCommand(int programmeIndex) {
        super(programmeIndex);
        logger.log(Level.INFO, "ViewCommand created with programme index: {0}", programmeIndex);
    }

    /**
     * Executes the command to view a specific programme.
     *
     * @param programmes The list of programmes.
     * @param history The history object to record the command execution.
     * @return A CommandResult object containing the result of the command execution.
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        assert programmes != null : "ProgrammeList must not be null";

        Programme programme = programmes.getProgramme(programmeIndex);
        assert programme != null : "Programme must not be null";

        logger.log(Level.INFO, "ViewCommand executed successfully.");

        String result = String.format(SUCCESS_MESSAGE_FORMAT, programme);
        return new CommandResult(result);
    }
}
