package ca.ulaval.glo4002.game.domain.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FoodQuantitySummaryCalculatorTest {

    private final static int FRESH_BURGER_QUANTITY = 90;
    private final static int CONSUMED_BURGER_QUANTITY = 50;
    private final static int EXPIRED_BURGER_QUANTITY = 10;
    private final static int FRESH_SALAD_QUANTITY = 120;
    private final static int CONSUMED_SALAD_QUANTITY = 80;
    private final static int EXPIRED_SALAD_QUANTITY = 30;
    private final static int FRESH_WATER_QUANTITY = 300;
    private final static int CONSUMED_WATER_QUANTITY = 190;
    private final static int EXPIRED_WATER_QUANTITY = 110;
    private final static String FRESH_FOOD = "fresh";
    private final static String CONSUMED_FOOD = "consumed";
    private final static String EXPIRED_FOOD = "expired";

    private List<Food> allFreshFood;
    private Map<FoodType, Integer> freshFoodQuantities;
    private Map<FoodType, Integer> consumedFoodQuantities;
    private Map<FoodType, Integer> expiredFoodQuantities;
    private Pantry pantry;
    private FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;

    @BeforeEach
    public void setUp() {
        initializeAllFreshFood();
        freshFoodQuantities = new HashMap<>(){{
            put(FoodType.BURGER, FRESH_BURGER_QUANTITY);
            put(FoodType.SALAD, FRESH_SALAD_QUANTITY);
            put(FoodType.WATER, FRESH_WATER_QUANTITY);
        }};
        consumedFoodQuantities = new HashMap<>(){{
            put(FoodType.BURGER, CONSUMED_BURGER_QUANTITY);
            put(FoodType.SALAD, CONSUMED_SALAD_QUANTITY);
            put(FoodType.WATER, CONSUMED_WATER_QUANTITY);
        }};
        expiredFoodQuantities = new HashMap<>(){{
            put(FoodType.BURGER, EXPIRED_BURGER_QUANTITY);
            put(FoodType.SALAD, EXPIRED_SALAD_QUANTITY);
            put(FoodType.WATER, EXPIRED_WATER_QUANTITY);
        }};
        pantry = mock(Pantry.class);
        foodQuantitySummaryCalculator = new FoodQuantitySummaryCalculator();
    }

    @Test
    public void givenAPantry_whenComputeSummary_thenFoodSummaryShouldBeProvided() {
        when(pantry.getAllFreshFood()).thenReturn(allFreshFood);
        when(pantry.getConsumedFoodQuantities()).thenReturn(consumedFoodQuantities);
        when(pantry.getExpiredFoodQuantities()).thenReturn(expiredFoodQuantities);

        Map<String, Map<FoodType, Integer>> expectedFoodSummary = foodQuantitySummaryCalculator.computeSummaries(pantry);

        assertEquals(expectedFoodSummary.get(FRESH_FOOD), freshFoodQuantities);
        assertEquals(expectedFoodSummary.get(CONSUMED_FOOD), consumedFoodQuantities);
        assertEquals(expectedFoodSummary.get(EXPIRED_FOOD), expiredFoodQuantities);
    }

    private void initializeAllFreshFood() {
        Food freshBurger = new Food(FoodType.BURGER, FRESH_BURGER_QUANTITY);
        Food freshSalad = new Food(FoodType.SALAD, FRESH_SALAD_QUANTITY);
        Food freshWater = new Food(FoodType.WATER, FRESH_WATER_QUANTITY);

        allFreshFood = new LinkedList<>(Arrays.asList(freshBurger, freshSalad, freshWater));
    }
}