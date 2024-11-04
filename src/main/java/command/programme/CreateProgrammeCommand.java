// @@author TVageesan
package command.programme;
import java.util.ArrayList;
import java.util.logging.Level;

import command.CommandResult;
import programme.Day;
import programme.ProgrammeList;
import programme.Programme;
import history.History;

/**
 * Represents a command to create a new programme in ProgrammeList.
 */
public class CreateProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "create";
    public static final String SUCCESS_MESSAGE_FORMAT = "New programme created: %n%s";

    private final String programmeName;
    private final ArrayList<Day> programmeContents;

    /**
     * Constructs a CreateProgrammeCommand with the specified programme name and contents.
     *
     * @param programmeName The name of the programme to be created.
     * @param programmeContents The list of days that make up the programme.
     */
    public CreateProgrammeCommand(String programmeName, ArrayList<Day> programmeContents) {
        assert programmeName != null && !programmeName.isEmpty() : "Programme name must not be null";
        assert programmeContents != null : "Programme contents must not be null";
        this.programmeName = programmeName;
        this.programmeContents = programmeContents;
    }

    /**
     * Executes the command to create a new programme and adds it to the programme list.
     *
     * @param programmes The list of programmes to which the new programme will be added.
     * @param history The history object to record the command execution.
     * @return A CommandResult object containing the result of the command execution.
     */
    @Override
    public CommandResult execute(ProgrammeList programmes, History history){
        assert programmes != null : "Programme list must not be null";
        
        Programme created = programmes.insertProgramme(programmeName, programmeContents);
        assert created != null : "programme must be created";
        
        logger.log(Level.INFO, "CreateCommand executed successfully.");
        
        String result = String.format(SUCCESS_MESSAGE_FORMAT, created);
        return new CommandResult(result);
    }
}
