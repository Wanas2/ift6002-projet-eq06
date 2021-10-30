package ca.ulaval.glo4002.game.domain.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CookItSubscriptionTest {

    private final int EXPECTED_BURGER_QUANTITY = 100;
    private final int EXPECTED_SALAD_QUANTITY = 250;
    private final int EXPECTED_WATER_QUANTITY = 10000;

    private CookItSubscription cookItSubscription;
    private Map<FoodType, Food> foods;

    @BeforeEach
    public void setUp() {
        cookItSubscription = new CookItSubscription();
        foods = new HashMap<>();
    }

    @Test
    public void whenProvidingFood_thenFoodShouldBeProvided() {
        foods = cookItSubscription.provideFood();

        assertFalse(foods.isEmpty());
    }

    @Test
    public void whenProvidingFood_thenTheRightAmountOfFoodShouldBeProvided() {
        foods = cookItSubscription.provideFood();

        assertEquals(EXPECTED_BURGER_QUANTITY, foods.get(FoodType.BURGER).quantity());
        assertEquals(EXPECTED_SALAD_QUANTITY, foods.get(FoodType.SALAD).quantity());
        assertEquals(EXPECTED_WATER_QUANTITY, foods.get(FoodType.WATER).quantity());
    }
}