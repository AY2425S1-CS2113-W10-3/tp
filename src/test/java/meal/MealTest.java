package meal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MealTest {

    @Test
    public void testConstructorHappyPath() {
        Meal meal = new Meal("Sample Meal", 300);
        assertEquals("Sample Meal", meal.getName(), "Meal name should match the expected value.");
        assertEquals(300, meal.getCalories(), "Meal calories should match the expected value.");
    }

    @Test
    public void testConstructorEdgeCaseNullName() {
        assertThrows(AssertionError.class, () -> new Meal(null, 300), "Creating " +
                "a Meal with a null name should throw an AssertionError.");
    }

    @Test
    public void testConstructorEdgeCaseEmptyName() {
        assertThrows(AssertionError.class, () -> new Meal("", 300), "Creating " +
                "a Meal with an empty name should throw an AssertionError.");
    }

    @Test
    public void testConstructorEdgeCaseNegativeCalories() {
        assertThrows(AssertionError.class, () -> new Meal("Negative Calories Meal", -100),
                "Create a Meal with negative calories should throw an AssertionError.");
    }

    @Test
    public void testGetCaloriesHappyPath() {
        Meal meal = new Meal("Sample Meal", 250);
        assertEquals(250, meal.getCalories(), "getCalories should " +
                "return the correct calorie count.");
    }

    @Test
    public void testGetNameHappyPath() {
        Meal meal = new Meal("Healthy Salad", 150);
        assertEquals("Healthy Salad", meal.getName(), "getName " +
                "should return the correct meal name.");
    }

    @Test
    public void testEqualsHappyPath() {
        Meal meal1 = new Meal("Same Meal", 400);
        Meal meal2 = new Meal("Same Meal", 400);
        assertEquals(meal1, meal2, "Meals with the same name and calories should be equal.");
    }

    @Test
    public void testEqualsEdgeCaseDifferentCalories() {
        Meal meal1 = new Meal("Same Meal", 400);
        Meal meal2 = new Meal("Same Meal", 300);
        assertNotEquals(meal1, meal2, "Meals with different calories should not be equal.");
    }

    @Test
    public void testEqualsEdgeCaseDifferentName() {
        Meal meal1 = new Meal("Meal One", 400);
        Meal meal2 = new Meal("Meal Two", 400);
        assertNotEquals(meal1, meal2, "Meals with different names should not be equal.");
    }

    @Test
    public void testHashCodeHappyPath() {
        Meal meal1 = new Meal("Hash Meal", 500);
        Meal meal2 = new Meal("Hash Meal", 500);
        assertEquals(meal1.hashCode(), meal2.hashCode(), "Meals with the " +
                "same name and calories should have the same hash code.");
    }

    @Test
    public void testHashCodeEdgeCaseDifferentCalories() {
        Meal meal1 = new Meal("Hash Meal", 500);
        Meal meal2 = new Meal("Hash Meal", 300);
        assertNotEquals(meal1.hashCode(), meal2.hashCode(), "Meals with " +
                "different calories should have different hash codes.");
    }

    @Test
    public void testHashCodeEdgeCaseDifferentName() {
        Meal meal1 = new Meal("Hash Meal One", 500);
        Meal meal2 = new Meal("Hash Meal Two", 500);
        assertNotEquals(meal1.hashCode(), meal2.hashCode(), "Meals with " +
                "different names should have different hash codes.");
    }

    @Test
    public void testToStringHappyPath() {
        Meal meal = new Meal("Sample Meal", 350);
        assertEquals("Sample Meal | 350kcal", meal.toString(), "toString should " +
                "return the correct formatted string.");
    }
}
