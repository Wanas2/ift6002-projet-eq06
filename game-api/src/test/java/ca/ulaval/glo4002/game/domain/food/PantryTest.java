package ca.ulaval.glo4002.game.domain.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PantryTest {

    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private Map<FoodType, Food> foods;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        aFoodItem1 = mock(Food.class);
        aFoodItem2 = mock(Food.class);
        aFoodItem3 = mock(Food.class);
        foods = new HashMap<>();
        foods.put(FoodType.BURGER, aFoodItem1);
        foods.put(FoodType.SALAD, aFoodItem2);
        foods.put(FoodType.WATER, aFoodItem3);
        pantry = new Pantry();
    }

    @Disabled
    @Test
    void whenAddFood_thenFoodShouldBeAvailable() {
        pantry.addFood(foods);

        boolean isThereFood = pantry.provideFood();
        assertTrue(isThereFood);
    }

    @Test
    public void whenProvideFood_then() {

    }

    @Test
    void testAddFood() {

    }

    @Test
    void updateAgeOfFoods() {

    }
}