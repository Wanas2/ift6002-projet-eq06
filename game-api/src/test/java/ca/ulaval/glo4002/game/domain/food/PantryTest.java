package ca.ulaval.glo4002.game.domain.food;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PantryTest {

    private final int A_QUANTITY_OF_BURGER_ORDERED = 3;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private Map<FoodType, Food> someFood;
    private CookItSubscription cookItSubscription;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        initializeSomeFood();
        cookItSubscription = mock(CookItSubscription.class);
        pantry = new Pantry();
    }

    @Test
    public void whenOrderFood_thenPantryIsAbleToProvideFood() {
        pantry.orderFood();

        assertTrue(pantry.provideFood(someFood));
    }

    @Test
    public void whenAddNewBatchToFreshFood_thenCookItSubscriptionShouldProvideFoodToPantry() {
        pantry.addNewBatchOfFoodToFreshFood(cookItSubscription);

        verify(cookItSubscription).provideFood();
    }

    @Test
    public void givenFoodRequested_whenAddNewBatchToFreshFood_thenPantryIsAbleToProvideFood() {
        pantry.addNewBatchOfFoodToFreshFood(cookItSubscription);

        assertTrue(pantry.provideFood(someFood));
    }

    @Test
    public void givenNotEnoughFreshFood_whenProvideFood_thenShouldInformTheRequester() {
//        pantry.addNewBatchOfFoodToFreshFood();
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        someFood = new HashMap<>();
        someFood.put(FoodType.BURGER, aFoodItem1);
        someFood.put(FoodType.SALAD, aFoodItem2);
        someFood.put(FoodType.WATER, aFoodItem3);
    }
}
