package common;

public class Utils {

    public static final int NULL_INTEGER = -1;

    public static final float NULL_FLOAT = -1.0f;

    public static boolean isNull(int val) {
        return val == -1;
    }

    public static boolean isNull(String val) {
        return val == null || val.isEmpty();
    }
}
