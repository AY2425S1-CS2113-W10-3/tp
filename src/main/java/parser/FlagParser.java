//@@author nirala-ts
package parser;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.Level;
import java.util.logging.Logger;


import static parser.FlagDefinitions.*;
import static parser.ParserUtils.parseIndex;

import static common.Utils.isNull;

import static parser.ParserUtils.parseInteger;
import static parser.ParserUtils.parseIndex;
import static parser.ParserUtils.parseFloat;
import static parser.ParserUtils.parseDate;
import static parser.ParserUtils.splitArguments;

/**
 * The {@code FlagParser} class simplifies the parsing of flagged argument strings. The values can be retrieved in
 * various formats, such as Integer, Date, String, or Index.
 * This class also supports aliasing for easy retrieval of data in different formats.
 *
 * @author nirala-ts
 */
public class FlagParser {
    private static final String DEFAULT_SPLIT_BY = "(?=/)";
    private static final String SPLIT_BY_START = "(?=/(?!(";
    private static final String SPLIT_BY_DELIMITER = "|";
    private static final String SPLIT_BY_END = ")\\b))";

    private final Logger logger = Logger.getLogger(FlagParser.class.getName());

    private final Map<String, String> parsedFlags = new HashMap<>();
    private final Map<String, String> aliasMap = new HashMap<>();

    /**
     * Constructs a {@code FlagParser} with the given argument string, setting up aliases and ignored flags.
     * This parser is essential for processing commands with multiple flags.
     *
     * @param argumentString The argument string to parse.
     * @param ignoredFlags   Flags that are optional for this instance.
     * @throws IllegalArgumentException if {@code argumentString} is null.
     */
    public FlagParser(String argumentString, String... ignoredFlags) {
        if (isNull(argumentString)){
            throw new IllegalArgumentException("ArgumentString: " + argumentString + " is null");
        }

        initializeAliasMap();
        parse(argumentString, generateSplitBy(ignoredFlags));
    }

    /**
     * Generates a regex for splitting the argument string by specified flags and aliases.
     *
     * @param ignoredFlags Flags to ignore while generating the regex split pattern.
     * @return A regex string to split the argument string by flags.
     */
    private String generateSplitBy(String... ignoredFlags){
        if (ignoredFlags.length == 0){
            return DEFAULT_SPLIT_BY;
        }

        // Starts building the regex pattern, initializing with specific syntax for ignored flags
        StringBuilder splitBy = new StringBuilder(SPLIT_BY_START);
        for (String ignoredFlag: ignoredFlags) {
            splitBy.append(ignoredFlag.substring(1)).append(SPLIT_BY_DELIMITER);

            // Checks for aliases and adds them to the pattern if they map to the ignored flag
            for (Map.Entry<String, String> entry: aliasMap.entrySet()){
                String canonicalFlag = entry.getValue();
                String aliasFlag = entry.getKey();
                if (canonicalFlag.equals(ignoredFlag)){
                    splitBy.append(aliasFlag.substring(1)).append(SPLIT_BY_DELIMITER);
                }
            }
        }

        // Removes the trailing delimiter added after the last flag to prevent invalid regex syntax
        splitBy.setLength(splitBy.length() - 1);

        return splitBy.append(SPLIT_BY_END).toString();
    }

    /**
     * Sets up flag aliases to allow flexible parsing by recognizing alternative names for flags.
     */
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

    /**
     * Parses the argument string by splitting it based on the given regex and populates
     * the {@code parsedFlags} map with flag-value pairs.
     *
     * @param argumentString The string to parse.
     * @param splitBy The regex used to split the argument string by flags.
     */
    private void parse(String argumentString, String splitBy) {
        assert argumentString != null : "Argument string must not be null";

        String[] args = argumentString.split(splitBy);
        for (String arg : args) {

            String[] argParts = splitArguments(arg);
            String flag = argParts[0].trim();
            String value = argParts[1].trim();
            flag = resolveAlias(flag);

            logger.log(Level.INFO, "Successfully parsed flag: {0} with value: {1}", new Object[]{flag, value});
            parsedFlags.put(flag, value);
        }
    }

