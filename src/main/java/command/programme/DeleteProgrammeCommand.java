// @@author TVageesan
package command.programme;

import command.CommandResult;
import history.History;
import programme.Programme;
import programme.ProgrammeList;

import java.util.logging.Level;

/**
 * Represents a command to delete a programme from the ProgrammeList.
 */
public class DeleteProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "delete";
    public static final String SUCCESS_MESSAGE_FORMAT = "Deleted:%n%s";

    /**
     * Constructs a DeleteProgrammeCommand with the specified programme index.
     *
     * @param programmeIndex The index of the programme to be deleted.
     */
    public DeleteProgrammeCommand(int programmeIndex) {
        super(programmeIndex);
        logger.log(Level.INFO, "DeleteCommand created with programme index: {0}", programmeIndex);
    }

    /**
     * Executes the command to delete a programme from the programme list.
     *
     * @param programmes The list of programmes from which the programme will be deleted.
     * @param history The history object to record the command execution.
     * @return A CommandResult object containing the result of the command execution.
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        assert programmes != null : "Programme list must not be null";
        
        Programme programme = programmes.deleteProgram(programmeIndex);
        
        logger.log(Level.INFO, "DeleteCommand executed successfully.");
        
        String result = String.format(SUCCESS_MESSAGE_FORMAT, programme);
        return new CommandResult(result);
    }
}
