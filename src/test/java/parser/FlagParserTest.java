package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FlagParserTest {

    private FlagParser flagParser;

    @BeforeEach
    void setUp() {
        String argumentString = "/p 1 /d Day1 /t 12-12-2023 /w 2.5 /n TestExercise /s 3 /r 10";
        flagParser = new FlagParser(argumentString);
    }

    @Test
    void testHasFlagValidCase() {
        assertTrue(flagParser.hasFlag("/p"),
                "Expected flag '/p' to be present");
    }

    @Test
    void testHasFlagMissingFlag() {
        assertFalse(flagParser.hasFlag("/m"),
                "Expected flag '/m' to be absent");
    }

    @Test
    void testHasFlagEmptyFlag() {
        assertThrows(AssertionError.class, () -> flagParser.hasFlag(""),
                "Expected AssertionError for empty flag");
    }

    @Test
    void testValidateRequiredFlagsValidCase() {
        assertDoesNotThrow(() -> flagParser.validateRequiredFlags("/p", "/d", "/t"),
                "Expected no exception for valid flags");
    }

    @Test
    void testValidateRequiredFlagsMissingFlag() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> flagParser.validateRequiredFlags("/p", "/m"),
                "Expected IllegalArgumentException for missing required flag");
        assertTrue(exception.getMessage().contains("/m"));
    }

    @Test
    void testGetStringByFlagValidCase() {
        assertEquals("Day1", flagParser.getStringByFlag("/d"),
                "Expected value 'Day1' for flag '/d'");
    }

    @Test
    void testGetStringByFlagFlagNotPresent() {
        assertNull(flagParser.getStringByFlag("/x"),
                "Expected null for non-existent flag '/x'");
    }

    @Test
    void testGetStringByFlagEmptyFlag() {
        assertThrows(AssertionError.class, () -> flagParser.getStringByFlag(""),
                "Expected AssertionError for empty flag");
    }

    @Test
    void testGetIndexByFlagValidCase() {
        assertEquals(0, flagParser.getIndexByFlag("/p"),
                "Expected zero-based index '0' for flag '/p' with value '1'");
    }

    @Test
    void testGetIndexByFlagInvalidIndex() {
        FlagParser invalidParser = new FlagParser("/p abc");
        assertThrows(IllegalArgumentException.class, () -> invalidParser.getIndexByFlag("/p"),
                "Expected IllegalArgumentException for invalid index");
    }

    @Test
    void testGetIntegerByFlagValidCase() {
        assertEquals(3, flagParser.getIntegerByFlag("/s"),
                "Expected integer value '3' for flag '/s'");
    }

    @Test
    void testGetIntegerByFlagInvalidInteger() {
        FlagParser invalidParser = new FlagParser("/s abc");
        assertThrows(IllegalArgumentException.class, () -> invalidParser.getIntegerByFlag("/s"),
                "Expected IllegalArgumentException for invalid integer");
    }

    @Test
    void testGetFloatByFlagValidCase() {
        assertEquals(2.5f, flagParser.getFloatByFlag("/w"),
                "Expected float value '2.5' for flag '/w'");
    }

    @Test
    void testGetFloatByFlagInvalidFloat() {
        FlagParser invalidParser = new FlagParser("/w abc");
        assertThrows(IllegalArgumentException.class, () -> invalidParser.getFloatByFlag("/w"),
                "Expected IllegalArgumentException for invalid float");
    }

    @Test
    void testGetDateByFlagValidCase() {
        assertEquals(LocalDate.of(2023, 12, 12), flagParser.getDateByFlag("/t"),
                "Expected date '12-12-2023' for flag '/t'");
    }

    @Test
    void testGetDateByFlagInvalidDate() {
        FlagParser invalidParser = new FlagParser("/t 32-12-2023");
        assertThrows(IllegalArgumentException.class, () -> invalidParser.getDateByFlag("/t"),
                "Expected IllegalArgumentException for invalid date");
    }
}
