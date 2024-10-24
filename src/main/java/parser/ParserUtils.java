package parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/*
    PaserUtils is a utility class containing common methods used across all parsing functions
 */

public class ParserUtils {

    public static String[] splitArguments(String argumentString) {
        assert argumentString != null : "Argument string must not be null";
        String[] inputArguments = argumentString.split(" ", 2);
        String command = inputArguments[0];
        String args = (inputArguments.length > 1) ? inputArguments[1] : "";
        return new String[]{command, args};
    }

    public static int parseInteger(String intString){
        assert intString != null : "intString must not be null";

        intString = intString.trim();

        if (intString.isEmpty()){
            throw new IllegalArgumentException("intString is empty.");
        }
        try{
            return Integer.parseInt(intString);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("intString is not an integer.");
        }
    }

    public static float parseFloat(String floatString) {
        assert floatString != null : "floatString must not be null";

        floatString = floatString.trim();

        if (floatString.isEmpty()) {
            throw new IllegalArgumentException("floatString is empty.");
        }
        try {
            return Float.parseFloat(floatString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("floatString is not a valid float.");
        }
    }

    public static int parseIndex(String indexString) {
        int index = parseInteger(indexString) - 1;
        if (index < 0){
            throw new IllegalArgumentException("index must not be negative.");
        }
        return index;
    }

    public static LocalDate parseDate(String dateString) {
        assert dateString != null && !dateString.trim().isEmpty() : "Date string must not be null or empty";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: dd-MM-yyyy. " +
                    "Error: " + e.getParsedString(), e);
        }
    }
}
