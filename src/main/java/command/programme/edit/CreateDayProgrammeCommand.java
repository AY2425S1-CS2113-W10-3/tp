// @@author TVageesan
package command.programme.edit;

import command.CommandResult;
import programme.Day;
import programme.Programme;
import programme.ProgrammeList;

import java.util.logging.Level;

/**
 * Command to create a new day in a programme.
 * This command adds a new Day object to an existing programme identified by the programme ID.
 */
public class CreateDayProgrammeCommand extends EditProgrammeCommand {
    public static final String SUCCESS_MESSAGE_FORMAT = "Created new day: %s%n";
    private final Day createdDay;

    /**
     * Constructs a new CreateDayCommand.
     * @param programmeIndex The ID of the programme to add the day to
     * @param createdDay The Day object to be added to the programme
     */
    public CreateDayProgrammeCommand(int programmeIndex, Day createdDay) {
        super(programmeIndex);
        assert createdDay != null : "created day cannot be null";
        this.createdDay = createdDay;
        logger.log(Level.INFO, "CreateDayCommand created with day: {0}", createdDay);
    }

    /**
     * Executes the command to insert the created day into the specified programme.
     * @author TVageesan
     * @param programmes the ProgrammeList that contains the programmes where the day will be added
     * @return a CommandResult containing a success message indicating the created day
     */
    @Override
    public CommandResult execute(ProgrammeList programmes) {
        assert programmes != null : "programmes cannot be null";

        Programme selectedProgramme = programmes.getProgramme(programmeIndex);
        selectedProgramme.insertDay(createdDay);

        logger.log(Level.INFO, "CreateDayCommand executed successfully.");

        String resultMessage = String.format(SUCCESS_MESSAGE_FORMAT, createdDay);
        return new CommandResult(resultMessage);
    }
}