    /**
     * Resolves the alias for a given flag, returning the canonical flag if an alias is found.
     *
     * @param flag The flag or alias to resolve.
     * @return The canonical flag, or the original flag if no alias is found.
     */
    private String resolveAlias(String flag) {
        if (aliasMap.containsKey(flag)) {
            return aliasMap.get(flag);
        }
        return flag;
    }

    /**
     * Checks if a flag is present in the parsed flags.
     *
     * @param flag The flag to check.
     * @return {@code true} if the flag is present, {@code false} otherwise.
     */
    public boolean hasFlag(String flag) {
        assert flag != null && !flag.isEmpty() : "Flag must not be null or empty";

        flag = resolveAlias(flag);
        boolean hasFlag = parsedFlags.containsKey(flag);

        logger.log(Level.INFO, "Flag {0} presence: {1}", new Object[]{flag, hasFlag});
        return hasFlag;
    }

    /**
     * Validates that all required flags are present in the parsed flags and contains a non-null value.
     *
     * @param requiredFlags The required flags to validate.
     * @throws IllegalArgumentException if any required flag is missing.
     */
    public void validateRequiredFlags(String... requiredFlags) {
        assert requiredFlags != null : "Required flags string must not be null";

        for (String flag : requiredFlags) {

            flag = resolveAlias(flag);

            if (!hasFlag(flag)) {
                logger.log(Level.WARNING, "Missing required flag: {0}", flag);
                throw new IllegalArgumentException("Required flag: " + flag + " is missing.");
            }

            String value = getStringByFlag(flag);

            if (isNull(value)) {
                logger.log(Level.WARNING, "Required flag has null value: {0}", flag);
                throw new IllegalArgumentException("Required flag: " + flag + " has no associated value.");
            }
        }
    }

    /**
     * Retrieves the string value associated with a flag.
     *
     * @param flag The flag whose value to retrieve.
     * @return The value associated with the flag, or {@code null} if the flag is absent.
     */
    public String getStringByFlag(String flag) {
        assert flag != null && !flag.isEmpty() : "Flag must not be null or empty";

        flag = resolveAlias(flag);

        if (!parsedFlags.containsKey(flag)) {
            logger.log(Level.INFO, "Flag {0} not found; returning null", flag);
            return null;
        }

        String value = parsedFlags.get(flag);
        logger.log(Level.INFO, "Successfully retrieved value for flag {0}: {1}", new Object[]{flag, value});
        return value.trim();
    }

    /**
     * Retrieves the zero-based index associated with a flag.
     *
     * @param flag The flag whose index to retrieve.
     * @return The zero-based index parsed from the flag's value.
     */
    public int getIndexByFlag(String flag) {
        String indexString = getStringByFlag(flag);
        return parseIndex(indexString);
    }

    /**
     * Retrieves the integer value associated with a flag.
     *
     * @param flag The flag whose integer value to retrieve.
     * @return The integer parsed from the flag's value.
     */
    public int getIntegerByFlag(String flag){
        String intString = getStringByFlag(flag);
        return parseInteger(intString);
    }

    /**
     * Retrieves the float value associated with a flag.
     *
     * @param flag The flag whose float value to retrieve.
     * @return The float parsed from the flag's value.
     */
    public float getFloatByFlag(String flag) {
        String floatString = getStringByFlag(flag);
        return parseFloat(floatString);
    }

    /**
     * Retrieves the date value associated with a flag.
     *
     * @param flag The flag whose date value to retrieve.
     * @return The {@code LocalDate} parsed from the flag's value.
     */
    public LocalDate getDateByFlag(String flag){
        String dateString = getStringByFlag(flag);
        return parseDate(dateString);
    }
}
