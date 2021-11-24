package ca.ulaval.glo4002.game.domain.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class FoodHistoryTest {

    private static final FoodType A_FOOD_TYPE = FoodType.BURGER;
    private static final FoodType ANOTHER_FOOD_TYPE = FoodType.SALAD;
    private static final int FOOD_QUANTITY_1 = 12;
    private static final int FOOD_QUANTITY_2 = 5;
    private static final int FOOD_QUANTITY_3 = 8;

    private FoodHistory foodHistory;

    @BeforeEach
    public void setUp() {
        foodHistory = new FoodHistory();
    }

    @Test
    public void initiallyAllFoodQuantitiesInHistoryAreZero() {
        int freshFoodQuantity =
                foodHistory.getFreshFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();
        int consumedFoodQuantity =
                foodHistory.getConsumedFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();
        int expiredFoodQuantity =
                foodHistory.getExpiredFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();

        assertEquals(0, freshFoodQuantity);
        assertEquals(0, consumedFoodQuantity);
        assertEquals(0, expiredFoodQuantity);
    }

    @Test void givenSomeFood_whenIncreaseExpiredQuantity_thenExpiredFoodQuantityIsIncreased() {
        Food food = new Food(A_FOOD_TYPE, FOOD_QUANTITY_1);

        foodHistory.increaseExpiredQuantity(food);

        int expiredFoodQuantity =
                foodHistory.getExpiredFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();

        assertEquals(food.quantity(), expiredFoodQuantity);
    }

    @Test void givenSomeFood_whenIncreaseConsumedQuantity_theConsumedFoodQuantityIsIncreased() {
        Food food = new Food(A_FOOD_TYPE, FOOD_QUANTITY_1);

        foodHistory.increaseConsumedQuantity(food);

        int consumedFoodQuantity =
                foodHistory.getConsumedFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();

        assertEquals(food.quantity(), consumedFoodQuantity);
    }

    @Test
    public void givenSomeFreshFood_whenComputeFreshFoodQuantities_thenTheQuantityMustBeComputed() {
        List<Food> allFreshFoods = new ArrayList<>();
        allFreshFoods.add(new Food(A_FOOD_TYPE, FOOD_QUANTITY_1));
        allFreshFoods.add(new Food(A_FOOD_TYPE, FOOD_QUANTITY_2));
        allFreshFoods.add(new Food(ANOTHER_FOOD_TYPE, FOOD_QUANTITY_2));
        allFreshFoods.add(new Food(ANOTHER_FOOD_TYPE, FOOD_QUANTITY_3));

//        foodHistory.computeFreshFoodQuantities();
    }

    @Test
    public void givenAllFoodQuantitiesAreGreaterThanZero_whenReset_thenAllFoodQuantityShouldBeZero() {
        List<Food> allFreshFoods = new ArrayList<>();
        allFreshFoods.add(new Food(A_FOOD_TYPE, FOOD_QUANTITY_1));
        foodHistory.computeFreshFoodQuantities(allFreshFoods);
        Food food = new Food(A_FOOD_TYPE, FOOD_QUANTITY_1);
        foodHistory.increaseConsumedQuantity(food);
        foodHistory.increaseExpiredQuantity(food);

        foodHistory.reset();
        int freshFoodQuantity =
                foodHistory.getFreshFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();
        int consumedFoodQuantity =
                foodHistory.getConsumedFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();
        int expiredFoodQuantity =
                foodHistory.getExpiredFoodQuantities().values().stream().mapToInt(Integer::intValue).sum();

        assertEquals(0, freshFoodQuantity);
        assertEquals(0, consumedFoodQuantity);
        assertEquals(0, expiredFoodQuantity);
    }
}
