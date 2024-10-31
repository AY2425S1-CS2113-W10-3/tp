package parser;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;

import static parser.FlagDefinitions.*;
import static parser.ParserUtils.parseIndex;
import static parser.ParserUtils.parseInteger;
import static parser.ParserUtils.parseFloat;
import static parser.ParserUtils.parseDate;
import static parser.ParserUtils.splitArguments;

/*
    FlagParser simplifies parsing flagged argument strings
    From an argument string, creates a hashmap of flag -> value
    These values can then be retrieved in Integer, Date, String or Index formats
*/
public class FlagParser {
    private final Logger logger = Logger.getLogger(FlagParser.class.getName());
    private final Map<String, String> parsedFlags = new HashMap<>();
    private final Map<String, String> aliasMap = new HashMap<>();
    private String splitBy = " (?=/)";

    public FlagParser(String argumentString, String... ignoredFlags) {
        if (ignoredFlags.length > 0){
            StringBuilder splitBy = new StringBuilder("(?=/(?![");
            for (String flag: ignoredFlags) {
                splitBy.append(flag.charAt(1));
            }
            this.splitBy = splitBy.append("]\\b))").toString();
        }
        initializeAliasMap();
        if (argumentString != null && !argumentString.trim().isEmpty()) {
            parse(argumentString);
        }
    }

    private void initializeAliasMap() {
        aliasMap.put(PROGRAMME_FLAG, PROGRAMME_FLAG);
        aliasMap.put("/progIndex", PROGRAMME_FLAG);
        aliasMap.put("/programme", PROGRAMME_FLAG);

        aliasMap.put("/day", DAY_FLAG);
        aliasMap.put("/date", DATE_FLAG);

        aliasMap.put("/name", NAME_FLAG);
        aliasMap.put("/exercise", EXERCISE_FLAG);
        aliasMap.put("/set", SETS_FLAG);
        aliasMap.put("/rep", REPS_FLAG);
        aliasMap.put("/weight", WEIGHT_FLAG);

        aliasMap.put("/createEx", ADD_EXERCISE_FLAG);
        aliasMap.put("/updateEx", UPDATE_EXERCISE_FLAG);
        aliasMap.put("/removeEx", REMOVE_EXERCISE_FLAG);
        aliasMap.put("/createDay", ADD_DAY_INDEX);
        aliasMap.put("/removeDay", REMOVE_DAY_INDEX);

        aliasMap.put("/mealName", NAME_FLAG);
        aliasMap.put("/mealCalories", MEAL_CALORIES);
        aliasMap.put("/mealIndex", MEAL_INDEX);

        aliasMap.put("/waterAmount", VOLUME_FLAG);
        aliasMap.put("/waterIndex", WEIGHT_FLAG);
    }

    private void parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        String[] args = argumentString.trim().split(this.splitBy);
        for (String arg : args) {
            String[] argParts = splitArguments(arg);

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

    public boolean hasFlag(String flag) {
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
                throw new IllegalArgumentException("Required flag: " + flag + " is missing. Please provide the flag.");
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
        return parseIndex(indexString);
    }

    public int getIntegerByFlag(String flag){
        String intString = getStringByFlag(flag);
        return parseInteger(intString);
    }

    public float getFloatByFlag(String flag) {
        String floatString = getStringByFlag(flag);
        return parseFloat(floatString);
    }

    public LocalDate getDateByFlag(String flag){
        String dateString = getStringByFlag(flag);
        return parseDate(dateString);
    }
}
