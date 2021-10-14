package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class PantryTest {

    private final int QUANTITY_OF_FOOD_OF_ZERO = 0;
    private final int A_QUANTITY_OF_ONE_BURGER_ORDERED = 1;
    private final int A_QUANTITY_OF_TWO_BURGER_ORDERED = 2;
    private final int A_QUANTITY_OF_SIX_BURGER_ORDERED = 6;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private Map<FoodType, Food> foodWithQuantityZero;
    private Map<FoodType, Food> foodWithOnlyOneBurger;
    private Map<FoodType, Food> foodWithOnlyTwoBurgers;
    private Map<FoodType, Food> foodWithOnlySixBurgers;
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
        cookItSubscription = new CookItSubscription();
        pantry = new Pantry(cookItSubscription);
    }

    @Test
    public void givenZeroFoodOrdered_whenAddOrderedFoodToCurrentTurnFoodBatch_thenPantryHasNoFreshFood() {
        initializeFoodWithQuantityZero();

        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithQuantityZero);

        assertTrue(pantry.getAllFreshFood().isEmpty());
    }

    @Test
    public void givenSomeFoodOrdered_whenAddOrderedFoodToCurrentTurnFoodBatch_thenPantryStillHasNoFreshFood() {
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyOneBurger);

        assertTrue(pantry.getAllFreshFood().isEmpty());
    }

    @Test
    public void givenSomeFoodInCurrentTurnBatch_whenAddCurrentTurnFoodBatchToFreshFood_PantryNowHasFreshFood() {
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyOneBurger);

        pantry.addCurrentTurnFoodBatchToFreshFood();

        assertFalse(pantry.getAllFreshFood().isEmpty());
    }

    @Test
    public void givenFoodFromCookItSubscription_whenAddCurrentTurnFoodBatchToFreshFood_thenFoodFromCookItSubscriptionIsAddedToFreshFood() {
        Map<FoodType, Food> foodFromCookIt = cookItSubscription.provideFood();
        int expectedBurgerQuantity = foodFromCookIt.get(FoodType.BURGER).quantity();
        int expectedSaladQuantity = foodFromCookIt.get(FoodType.SALAD).quantity();
        int expectedWaterQuantity = foodFromCookIt.get(FoodType.WATER).quantity();

        pantry.addCurrentTurnFoodBatchToFreshFood();
        Map<FoodType, Food> allFreshFood = pantry.getAllFreshFood().peek();

        assertEquals(expectedBurgerQuantity, allFreshFood.get(FoodType.BURGER).quantity());
        assertEquals(expectedSaladQuantity, allFreshFood.get(FoodType.SALAD).quantity());
        assertEquals(expectedWaterQuantity, allFreshFood.get(FoodType.WATER).quantity());
    }


    @Test
    public void givenSomeFoodOrderedInCurrentTurn_whenAddCurrentTurnFoodBatchToFreshFood_thenTheFoodBatchIsAddedToFreshFood() {
        Map<FoodType, Food> foodFromCookIt = cookItSubscription.provideFood();
        int expectedBurgerQuantity = foodWithOnlyOneBurger.get(FoodType.BURGER).quantity()
                + foodFromCookIt.get(FoodType.BURGER).quantity();

        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyOneBurger);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        Map<FoodType, Food> allFreshFood = pantry.getAllFreshFood().peek();

        assertEquals(expectedBurgerQuantity, allFreshFood.get(FoodType.BURGER).quantity());
    }

    @Test
    public void givenNotEnoughBurgersToProvide_whenGiveExactOrMostPossibleBurgerDesired_thenAllFreshBurgersAreConsumed() {
        int requestedQuantityOfBurgers = 102;
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyOneBurger);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        int expectedRemainingFreshBurgers = 0;
        int expectedConsumedBurgers = 101;

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        Map<FoodType, Food> allFreshFood = pantry.getAllFreshFood().peek();
        int freshBurgersQuantity = allFreshFood.get(FoodType.BURGER).quantity();
        int consumedBurgersQuantity = pantry.getAllConsumedFood().get(FoodType.BURGER).quantity();

        assertEquals(expectedRemainingFreshBurgers, freshBurgersQuantity);
        assertEquals(expectedConsumedBurgers, consumedBurgersQuantity);
    }

    @Test
    public void givenEnoughBurgersToProvide_whenGiveExactOrMostPossibleBurgerDesired_thenOnlyRequestedBurgerQuantityIsConsumed() {
        int requestedQuantityOfBurgers = 101;
        int expectedFreshBurgerQuantityRemaining = 101;
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyTwoBurgers);
        pantry.addCurrentTurnFoodBatchToFreshFood();

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        int consumedBurgersQuantity = pantry.getAllConsumedFood().get(FoodType.BURGER).quantity();

        assertEquals(requestedQuantityOfBurgers, consumedBurgersQuantity);
        assertEquals( expectedFreshBurgerQuantityRemaining, requestedQuantityOfBurgers);
    }

    @Test
    public void givenManyBatchedOfFreshFood_whenGiveExactOrMostPossibleBurgerDesired_thenOlderBatchesAreConsumedFirst() {
        int requestedQuantityOfBurgers = 200;
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyTwoBurgers);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlySixBurgers);
        pantry.addCurrentTurnFoodBatchToFreshFood();

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        Map<FoodType, Food> firstBatchOfFreshFood = pantry.getAllFreshFood().remove();
        Map<FoodType, Food> secondBatchOfFreshFood = pantry.getAllFreshFood().remove();

        assertFalse(firstBatchOfFreshFood.containsKey(FoodType.BURGER));
        assertTrue(secondBatchOfFreshFood.containsKey(FoodType.BURGER));
    }

    @Test
    public void givenManyBatchedOfFreshFood_whenGiveExactOrMostPossibleBurgerDesired_thenTheAppropriateQuantityOfFoodIsConsumed() {
        int requestedQuantityOfBurgers = 200;
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyTwoBurgers);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlySixBurgers);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        int expectedFreshBurgerQuantityRemaining = 8;

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        pantry.getAllFreshFood().remove();
        Map<FoodType, Food> secondBatchOfFreshFood = pantry.getAllFreshFood().remove();
        int freshBurgersQuantityAfter = secondBatchOfFreshFood.get(FoodType.BURGER).quantity();
        int consumedBurgersQuantity = pantry.getAllConsumedFood().get(FoodType.BURGER).quantity();

        assertEquals(requestedQuantityOfBurgers, consumedBurgersQuantity);
        assertEquals( expectedFreshBurgerQuantityRemaining, freshBurgersQuantityAfter);
    }

    @Test
    public void whenRemoveExpiredFoodFromFreshFood_thenExpiredFoodIsRemovedFromFreshFood() {
        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();
        pantry.removeExpiredFoodFromFreshFood();

        assertFalse(pantry.getAllFreshFood().peek().containsKey(FoodType.BURGER));
        assertFalse(pantry.getAllFreshFood().peek().containsKey(FoodType.SALAD));
        assertFalse(pantry.getAllFreshFood().peek().containsKey(FoodType.WATER));
    }

    @Test
    public void whenRemoveExpiredFoodFromFreshFood_thenExpiredFoodIsAddedSavedAsExpired() {
        int expectedExpiredBurgers = 100;
        int expectedExpiredSalads = 250;
        int expectedExpiredWater = 10000;

        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();
        pantry.removeExpiredFoodFromFreshFood();
        int expiredBurgers = pantry.getAllExpiredFood().get(FoodType.BURGER).quantity();
        int expiredSalads =  pantry.getAllExpiredFood().get(FoodType.SALAD).quantity();
        int expiredWater = pantry.getAllExpiredFood().get(FoodType.WATER).quantity();

        assertEquals(expectedExpiredBurgers, expiredBurgers);
        assertEquals(expectedExpiredSalads, expiredSalads);
        assertEquals(expectedExpiredWater, expiredWater);
    }

    @Test
    public void whenReset_thenPantryShouldHAveNoFreshFood() {
        pantry.addCurrentTurnFoodBatchToFreshFood();

        pantry.reset();

        assertTrue(pantry.getAllFreshFood().isEmpty());
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
        Food aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_SIX_BURGER_ORDERED);
        Food aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        Food aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        foodWithOnlySixBurgers = new HashMap<>();

        foodWithOnlySixBurgers.put(FoodType.BURGER, aFoodItem1);
        foodWithOnlySixBurgers.put(FoodType.SALAD, aFoodItem2);
        foodWithOnlySixBurgers.put(FoodType.WATER, aFoodItem3);
    }
}
