package history;

import meal.Meal;
import meal.MealList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import programme.Day;
import programme.Exercise;
import water.Water;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class DailyRecordTest {
    private DailyRecord dailyRecord;
    private Day validDay;
    private Meal meal1;
    private Meal meal2;

    @BeforeEach
    public void setUp() {
        validDay = new Day("validDay");
        validDay.insertExercise(new Exercise(3, 12, 50, 120,"Bench_Press"));
        validDay.insertExercise(new Exercise(3, 12, 80, 200, "Squat"));

        meal1 = new Meal("potato", 100);
        meal2 = new Meal("pasta", 900);
    }

    @AfterEach
    public void tearDown() {
        dailyRecord = null;
        validDay = null;
        meal1 = null;
        meal2 = null;
    }

    @Test
    public void testConstructor_default() {
        dailyRecord = new DailyRecord();
        assertNotNull(dailyRecord.getWaterFromRecord());
        assertNotNull(dailyRecord.getMealListFromRecord());
    }

    @Test
    public void logDay_validDay() {
        dailyRecord = new DailyRecord();
        dailyRecord.logDayToRecord(validDay);

        assertEquals(validDay, dailyRecord.getDayFromRecord());
    }

    @Test
    public void logDay_emptyDay() {
        dailyRecord = new DailyRecord();
        Day emptyDay = new Day("Empty Day");
        dailyRecord.logDayToRecord(emptyDay);
        assertEquals(emptyDay, dailyRecord.getDayFromRecord());
    }

    @Test
    public void addMealToRecord_validMeals() {
        dailyRecord = new DailyRecord();
        dailyRecord.addMealToRecord(meal1);
        dailyRecord.addMealToRecord(meal2);
        assertFalse(dailyRecord.getMealListFromRecord().isEmpty());
        assertEquals(2, dailyRecord.getMealListFromRecord().getSize());
        assertEquals("pasta", dailyRecord.getMealListFromRecord().getMeals().get(1).getName());
    }

    @Test
    public void addMealToRecord_negativeCaloriesMeal() {
        dailyRecord = new DailyRecord();
        assertThrows(AssertionError.class, () -> dailyRecord.addMealToRecord(new Meal("potato", -100)));
    }

    @Test
    public void addMealToRecord_nullMeal() {
        dailyRecord = new DailyRecord();
        assertThrows(AssertionError.class, () -> dailyRecord.addMealToRecord(null));
    }

    @Test
    public void deleteMealFromRecord_validIndex() {
        dailyRecord = new DailyRecord();
        dailyRecord.addMealToRecord(meal1);
        dailyRecord.addMealToRecord(meal2);
        dailyRecord.deleteMealFromRecord(0);
        assertEquals(1, dailyRecord.getMealListFromRecord().getSize());
        assertEquals("pasta", dailyRecord.getMealListFromRecord().getMeals().get(0).getName());
    }

    @Test
    public void deleteMealFromRecord_negativeIndex() {
        dailyRecord = new DailyRecord();
        dailyRecord.addMealToRecord(meal1);
        assertThrows(AssertionError.class, () -> dailyRecord.deleteMealFromRecord(-1));
    }

    @Test
    public void deleteMealFromRecord_outOfBoundsIndex() {
        dailyRecord = new DailyRecord();
        dailyRecord.addMealToRecord(meal1);
        assertThrows(IndexOutOfBoundsException.class, () -> dailyRecord.deleteMealFromRecord(10));
    }

    @Test
    public void addWaterToRecord_validWater() {
        dailyRecord = new DailyRecord();
        dailyRecord.addWaterToRecord(100.0f);
        dailyRecord.addWaterToRecord(400.0f);
        assertFalse(dailyRecord.getWaterFromRecord().isEmpty());
        assertEquals(2, dailyRecord.getWaterFromRecord().getWaterList().size());
        assertEquals(100.0f, dailyRecord.getWaterFromRecord().getWaterList().get(0));
    }

    @Test
    public void addWaterToRecord_negativeWater() {
        dailyRecord = new DailyRecord();
        assertThrows(AssertionError.class, () -> dailyRecord.addWaterToRecord(-500.0f));
    }

    @Test
    public void removeWaterFromRecord_validIndex() {
        dailyRecord = new DailyRecord();
        dailyRecord.addWaterToRecord(100.0f);
        dailyRecord.addWaterToRecord(400.0f);
        dailyRecord.removeWaterFromRecord(0);
        assertEquals(1, dailyRecord.getWaterFromRecord().getWaterList().size());
        assertEquals(400.0f, dailyRecord.getWaterFromRecord().getWaterList().get(0));
    }

    @Test
    public void removeWaterFromRecord_negativeIndex() {
        dailyRecord = new DailyRecord();
        dailyRecord.addWaterToRecord(100.0f);
        assertThrows(IndexOutOfBoundsException.class, () -> dailyRecord.removeWaterFromRecord(-1));
    }

    @Test
    public void removeWaterFromRecord_outOfBoundsIndex() {
        dailyRecord = new DailyRecord();
        dailyRecord.addWaterToRecord(100.0f);
        assertThrows(IndexOutOfBoundsException.class, () -> dailyRecord.removeWaterFromRecord(10));
    }

    @Test
    public void getDayFromRecord_initialDay() {
        dailyRecord = new DailyRecord();
        assertNull(dailyRecord.getDayFromRecord());
    }

    @Test
    public void getDayFromRecord_afterLogDay() {
        dailyRecord = new DailyRecord();
        dailyRecord.logDayToRecord(validDay);
        assertEquals("validDay", dailyRecord.getDayFromRecord().getName());
    }

    @Test
    public void getMealList_initialMealList() {
        dailyRecord = new DailyRecord();
        MealList mealList = dailyRecord.getMealListFromRecord();
        assertNotNull(mealList);
        assertTrue(mealList.getMeals().isEmpty());
    }

    @Test
    public void getMealList_afterAddMeal() {
        dailyRecord = new DailyRecord();
        dailyRecord.addMealToRecord(meal1);
        dailyRecord.addMealToRecord(meal2);
        MealList mealList = dailyRecord.getMealListFromRecord();
        assertEquals("potato" , mealList.getMeals().get(0).getName());
        assertEquals("pasta", mealList.getMeals().get(1).getName());
    }

    @Test
    public void getMealList_afterDeleteMeal() {
        dailyRecord = new DailyRecord();
        dailyRecord.addMealToRecord(meal1);
        dailyRecord.addMealToRecord(meal2);
        dailyRecord.deleteMealFromRecord(0);
        assertEquals(1, dailyRecord.getMealListFromRecord().getSize());
    }

    @Test
    public void getWater_initialWater() {
        dailyRecord = new DailyRecord();
        Water water = dailyRecord.getWaterFromRecord();
        assertNotNull(water);
        assertTrue(water.getWaterList().isEmpty());
    }

    @Test
    public void getWater_afterAddWater() {
        dailyRecord = new DailyRecord();
        dailyRecord.addWaterToRecord(100.0f);
        dailyRecord.addWaterToRecord(400.0f);
        Water water = dailyRecord.getWaterFromRecord();
        assertEquals(100.0f , water.getWaterList().get(0));
        assertEquals(400.0f , water.getWaterList().get(1));
    }

    @Test
    public void getWater_afterRemoveWater() {
        dailyRecord = new DailyRecord();
        dailyRecord.addWaterToRecord(100.0f);
        dailyRecord.addWaterToRecord(400.0f);
        dailyRecord.removeWaterFromRecord(0);
        assertEquals(1, dailyRecord.getWaterFromRecord().getWaterList().size());
    }

    @Test
    public void toString_emptyRecord() {
        MealList mockMealList = mock(MealList.class);
        Water mockWater = mock(Water.class);
        Day mockDay = mock(Day.class);

        dailyRecord = spy(new DailyRecord());

        when(mockMealList.getMeals()).thenReturn(new ArrayList<Meal>());
        when(mockWater.getWaterList()).thenReturn(new ArrayList<Float>());
        when(mockDay.getExercisesCount()).thenReturn(0);

        doReturn(mockMealList).when(dailyRecord).getMealListFromRecord();
        doReturn(mockDay).when(dailyRecord).getDayFromRecord();
        doReturn(mockWater).when(dailyRecord).getWaterFromRecord();

        String result = dailyRecord.toString();

        assertTrue(result.contains("No Day"));
        assertTrue(result.contains("No Water"));
        assertTrue(result.contains("No Meals"));
    }

    @Test
    public void toString_callsGetCaloriesFromMeal() {
        dailyRecord = new DailyRecord();
        dailyRecord.addMealToRecord(meal1);
        dailyRecord.addMealToRecord(meal2);
        String result = dailyRecord.toString();
        assertTrue(result.contains("Total Calories from Meals:"));
    }

    @Test
    public void toString_callsGetTotalWater() {
        dailyRecord = new DailyRecord();
        dailyRecord.addWaterToRecord(100.0f);
        dailyRecord.addWaterToRecord(400.0f);
        String result = dailyRecord.toString();
        assertTrue(result.contains("Total Water Intake:"));
    }

    @Test
    public void toString_testDayToString() {
        dailyRecord = new DailyRecord();
        dailyRecord.logDayToRecord(validDay);
        String result = dailyRecord.toString();
        assertFalse(result.contains("No Day"));
    }
}
