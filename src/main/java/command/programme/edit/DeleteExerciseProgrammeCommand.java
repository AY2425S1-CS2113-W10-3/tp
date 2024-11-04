// @@author TVageesan
package command.programme.edit;

import command.CommandResult;
import programme.Day;
import programme.Exercise;
import programme.Programme;
import programme.ProgrammeList;

import java.util.logging.Level;

/**
 * Command to delete a specific exercise from a day within a programme.
 * <p>
 * This class encapsulates the functionality to remove an exercise identified by its
 * ID from a specified day of a programme, which is also identified by its index.
 * </p>
 */
public class DeleteExerciseProgrammeCommand extends EditProgrammeCommand {
    public static final String SUCCESS_MESSAGE_FORMAT = "Deleted exercise %d: %n%s%n";

    /**
     * Constructs a DeleteExerciseCommand with the specified programme index, day ID, and exercise ID.
     *
     * @param programmeIndex the index of the programme from which the exercise will be deleted
     * @param dayIndex the ID of the day from which the exercise will be deleted
     * @param exerciseIndex the ID of the exercise to be deleted
     */
    public DeleteExerciseProgrammeCommand(int programmeIndex, int dayIndex, int exerciseIndex) {
        super(programmeIndex, dayIndex, exerciseIndex);
        logger.log(
                Level.INFO,
                "DeleteExerciseCommand created with programme index: {0}, day index: {1}, and exercise index: {2}",
                new Object[]{programmeIndex, dayIndex, exerciseIndex}
        );
    }

    /**
     * Executes the command to delete the specified exercise from the given day of the programme.
     *
     * @param programmes the ProgrammeList containing the programmes where the exercise will be deleted
     * @return a CommandResult containing a success message indicating the deleted exercise
     */
    @Override
    public CommandResult execute(ProgrammeList programmes) {
        assert programmes != null : "programmes cannot be null";

        Programme selectedProgramme = programmes.getProgramme(programmeIndex);
        Day selectedDay = selectedProgramme.getDay(dayIndex);
        Exercise deletedExercise = selectedDay.deleteExercise(exerciseIndex);

        logger.log(Level.INFO, "DeleteExerciseCommand executed successfully.");

        String result = String.format(SUCCESS_MESSAGE_FORMAT, exerciseIndex, deletedExercise);
        return new CommandResult(result);
    }
}
