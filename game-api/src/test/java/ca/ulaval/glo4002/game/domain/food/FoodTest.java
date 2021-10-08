package ca.ulaval.glo4002.game.domain.food;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodTest {

    private final FoodType A_FOOD_TYPE = FoodType.BURGER;
    private final int A_QUANTITY = 4;
    private final int A_LIFE_SPAN = A_FOOD_TYPE.numberOfTurnBeforeExpiry();
    private Food food;

    @BeforeEach
    void setUp() {
        food = new Food(A_FOOD_TYPE, A_QUANTITY);
    }

    @Test
    public void foodIsFreshOnInit() {
        assertFalse(food.isExpired());
    }

    @Test
    public void whenLifeSpanComingToAnEnd_thenFoodShouldBeExpired() {
        for(int i = 0; i < A_LIFE_SPAN; i++){
            food.incrementAgeByOne();
        }
        assertTrue(food.isExpired());
    }

    @Test
    public void whenIncrease_thenQuantityShouldBeIncreased() {
        final int ADDED_QUANTITY = 5;
        food.increaseQuantity(ADDED_QUANTITY);
        int expectedQuantity = 9;

        assertEquals(expectedQuantity, food.quantity());
    }

    @Test
    public void whenDecreaseRemovedQuantityLowerOrEqualThanQuantity_thenQuantityShouldBeDecrease() {
        final int REMOVED_QUANTITY = A_QUANTITY - 1;
        food.decreaseQuantity(REMOVED_QUANTITY);
        int expectedQuantity = A_QUANTITY - REMOVED_QUANTITY;
        assertEquals(expectedQuantity, food.quantity());
    }

    @Test
    public void whenDecreaseRemovedQuantityGreaterThanQuantity_thenQuantityShouldBeZero() {
        final int REMOVED_QUANTITY = A_QUANTITY + 2;
        food.decreaseQuantity(REMOVED_QUANTITY);
        int expectedQuantity = 0;
        assertEquals(expectedQuantity, food.quantity());
    }
}
