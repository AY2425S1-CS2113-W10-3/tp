// @@author TVageesan
package command.programme.edit;

import command.CommandResult;

import programme.Day;
import programme.Exercise;
import programme.ExerciseUpdate;
import programme.Programme;
import programme.ProgrammeList;

import java.util.logging.Level;

/**
 * Command to edit a specific exercise's fields within a day of a programme.
 * <p>
 * This class encapsulates the functionality to update the details of an
 * existing exercise identified by its ID within a specified day of a programme.
 * </p>
 */
public class EditExerciseProgrammeCommand extends EditProgrammeCommand {

    public static final String SUCCESS_MESSAGE_FORMAT = "Updated exercise: %s%n";

    private final ExerciseUpdate update;

    /**
     * Constructs an EditExerciseCommand with the specified programme index, day ID,
     * exercise ID, and updated exercise details.
     *
     * @param programmeIndex the index of the programme containing the exercise to be updated
     * @param dayIndex the ID of the day containing the exercise to be updated
     * @param exerciseIndex the ID of the exercise to be updated
     * @param update the ExerciseUpdate object containing the fields that need to be updated in the target Exercise
     */
    public EditExerciseProgrammeCommand(int programmeIndex, int dayIndex, int exerciseIndex, ExerciseUpdate update) {
        super(programmeIndex, dayIndex, exerciseIndex);
        assert update != null : "update object must not be null";
        this.update = update;
        logger.log(
                Level.INFO,
                "EditExerciseCommand created with programme index: {0}, day index: {1}, and exercise index: {2}",
                new Object[]{programmeIndex, dayIndex, exerciseIndex}
        );
    }

    /**
     * Executes the command to update the specified exercise in the given day of the programme.
     *
     * @param programmes the ProgrammeList containing the programmes where the exercise will be updated
     * @return a CommandResult containing a success message indicating the updated exercise
     */
    @Override
    public CommandResult execute(ProgrammeList programmes) {
        assert programmes != null : "programmes cannot be null";

        Programme selectedProgramme = programmes.getProgramme(programmeIndex);
        Day selectedDay = selectedProgramme.getDay(dayIndex);
        Exercise selectedExercise = selectedDay.getExercise(exerciseIndex);
        selectedExercise.updateExercise(update);

        logger.log(Level.INFO, "EditExerciseCommand executed successfully.");

        String result = String.format(SUCCESS_MESSAGE_FORMAT, selectedExercise);
        return new CommandResult(result);
    }
}
