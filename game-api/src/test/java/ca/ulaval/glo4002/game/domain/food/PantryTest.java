package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class PantryTest {

    private final int QUANTITY_OF_FOOD_OF_ZERO = 0;
    private final int A_QUANTITY_OF_ONE_BURGER_ORDERED = 1;
    private final int A_QUANTITY_OF_TWO_BURGER_ORDERED = 2;
    private final int A_QUANTITY_OF_SIX_BURGER_ORDERED = 6;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private List<Food> aGroupOfFoodWithQuantityZero;
    private List<Food> foodWithOnlyOneBurger;
    private List<Food> foodWithOnlyTwoBurgers;
    private List<Food> foodWithOnlySixBurgers;
    private CookItSubscription cookItSubscription;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        initializeFoodWithOnlyOneBurger();
        initializeFoodWithOnlyTwoBurgers();
        initializeSomeFood();

        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_ONE_BURGER_ORDERED;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;
        cookItSubscription = new CookItSubscription();
        pantry = new Pantry(cookItSubscription);
    }

    @Test
    public void givenZeroFoodOrdered_whenAddOrderedFoodToCurrentTurnFoodBatch_thenPantryHasNoFreshFood() {
        initializeFoodWithQuantityZero();

        pantry.addOrderedFoodToCurrentTurnFoodBatch(aGroupOfFoodWithQuantityZero);

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
        List<Food> foodFromCookIt = cookItSubscription.provideFood();
        int expectedBurgerQuantity = foodFromCookIt.stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null)
                .quantity();

        pantry.addCurrentTurnFoodBatchToFreshFood();
        List<Food> allFreshFood = pantry.getAllFreshFood().peek();
        Food freshBurgers = allFreshFood.stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null);

        assertEquals(expectedBurgerQuantity, freshBurgers.quantity());
    }


    @Test
    public void givenSomeFoodOrderedInCurrentTurn_whenAddCurrentTurnFoodBatchToFreshFood_thenTheFoodBatchIsAddedToFreshFood() {
        List<Food> foodFromCookIt = cookItSubscription.provideFood();
        int expectedBurgerQuantity = foodFromCookIt.stream().
                filter(food -> food.getType().equals(FoodType.BURGER))
                .findAny().orElse(null)
                .quantity() + 1;

        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyOneBurger);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        List<Food> allFreshFood = pantry.getAllFreshFood().peek();
        Food freshBurgers = allFreshFood.stream().
                filter(food -> food.getType().equals(FoodType.BURGER))
                .findAny().orElse(null);

        assertEquals(expectedBurgerQuantity, freshBurgers.quantity());
    }

    @Test
    public void givenNotEnoughBurgersToProvide_whenGiveExactOrMostPossibleBurgerDesired_thenAllFreshBurgersAreConsumed() {
        int requestedQuantityOfBurgers = 102;
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyOneBurger);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        int expectedRemainingFreshBurgers = 0;
        int expectedConsumedBurgers = 101;

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        List<Food> allFreshFood = pantry.getAllFreshFood().peek();
        int freshBurgersQuantity = allFreshFood.stream().
                filter(food -> food.getType().equals(FoodType.BURGER))
                .findAny().orElse(null).quantity();
        int consumedBurgersQuantity = pantry.getAllConsumedFood().stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null).
                quantity();

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
        int consumedBurgersQuantity = pantry.getAllConsumedFood().stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null).
                quantity();

        assertEquals(requestedQuantityOfBurgers, consumedBurgersQuantity);
        assertEquals(expectedFreshBurgerQuantityRemaining, requestedQuantityOfBurgers);
    }

    @Test
    public void givenManyBatchedOfFreshFood_whenGiveExactOrMostPossibleBurgerDesired_thenOlderBatchesAreConsumedFirst() {
        int requestedQuantityOfBurgers = 200;
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlyTwoBurgers);
        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foodWithOnlySixBurgers);
        pantry.addCurrentTurnFoodBatchToFreshFood();

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        List<Food> firstBatchOfFreshFood = pantry.getAllFreshFood().remove();
        List<Food> secondBatchOfFreshFood = pantry.getAllFreshFood().remove();
        Optional<Food> burgersFromFirstBatch = firstBatchOfFreshFood.stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny();
        Optional<Food> burgersFromSecondBatch = secondBatchOfFreshFood.stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny();

        assertFalse(burgersFromFirstBatch.isPresent());
        assertFalse(burgersFromSecondBatch.isPresent());
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
        List<Food> secondBatchOfFreshFood = pantry.getAllFreshFood().remove();
        int freshBurgersQuantityAfter = secondBatchOfFreshFood.stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null).quantity();
        int consumedBurgersQuantity = pantry.getAllConsumedFood().stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null).
                quantity();

        assertEquals(requestedQuantityOfBurgers, consumedBurgersQuantity);
        assertEquals(expectedFreshBurgerQuantityRemaining, freshBurgersQuantityAfter);
    }

    @Test
    public void whenRemoveExpiredFoodFromFreshFood_thenExpiredFoodIsRemovedFromFreshFood() {
        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();
        pantry.removeExpiredFoodFromFreshFood();

        Optional<Food> freshBurgers = pantry.getAllFreshFood().peek().stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny();

        assertFalse(freshBurgers.isPresent());
    }

    @Test
    public void whenRemoveExpiredFoodFromFreshFood_thenExpiredFoodIsAddedSavedAsExpired() {
        int expectedExpiredBurgers = 100;

        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.incrementFreshFoodAges();
        pantry.incrementFreshFoodAges();
        pantry.removeExpiredFoodFromFreshFood();

        int expiredBurgersQuantity = pantry.getAllExpiredFood().stream().
                filter(food -> food.getType().equals(FoodType.BURGER)).
                findAny().orElse(null).
                quantity();

        assertEquals(expectedExpiredBurgers, expiredBurgersQuantity);
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
        aGroupOfFoodWithQuantityZero = new ArrayList<>();

        aGroupOfFoodWithQuantityZero.add(aFoodItem1);
        aGroupOfFoodWithQuantityZero.add(aFoodItem2);
        aGroupOfFoodWithQuantityZero.add(aFoodItem3);
    }

    private void initializeFoodWithOnlyOneBurger() {
        Food aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_ONE_BURGER_ORDERED);
        Food aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithOnlyOneBurger = new ArrayList<>();

        foodWithOnlyOneBurger.add(aFoodItem1);
        foodWithOnlyOneBurger.add(aFoodItem2);
        foodWithOnlyOneBurger.add(aFoodItem3);
    }

    private void initializeFoodWithOnlyTwoBurgers() {
        Food aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_TWO_BURGER_ORDERED);
        Food aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithOnlyTwoBurgers = new ArrayList<>();

        foodWithOnlyTwoBurgers.add(aFoodItem1);
        foodWithOnlyTwoBurgers.add(aFoodItem2);
        foodWithOnlyTwoBurgers.add(aFoodItem3);
    }

    private void initializeSomeFood() {
        Food aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_SIX_BURGER_ORDERED);
        Food aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        Food aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        foodWithOnlySixBurgers = new ArrayList<>();

        foodWithOnlySixBurgers.add(aFoodItem1);
        foodWithOnlySixBurgers.add(aFoodItem2);
        foodWithOnlySixBurgers.add(aFoodItem3);
    }
}
