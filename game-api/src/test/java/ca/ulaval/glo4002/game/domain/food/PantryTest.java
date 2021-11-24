package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.domain.food.foodDistribution.FoodDistributor;
import ca.ulaval.glo4002.game.domain.food.foodDistribution.WaterSplitter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.*;


class PantryTest {

    private static final FoodType A_FOOD_TYPE_1 = FoodType.BURGER;
    private static final FoodType A_FOOD_TYPE_2 = FoodType.SALAD;
    private static final FoodType A_FOOD_TYPE_3 = FoodType.WATER;
    private static final int A_FOOD_QUANTITY_1 = 12;
    private static final int A_FOOD_QUANTITY_2 = 5;
    private static final int A_FOOD_QUANTITY_3 = 8;

    private Food food1;
    private Food food2;
    private Food foodExpiringAfterMoreThanThreeTurns;
    private FoodHistory foodHistory;
    private FoodDistributor foodDistributor;
    private WaterSplitter waterSplitter;
    private FoodProvider foodProvider;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        food1 = new Food(A_FOOD_TYPE_1, A_FOOD_QUANTITY_1);
        food2 = new Food(A_FOOD_TYPE_2, A_FOOD_QUANTITY_2);
        foodExpiringAfterMoreThanThreeTurns = new Food(A_FOOD_TYPE_1, A_FOOD_QUANTITY_2);
        foodProvider = mock(CookItSubscription.class);
        foodDistributor = new FoodDistributor();
        waterSplitter = mock(WaterSplitter.class);
        foodHistory = mock(FoodHistory.class);
        pantry = new Pantry(foodProvider, foodDistributor, waterSplitter, foodHistory);
    }

    @Test
    public void initiallyNoFoodIsStoredInPantry() {
        assertTrue(pantry.getAllFreshFoods().isEmpty());
    }

    @Test
    public void givenSomeFood_whenObtainNewlyOrderedFood_thenPantryStillHasNoFreshFood() {
        List<Food> orderedFoods = new ArrayList<>();
        orderedFoods.add(food1);
        orderedFoods.add(food2);

        pantry.obtainNewlyOrderedFoods(orderedFoods);

        assertTrue(pantry.getAllFreshFoods().isEmpty());
    }

    @Test
    public void givenSomeFoodOrdered_whenStoreAllNewlyOrderedFoods_thenPantryNowHasFreshFood() {
        List<Food> orderedFoods = new ArrayList<>();
        orderedFoods.add(food1);
        orderedFoods.add(food2);

        pantry.obtainNewlyOrderedFoods(orderedFoods);
        pantry.storeAllNewlyOrderedFoods();

        assertFalse(pantry.getAllFreshFoods().isEmpty());
    }

    @Test
    public void whenStoreAllNewlyOrderedFoods_thenTheFoodProviderShouldProvideFoodToPantry() {
        pantry.storeAllNewlyOrderedFoods();

        verify(foodProvider).provideFood();
    }

    @Test
    public void givenSomeExpiredFood_whenIncrementFreshFoodAges_ThenTheQuantityOfTheExpiredFoodShouldBeIncremented() {
        List<Food> orderedFoods = new ArrayList<>();
        orderedFoods.add(foodExpiringAfterMoreThanThreeTurns);
        pantry.obtainNewlyOrderedFoods(orderedFoods);
        pantry.storeAllNewlyOrderedFoods();

        Food storedFreshFood = pantry.getAllFreshFoods().get(0);
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();

        verify(foodHistory).increaseExpiredQuantity(storedFreshFood);
    }

    @Test
    public void givenSomeNonExpiredFood_whenIncrementFreshFoodAges_ThenTheQuantityOfTheExpiredFoodShouldNotBeIncremented() {
        List<Food> orderedFoods = new ArrayList<>();
        orderedFoods.add(foodExpiringAfterMoreThanThreeTurns);
        pantry.obtainNewlyOrderedFoods(orderedFoods);
        pantry.storeAllNewlyOrderedFoods();

        Food storedFreshFood = pantry.getAllFreshFoods().get(0);
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();

        verify(foodHistory, never()).increaseExpiredQuantity(storedFreshFood);
    }

    @Test
    public void givenSomeFreshFood_whenSplitWaterInTwo_TheTheWaterIsSplit() {
        pantry.splitWaterInTwo();

        verify(waterSplitter).splitWater(pantry.getAllFreshFoods());
    }
}
