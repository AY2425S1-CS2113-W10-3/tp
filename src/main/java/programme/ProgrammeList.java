// @@author Atulteja

package programme;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static common.Utils.NULL_INTEGER;

public class ProgrammeList {

    private static final Logger logger = Logger.getLogger(ProgrammeList.class.getName());

    int currentActiveProgramme;
    private final ArrayList<Programme> programmeList;

    public ProgrammeList() {
        programmeList = new ArrayList<>();
        logger.log(Level.INFO, "ProgrammeList created with an empty list.");
    }

    public ArrayList<Programme> getProgrammeList() {
        return programmeList;
    }

    public int getProgrammeListSize(){
        logger.log(Level.INFO, "Getting programme list size: {0}", programmeList.size());
        return programmeList.size();
    }

    public Programme insertProgramme(String programmeName, ArrayList<Day> days) {
        Programme programmeToAdd = new Programme(programmeName, days);
        programmeList.add(programmeToAdd);
        return programmeToAdd;
    }

    public Programme deleteProgram(int index){
        if (index == NULL_INTEGER){
            index = currentActiveProgramme;
        }

        if (index < 0 || index >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for deleteProgram()", index);
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for programme list.");
        }

        Programme programmeToDelete = programmeList.get(index);
        programmeList.remove(index);
        logger.log(Level.INFO, "Deleted programme at index {0}: {1}", new Object[]{index, programmeToDelete});
        return programmeToDelete;
    }

    public Programme getProgramme(int index){
        if (index == NULL_INTEGER){
            index = currentActiveProgramme;
        }

        if (index < 0 || index >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for getProgramme()", index);
            throw new IndexOutOfBoundsException("Index " + index + 1 + " is out of bounds for programme list.");
        }

        logger.log(Level.INFO, "Retrieving programme at index {0}: {1}", new Object[]{index, programmeList.get(index)});
        return programmeList.get(index);
    }

    public Programme startProgramme(int startIndex) {
        if (startIndex < 0 || startIndex >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for startProgramme()", startIndex);
            throw new IndexOutOfBoundsException("Index " + startIndex + " is out of bounds for programme list.");
        }

        currentActiveProgramme = startIndex;
        Programme activeProgramme = programmeList.get(currentActiveProgramme);
        logger.log(Level.INFO, "Started programme at index {0}: {1}", new Object[]{startIndex, activeProgramme});
        return activeProgramme;
    }

    @Override
    public String toString() {
        if (programmeList.isEmpty()){
            return "No programmes found.";
        }

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < programmeList.size(); i++) {
            Programme programme = programmeList.get(i);
            str.append(i + 1).append(". ").append(programme.getProgrammeName());
            if (i == currentActiveProgramme) {
                str.append(" -- Active");
            }
            str.append("\n");
        }
        return str.toString();
    }

}
