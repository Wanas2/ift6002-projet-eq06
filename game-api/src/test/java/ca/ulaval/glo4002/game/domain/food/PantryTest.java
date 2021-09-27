package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.AssertTrue;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PantryTest {

    private final int QUANTITY_OF_FOOD_OF_ZERO = 0;
    private final int A_QUANTITY_OF_ONE_BURGER_ORDERED = 1;
    private final int A_QUANTITY_OF_TWO_BURGER_ORDERED = 2;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;

    private FoodDTO aFoodDTO;
    private Map<FoodType, Food> someFood;
    private Map<FoodType, Food> foodWithOnlyOneBurger;
    private Map<FoodType, Food> foodWithOnlyTwoBurgers;
    private CookItSubscription cookItSubscription;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        initializeFoodWithOnlyOneBurger();
        initializeFoodWithOnlyTwoBurgers();
        initializeSomeFood();
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_ONE_BURGER_ORDERED;
        aFoodDTO.qtySalad =  A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater =  A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;

        cookItSubscription = mock(CookItSubscription.class);
        pantry = new Pantry();
    }

    @Test
    public void givenAFoodRequestButNoFoodAddedToFreshFoodStorage_whenProvideFood_thenNoFoodIsProvided() {
        pantry.orderFood(foodWithOnlyTwoBurgers);

        assertFalse(pantry.provideFood(foodWithOnlyOneBurger));
    }

    @Disabled
    @Test
    public void givenAFoodRequestAndEnoughFoodIsAddedToFreshFoodStorage_whenProvideFood_thenPantryProvideTheFood() {
        pantry.orderFood(foodWithOnlyTwoBurgers);
        pantry.addNewFoodToFreshFoodStorage();

        boolean isThereEnoughFoodInStorage = pantry.provideFood(foodWithOnlyOneBurger);

        assertTrue(isThereEnoughFoodInStorage);
    }

    @Test
    public void givenNotEnoughFood_whenProvideFood_thenReturnFalse() { // Todo the name
        pantry.orderFood(foodWithOnlyOneBurger);

        assertFalse(pantry.provideFood(foodWithOnlyTwoBurgers));
    }

    private void initializeFoodWithOnlyOneBurger() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_ONE_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithOnlyOneBurger = new HashMap<>();

        foodWithOnlyOneBurger.put(FoodType.BURGER, aFoodItem1);
        foodWithOnlyOneBurger.put(FoodType.SALAD, aFoodItem2);
        foodWithOnlyOneBurger.put(FoodType.WATER, aFoodItem3);
    }

    private void initializeFoodWithOnlyTwoBurgers() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_TWO_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithOnlyTwoBurgers = new HashMap<>();

        foodWithOnlyOneBurger.put(FoodType.BURGER, aFoodItem1);
        foodWithOnlyOneBurger.put(FoodType.SALAD, aFoodItem2);
        foodWithOnlyOneBurger.put(FoodType.WATER, aFoodItem3);
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_ONE_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        someFood = new HashMap<>();

        someFood.put(FoodType.BURGER, aFoodItem1);
        someFood.put(FoodType.SALAD, aFoodItem2);
        someFood.put(FoodType.WATER, aFoodItem3);
    }
}
