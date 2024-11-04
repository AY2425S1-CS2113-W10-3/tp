// @@author TVageesan
package command.programme.edit;

import command.CommandResult;
import command.programme.ProgrammeCommand;
import history.History;
import programme.ProgrammeList;

import java.util.logging.Logger;

/**
 * Abstract command class for all editing operations on a programme.
 * <p>
 * This class serves as a base for all editing commands that operate on
 * a programme, including operations to modify exercises or days within
 * a programme.
 * </p>
 */
public abstract class EditProgrammeCommand extends ProgrammeCommand {
    public static final String COMMAND_WORD = "edit";
    protected final Logger logger = Logger.getLogger(this.getClass().getName());

    protected int exerciseIndex;

    /**
     * Constructs an EditCommand with the specified programme index, day ID, and exercise ID.
     *
     * @param programmeIndex the index of the programme being edited
     * @param dayIndex the ID of the day being edited within the programme
     * @param exerciseIndex the ID of the exercise being edited
     */
    public EditProgrammeCommand(int programmeIndex, int dayIndex, int exerciseIndex) {
        super(programmeIndex, dayIndex);
        assert exerciseIndex >= 0 : "exercise id must be non-negative";
        this.exerciseIndex = exerciseIndex;
    }

    /**
     * Constructs an EditCommand with the specified programme index and day ID.
     *
     * @param programmeIndex the index of the programme being edited
     * @param dayIndex the ID of the day being edited within the programme
     */
    public EditProgrammeCommand(int programmeIndex, int dayIndex) {
        super(programmeIndex, dayIndex);
    }

    /**
     * Constructs an EditCommand with the specified programme index.
     *
     * @param programmeIndex the index of the programme being edited
     */
    public EditProgrammeCommand(int programmeIndex) {
        super(programmeIndex);
    }

    /**
     * Executes the edit command on the given ProgrammeList.
     * <p>
     * This method must be implemented by subclasses to define specific
     * editing behavior.
     * </p>
     *
     * @param programmes the ProgrammeList containing the programmes to edit
     * @return a CommandResult indicating the outcome of the edit operation
     */
    public abstract CommandResult execute(ProgrammeList programmes);

    @Override
    public CommandResult execute(ProgrammeList programmes, History history) {
        return execute(programmes);
    }
}
