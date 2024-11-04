package meal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealListTest {

    private MealList mealList;
    private Meal sampleMeal;

    @BeforeEach
    public void setUp() {
        mealList = new MealList();
        sampleMeal = new Meal("Sample Meal", 300);
    }

    @Test
    public void testIsEmptyHappyPath() {
        assertTrue(mealList.isEmpty(), "Meal list " +
                "should be empty initially.");
    }

    @Test
    public void testIsEmptyEdgeCaseNonEmptyList() {
        mealList.addMeal(sampleMeal);
        assertFalse(mealList.isEmpty(), "Meal list should not be empty after adding a meal.");
    }

    @Test
    public void testIsEmptyEdgeCaseEmptyAfterDeletion() {
        mealList.addMeal(sampleMeal);
        mealList.deleteMeal(0);
        assertTrue(mealList.isEmpty(), "Meal list " +
                "should be empty after adding and then deleting the only meal.");
    }

    @Test
    public void testGetSizeHappyPath() {
        mealList.addMeal(sampleMeal);
        assertEquals(1, mealList.getSize(), "Size should be 1 after adding one meal.");
    }

    @Test
    public void testGetSizeEdgeCaseEmptyList() {
        assertEquals(0, mealList.getSize(), "Size should be 0 for an empty meal list.");
    }

    @Test
    public void testGetSizeEdgeCaseMultipleMeals() {
        mealList.addMeal(sampleMeal);
        mealList.addMeal(new Meal("Another Meal", 500));
        assertEquals(2, mealList.getSize(), "Size should be 2 after adding two meals.");
    }

    @Test
    public void testAddMealHappyPath() {
        mealList.addMeal(sampleMeal);
        assertEquals(1, mealList.getSize(), "Size should be 1 after adding one meal.");
        assertTrue(mealList.getMeals().contains(sampleMeal), "Meal list should " +
                "contain the added meal.");
    }

    @Test
    public void testAddMealEdgeCaseNullMeal() {
        assertThrows(AssertionError.class, () -> mealList.addMeal(null), "Adding a null " +
                "meal should throw an AssertionError.");
    }

    @Test
    public void testAddMealEdgeCaseDuplicateMeals() {
        mealList.addMeal(sampleMeal);
        mealList.addMeal(sampleMeal);
        assertEquals(2, mealList.getSize(), "Size should " +
                "be 2 after adding the same meal twice.");
    }

    @Test
    public void testDeleteMealHappyPath() {
        mealList.addMeal(sampleMeal);
        Meal deletedMeal = mealList.deleteMeal(0);
        assertEquals(deletedMeal, sampleMeal, "Deleted meal should be equal to the meal added.");
        assertTrue(mealList.isEmpty(), "Meal list should be empty after deleting the only meal.");
    }

    @Test
    public void testDeleteMealEdgeCaseNegativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> mealList.deleteMeal(-1),
                "Deleting with a negative index should throw IndexOutOfBoundsException.");
    }

    @Test
    public void testDeleteMealEdgeCaseIndexOutOfBounds() {
        mealList.addMeal(sampleMeal);
        assertThrows(IndexOutOfBoundsException.class, () -> mealList.deleteMeal(1),
                "Deleting with an out-of-bounds index should throw IndexOutOfBoundsException.");
    }

    @Test
    public void testGetMealsHappyPath() {
        mealList.addMeal(sampleMeal);
        ArrayList<Meal> meals = mealList.getMeals();
        assertEquals(1, meals.size(), "Meal list should have one meal after adding one.");
        assertEquals(meals.get(0), sampleMeal, "The meal retrieved should be equal to the added meal.");
    }

    @Test
    public void testGetMealsEdgeCaseEmptyList() {
        ArrayList<Meal> meals = mealList.getMeals();
        assertTrue(meals.isEmpty(), "Retrieved list should be empty for a new MealList instance.");
    }

    @Test
    public void testGetMealsEdgeCaseMultipleMeals() {
        Meal anotherMeal = new Meal("Another Meal", 500);
        mealList.addMeal(sampleMeal);
        mealList.addMeal(anotherMeal);
        ArrayList<Meal> meals = mealList.getMeals();
        assertEquals(2, meals.size(), "Meal list should contain two meals after adding two.");
        assertTrue(meals.get(0).equals(sampleMeal) && meals.get(1).equals(anotherMeal),
                "Meals should be equal to those added.");
    }
}
