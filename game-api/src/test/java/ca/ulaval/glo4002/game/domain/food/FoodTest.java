package ca.ulaval.glo4002.game.domain.food;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FoodTest {

    private final FoodType FOOD_TYPE_BURGER = FoodType.BURGER;
    private int aQuantity = 4;
    private Food someFoodOfTypeBurger;
    private Food someMoreFoodOfTypeBurger;

    @BeforeEach
    void setUp() {
        someFoodOfTypeBurger = new Food(FOOD_TYPE_BURGER, aQuantity);
        someMoreFoodOfTypeBurger = new Food(FOOD_TYPE_BURGER, aQuantity);
    }

    @Test
    public void givenSomeMoreFood_whenIncrease_thenQuantityShouldBeIncreasedAccordingly() throws FoodTypeNotMatchingException {
        int expectedQuantity = someFoodOfTypeBurger.quantity() + someMoreFoodOfTypeBurger.quantity(); // Todo C'est bon ou pas?

        someFoodOfTypeBurger.increaseQuantity(someMoreFoodOfTypeBurger);

        assertEquals(expectedQuantity, someFoodOfTypeBurger.quantity());
    }

    // Todo Faut-il tester le cas où le food type est différent. C'est quoi le "assert" ici?
}
