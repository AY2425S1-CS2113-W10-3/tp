package parser;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

import static common.Utils.NULL_FLOAT;
import static common.Utils.NULL_INTEGER;
import static parser.ParserUtils.parseFloat;
import static parser.ParserUtils.parseIndex;
import static parser.ParserUtils.parseInteger;
import static parser.ParserUtils.parseDate;


/*
    FlagParser simplifies parsing flagged argument strings
    From an argument string, creates a hashmap of flag -> value
    These values can then be retrieved in Integer, Date, String or Index formats
*/
public class FlagParser {
    private final Logger logger = Logger.getLogger(FlagParser.class.getName());
    private final Map<String, String> parsedFlags = new HashMap<>();
    private final Map<String, String> aliasMap = new HashMap<>();

    public FlagParser(String argumentString) {
        initializeAliasMap();
        if (argumentString != null && !argumentString.trim().isEmpty()) {
            parse(argumentString);
        }
    }

    private void initializeAliasMap() {
        aliasMap.put("/p", "/p");
        aliasMap.put("/progIndex", "/p");
        aliasMap.put("/programme", "/p");

        aliasMap.put("/day", "/d");
        aliasMap.put("/date", "/t");

        aliasMap.put("/name", "/n");
        aliasMap.put("/exercise", "/e");
        aliasMap.put("/set", "/s");
        aliasMap.put("/rep", "/r");
        aliasMap.put("/weight", "/w");

        aliasMap.put("/createEx", "/a");
        aliasMap.put("/updateEx", "/u");
        aliasMap.put("/removeEx", "/x");
        aliasMap.put("/createDay", "/ad");
        aliasMap.put("/removeDay", "/xd");

        aliasMap.put("/mealName", "/n");
        aliasMap.put("/mealCalories", "/c");
        aliasMap.put("/mealIndex", "/m");

        aliasMap.put("/waterAmount", "/l");
        aliasMap.put("/waterIndex", "/w");
    }

    private void parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        String[] args = argumentString.trim().split(" (?=/)");
        for (String arg : args) {
            String[] argParts = arg.split(" ", 2);

            if (argParts.length < 2) {
                throw new IllegalArgumentException("Please provide a valid value.");
            }

            String flag = argParts[0].trim();
            String value = argParts[1].trim();

            flag = resolveAlias(flag);
            logger.log(Level.INFO, "Parsed flag: {0} with value: {1}", new Object[]{flag, value});
            parsedFlags.put(flag, value);
        }
    }

    private String resolveAlias(String flag) {
        if (aliasMap.containsKey(flag)) {
            return aliasMap.get(flag);
        }
        return flag;
    }

    private boolean hasFlag(String flag) {
        assert flag != null && !flag.isEmpty() : "Flag must not be null or empty";

        flag = resolveAlias(flag);
        boolean hasFlag = parsedFlags.containsKey(flag);

        logger.log(Level.INFO, "Flag {0} presence: {1}", new Object[]{flag, hasFlag});
        return hasFlag;
    }


    public void validateRequiredFlags(String... requiredFlags) {
        assert requiredFlags != null : "Required flags string must not be null";

        for (String flag : requiredFlags) {
            flag = resolveAlias(flag);
            if (!hasFlag(flag)) {
                throw new IllegalArgumentException("Required flag: " + flag + "is missing. Please provide the flag.");
            }
        }
    }

    public String getStringByFlag(String flag) {
        assert flag != null && !flag.isEmpty() : "Flag must not be null or empty";

        flag = resolveAlias(flag);

        if (!parsedFlags.containsKey(flag)) {
            return null;
        }

        String value = parsedFlags.get(flag);
        logger.log(Level.INFO, "Successfully retrieved value for flag {0}: {1}", new Object[]{flag, value});
        return value.trim();
    }

    public int getIndexByFlag(String flag) {
        String indexString = getStringByFlag(flag);
        if (indexString == null) {
            return NULL_INTEGER;
        }
        return parseIndex(indexString);
    }

    public int getIntegerByFlag(String flag){
        String intString = getStringByFlag(flag);
        if (intString == null) {
            return NULL_INTEGER;
        }
        return parseInteger(intString);
    }

    public float getFloatByFlag(String flag) {
        String floatString = getStringByFlag(flag);
        if (floatString == null) {
            return NULL_FLOAT;
        }
        return parseFloat(floatString);
    }

    public LocalDate getDateByFlag(String flag){
        String dateString = getStringByFlag(flag);
        if (dateString == null) {
            return LocalDate.now();
        }
        return parseDate(dateString);
    }
}
