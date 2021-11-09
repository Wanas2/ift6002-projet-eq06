package ca.ulaval.glo4002.game.domain.food;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    private final FoodType FOOD_TYPE = FoodType.BURGER;
    private final int FOOD_QUANTITY = 4;
    private Food food;

    @BeforeEach
    void setUp() {
        food = new Food(FOOD_TYPE, FOOD_QUANTITY);
    }

    @Test
    public void givenAFoodQuantityToAdd_whenIncreaseQuantity_thenTheQuantityShouldBeIncreased() {
        int foodQuantityToAdd = 5;
        int expectedFoodQuantity = 9;

        food.increaseQuantity(foodQuantityToAdd);

        assertEquals(expectedFoodQuantity, food.quantity());
    }

    @Test
    public void givenAFoodQuantityToDecrease_whenDecreaseQuantity_thenQuantityShouldBeDecreased() {
        int foodQuantityToDecrease = 2;
        int expectedFoodQuantity = FOOD_QUANTITY-foodQuantityToDecrease;

        food.decreaseQuantity(foodQuantityToDecrease);

        assertEquals(expectedFoodQuantity, food.quantity());
    }

    @Test
    public void givenAQuantityGreaterOrEqualThanTheFoodQuantity_whenDecreaseQuantity_thenTheRemainingFoodQuantityShouldBeZero() {
        int foodQuantityToDecrease = FOOD_QUANTITY+2;
        int expectedRemainingFoodQuantity = 0;

        food.decreaseQuantity(foodQuantityToDecrease);

        assertEquals(expectedRemainingFoodQuantity, food.quantity());
    }
}
