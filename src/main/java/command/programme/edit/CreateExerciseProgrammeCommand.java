// @@author TVageesan
package command.programme.edit;

import command.CommandResult;
import programme.Day;
import programme.Exercise;
import programme.Programme;
import programme.ProgrammeList;

import java.util.logging.Level;

/**
 * Command to create a new exercise within a specified day of a programme.
 * <p>
 * This class is responsible for creating an exercise and adding it to a specific day
 * within a specified programme.
 * </p>
 */
public class CreateExerciseProgrammeCommand extends EditProgrammeCommand {
    public static final String SUCCESS_MESSAGE_FORMAT = "Created new exercise: %n%s%n";
    private final Exercise createdExercise;

    /**
     * Constructs a CreateExerciseCommand with the specified programme index, day ID, and exercise.
     *
     * @param programmeIndex the index of the programme to which the exercise will be added
     * @param dayIndex the ID of the day within the programme where the exercise will be inserted
     * @param createdExercise the exercise to be created and added to the day
     */
    public CreateExerciseProgrammeCommand(int programmeIndex, int dayIndex, Exercise createdExercise) {
        super(programmeIndex,dayIndex);
        assert createdExercise != null : "created exercise must not be null";
        this.createdExercise = createdExercise;
        logger.log(Level.INFO, "CreateExerciseCommand created with exercise: {0}", createdExercise);
    }

    /**
     * Executes the command to insert the created exercise into the specified day of the programme.
     * @param programmes the ProgrammeList that contains the programmes where the exercise will be added
     * @return a CommandResult containing a success message indicating the created exercise
     */
    public CommandResult execute(ProgrammeList programmes) {
        assert programmes != null : "programmes cannot be null";

        Programme selectedProgramme = programmes.getProgramme(programmeIndex);
        Day selectedDay = selectedProgramme.getDay(dayIndex);
        selectedDay.insertExercise(createdExercise);

        logger.log(Level.INFO, "CreateExerciseCommand executed successfully.");

        String result = String.format(SUCCESS_MESSAGE_FORMAT, createdExercise);
        return new CommandResult(result);
    }
}
