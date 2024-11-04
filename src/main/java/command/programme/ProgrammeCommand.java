// @@author TVageesan
package command.programme;
import command.Command;

import java.util.logging.Logger;

import static common.Utils.NULL_INTEGER;

/**
 * Represents an abstract command related to programmes.
 */
public abstract class ProgrammeCommand extends Command {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    protected int programmeIndex;
    protected int dayIndex;

    /**
     * Constructs a ProgrammeCommand with the specified programme index and day index.
     *
     * @param programmeIndex The index of the programme.
     * @param dayIndex The index of the day.
     */
    public ProgrammeCommand(int programmeIndex, int dayIndex) {
        this(programmeIndex);
        assert dayIndex >= 0 : "dayIndex must not be negative";
        this.dayIndex = dayIndex;
    }

    /**
     * Constructs a ProgrammeCommand with the specified programme index.
     *
     * @param programmeIndex The index of the programme.
     */
    public ProgrammeCommand(int programmeIndex) {
        // We accept NULL_INTEGER as a valid programmeIndex as it is an optional argument
        assert programmeIndex == NULL_INTEGER || programmeIndex >= 0 : "Program index must be valid";
        this.programmeIndex = programmeIndex;
    }
    
    /**
     * Constructs a ProgrammeCommand with no specified indices.
     */
    public ProgrammeCommand(){}

    /**
     * Checks if this command is equal to another object.
     *
     * @param other The other object to compare to.
     * @return true if the other object is equal to this command, false otherwise.
     */
    @Override
    public boolean equals(Object other){
        if (this == other){
            return true;
        }

        if (!(other instanceof ProgrammeCommand that)){
            return false;
        }

        boolean isProgIndexEqual = this.programmeIndex == that.programmeIndex;
        boolean isDayIndexEqual = this.dayIndex == that.dayIndex;
        return isProgIndexEqual && isDayIndexEqual;
    }
}
