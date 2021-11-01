package ca.ulaval.glo4002.game.domain.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CookItSubscriptionTest {

    private int EXPECTED_BURGER_QUANTITY = 100;
    private int EXPECTED_SALAD_QUANTITY = 250;
    private int EXPECTED_WATER_QUANTITY = 10000;

    private List <Food> foods;
    private CookItSubscription cookItSubscription;

    @BeforeEach
    public void setUp() {
        cookItSubscription = new CookItSubscription();
        foods = new ArrayList<>();
    }

    @Test
    public void whenProvidingFood_thenFoodShouldBeProvided() {
        foods = cookItSubscription.provideFood();

        assertFalse(foods.isEmpty());
    }

    @Test
    public void whenProvidingFood_thenTheRightAmountOfFoodShouldBeProvided() { // Todo On peut tester seulement pour un FoodType ici?
        foods = cookItSubscription.provideFood();
        Food burgers = foods.stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null);
        Food salads = foods.stream().
                filter(food -> food.getType().equals(FoodType.SALAD)).
                findAny().orElse(null);
        Food water = foods.stream().
                filter(food -> food.getType().equals(FoodType.WATER)).
                findAny().orElse(null);

        assertEquals(EXPECTED_BURGER_QUANTITY, burgers.quantity());
        assertEquals(EXPECTED_SALAD_QUANTITY, salads.quantity());
        assertEquals(EXPECTED_WATER_QUANTITY, water.quantity());
    }
}