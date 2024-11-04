// @@author Atulteja

package programme;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Programme {
    private static final Logger logger = Logger.getLogger(Programme.class.getName());

    private final String programmeName;
    private final ArrayList<Day> dayList;

    public Programme(String programmeName, ArrayList<Day> dayList) {
        assert programmeName != null && !programmeName.isEmpty() : "Programme name cannot be null or empty";
        assert dayList != null : "Day list cannot be null";

        this.programmeName = programmeName;
        this.dayList = dayList;

        logger.log(Level.INFO, "Programme created: {0}", this);
    }

    public String getProgrammeName() {
        return programmeName;
    }

    public Day getDay(int index){
        if (index < 0 || index >= dayList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for getDay()", index);
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for day list.");
        }
        logger.log(Level.INFO, "Retrieving day at index {0}: {1}", new Object[]{index, dayList.get(index)});
        return dayList.get(index);
    }

    public void insertDay(Day day) {
        assert day != null : "Day to insert cannot be null";
        logger.log(Level.INFO, "Inserting day: {0}", day);

        dayList.add(day);
    }

    public int getDayCount() {
        logger.log(Level.INFO, "Getting day count: {0}", dayList.size());
        return dayList.size();
    }

    public Day deleteDay(int index){
        if (index < 0 || index >= dayList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for deleteDay()", index);
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for day list.");
        }
        Day toBeDeleted = dayList.get(index);
        dayList.remove(index);
        logger.log(Level.INFO, "Deleted day at index {0}: {1}", new Object[]{index, toBeDeleted});
        return toBeDeleted;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(programmeName).append("\n\n");

        for (int i = 0; i < dayList.size(); i++) {
            str.append("Day ").append(i+1).append(": ").append(dayList.get(i)).append("\n");
        }

        return str.toString();
    }
}
