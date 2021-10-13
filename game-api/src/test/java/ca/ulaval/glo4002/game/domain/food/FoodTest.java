package ca.ulaval.glo4002.game.domain.food;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodTest {

    private final FoodType A_FOOD_TYPE = FoodType.BURGER;
    private final int A_FOOD_QUANTITY = 4;
    private Food food;

    @BeforeEach
    void setUp() {
        food = new Food(A_FOOD_TYPE, A_FOOD_QUANTITY);
    }

    @Test
    public void initiallyFoodIsFresh() {
        assertFalse(food.isExpired());
    }

    @Test
    public void whenIncrementAgeByOne_thenFoodShouldBeExpiredIfTheNumberOfTurnBeforeExpiryIsReached() {
        food.incrementAgeByOne();
        food.incrementAgeByOne();

        assertTrue(food.isExpired());
    }

    @Test
    public void givenAFoodQuantityToAdd_increaseQuantity_thenTheQuantityShouldBeIncreased() {
        int foodQuantityToAdd = 5;
        int expectedFoodQuantity = 9;

        food.increaseQuantity(foodQuantityToAdd);

        assertEquals(expectedFoodQuantity, food.quantity());
    }

    @Test
    public void givenAFoodQuantityToDecrease_whenDecreaseQuantity_thenQuantityShouldBeDecreased() {
        int foodQuantityToDecrease = 2;
        int expectedFoodQuantity = A_FOOD_QUANTITY - foodQuantityToDecrease;

        food.decreaseQuantity(foodQuantityToDecrease);

        assertEquals(expectedFoodQuantity, food.quantity());
    }

    @Test
    public void givenAQuantityGreaterOrEqualThanTheFoodQuantity_whenDecreaseQuantity_thenTheRemainingFoodQuantityShouldBeZero() {
        int foodQuantityToDecrease = A_FOOD_QUANTITY + 2;
        int expectedRemainingFoodQuantity = 0;

        food.decreaseQuantity(foodQuantityToDecrease);

        assertEquals(expectedRemainingFoodQuantity, food.quantity());
    }
}
