// @@author Atulteja
package water;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Water {

    private static final Logger logger = Logger.getLogger(Water.class.getName());
    private final ArrayList<Float> waterList;

    public Water() {
        waterList = new ArrayList<>();
        logger.log(Level.INFO, "Water instance created with an empty list.");
    }

    public void addWater(float water) {
        assert water > 0 : "Water amount must be positive";

        waterList.add(water);
        logger.log(Level.INFO, "{0} liters of water added. Current list: {1}", new Object[]{water, waterList});
    }

    public boolean isEmpty() {
        return waterList.isEmpty();
    }

    public float deleteWater(int index) {
        if (index < 0 || index >= waterList.size()) {
            logger.log(Level.WARNING, "Invalid index for deletion: {0}", index);
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for the water list.");
        }

        float waterToBeDeleted = waterList.get(index);
        waterList.remove(index);
        logger.log(Level.INFO, "Deleted {0} liters of water at index {1}. Current list: {2}",
                new Object[]{waterToBeDeleted, index, waterList});
        return waterToBeDeleted;
    }

    public ArrayList<Float> getWaterList() {
        logger.log(Level.INFO, "Retrieved water list: {0}", waterList);
        return waterList;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        if(waterList.isEmpty()) {
            return "No record.";
        }

        for (int i = 0; i < waterList.size(); i++) {
            output.append(i + 1).append(": ").append(waterList.get(i)).append("\n");
        }

        return output.toString().trim();
    }
}
