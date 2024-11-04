// @@author andreusxcarvalho

package common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static final int NULL_INTEGER = -1;

    public static final float NULL_FLOAT = -1.0f;

    public static final String DATE_FORMAT = "dd-MM-yyyy";

    public static boolean isNull(int val) {
        return val == -1;
    }

    public static boolean isNull(String val) {
        return val == null || val.isEmpty();
    }

    public static String formatDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(formatter);
    }
}
