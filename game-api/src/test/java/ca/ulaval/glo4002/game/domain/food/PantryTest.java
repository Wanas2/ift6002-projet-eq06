package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;

import javax.validation.constraints.AssertTrue;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PantryTest {

    private final int QUANTITY_OF_FOOD_OF_ZERO = 0;
    private final int A_QUANTITY_OF_ONE_BURGER_ORDERED = 1;
    private final int A_QUANTITY_OF_TWO_BURGER_ORDERED = 2;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private Map<FoodType, Food> foodWithQuantityZero;
    private Map<FoodType, Food> foodWithOnlyOneBurger;
    private Map<FoodType, Food> foodWithOnlyTwoBurgers;
    private Map<FoodType, Food> someFood;
    private CookItSubscription cookItSubscription;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        initializeFoodWithQuantityZero();
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
    public void givenFood_whenAddFoodFromCookITToNewFood_thenCooItShouldProvideFood() {
        willReturn(someFood).given(cookItSubscription).provideFood();

        pantry.addFoodFromCookITToNewFood(cookItSubscription);

        verify(cookItSubscription).provideFood();
    }

    @Test
    public void givenNotEnoughBurgersToProvide_whenGiveExactOrMostPossibleBurgerDesired_thenAllFreshBurgersAreConsumed() { // Todo Les longues lignes sont aussi pas bons dans le tests?
        int aDesiredNumberOfBurgers = 2;
        pantry.addToNewBatchOfFreshFood(foodWithOnlyOneBurger);
        pantry.addNewFoodToFreshFood();
        int expectedRemainingFreshBurgers = 0;
        int expectedConsumedBurgers = 1;

        pantry.giveExactOrMostPossibleBurgerDesired(aDesiredNumberOfBurgers);
        Map<String, Map<FoodType, Integer>> allFoodQuantities = pantry.getFoodQuantitySummary();

        int freshBurgersQuantity = allFoodQuantities.get("fresh").get(FoodType.BURGER);
        int consumedBurgersQuantity = allFoodQuantities.get("consumed").get(FoodType.BURGER);
        assertEquals(expectedRemainingFreshBurgers, freshBurgersQuantity);
        assertEquals(expectedConsumedBurgers, consumedBurgersQuantity);
    }

    @Test
    public void givenEnoughBurgersToProvide_whenGiveExactOrMostPossibleBurgerDesired_thenOnlyRequestedBurgerQuantityIsConsumed() {
        int requestedQuantityOfBurgers = 1;
        int expectedFreshBurgerQuantityRemaining = 1;
        pantry.addToNewBatchOfFreshFood(foodWithOnlyTwoBurgers);
        pantry.addNewFoodToFreshFood();

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        Map<String, Map<FoodType, Integer>>  allFoodQuantities = pantry.getFoodQuantitySummary();
        int freshBurgersQuantityAfter = allFoodQuantities.get("fresh").get(FoodType.BURGER);
        int consumedBurgersQuantity = allFoodQuantities.get("consumed").get(FoodType.BURGER);

        assertEquals(requestedQuantityOfBurgers, consumedBurgersQuantity);
        assertEquals( expectedFreshBurgerQuantityRemaining, freshBurgersQuantityAfter);
    }

    private void initializeFoodWithQuantityZero() {
        Food aFoodItem1 = new Food(FoodType.BURGER, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithQuantityZero = new HashMap<>();

        foodWithQuantityZero.put(FoodType.BURGER, aFoodItem1);
        foodWithQuantityZero.put(FoodType.SALAD, aFoodItem2);
        foodWithQuantityZero.put(FoodType.WATER, aFoodItem3);
    }

    private void initializeFoodWithOnlyOneBurger() {
        Food aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_ONE_BURGER_ORDERED);
        Food aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithOnlyOneBurger = new HashMap<>();

        foodWithOnlyOneBurger.put(FoodType.BURGER, aFoodItem1);
        foodWithOnlyOneBurger.put(FoodType.SALAD, aFoodItem2);
        foodWithOnlyOneBurger.put(FoodType.WATER, aFoodItem3);
    }

    private void initializeFoodWithOnlyTwoBurgers() {
        Food aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_TWO_BURGER_ORDERED);
        Food aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithOnlyTwoBurgers = new HashMap<>();

        foodWithOnlyTwoBurgers.put(FoodType.BURGER, aFoodItem1);
        foodWithOnlyTwoBurgers.put(FoodType.SALAD, aFoodItem2);
        foodWithOnlyTwoBurgers.put(FoodType.WATER, aFoodItem3);
    }

    private void initializeSomeFood() {
        Food aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_ONE_BURGER_ORDERED);
        Food aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        Food aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        someFood = new HashMap<>();

        someFood.put(FoodType.BURGER, aFoodItem1);
        someFood.put(FoodType.SALAD, aFoodItem2);
        someFood.put(FoodType.WATER, aFoodItem3);
    }
}
