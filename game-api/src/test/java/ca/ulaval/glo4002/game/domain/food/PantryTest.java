package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;


class PantryTest {

    private final static int QUANTITY_OF_FOOD_OF_ZERO = 0;
    private final static int A_QUANTITY_OF_ONE_BURGER_ORDERED = 1;
    private final static int A_QUANTITY_OF_TWO_BURGER_ORDERED = 2;
    private final static int A_QUANTITY_OF_SIX_BURGER_ORDERED = 6;
    private final static int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final static int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private List<Food> foodWithQuantityZero;
    private List<Food> foodWithOnlyOneBurger;
    private List<Food> foodWithOnlyTwoBurgers;
    private List<Food> foodWithOnlySixBurgers;
    private FoodProvider foodProvider;
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
        foodProvider = mock(CookItSubscription.class);
        pantry = new Pantry(foodProvider);
    }

    @Test
    public void givenZeroFoodOrdered_whenObtainNewlyOrderedFood_theFoodIsNotYetStored() {
        initializeFoodWithQuantityZero();

        pantry.obtainNewlyOrderedFood(foodWithQuantityZero);

        assertTrue(pantry.getAllFreshFood().isEmpty());
    }

    @Test
    public void givenSomeFoodOrdered_whenObtainNewlyOrderedFood_thenPantryStillHasNoFreshFood() {
        initializeFoodWithQuantityZero();

        pantry.obtainNewlyOrderedFood(foodWithOnlyOneBurger);

        assertTrue(pantry.getAllFreshFood().isEmpty());
    }

    @Test
    public void whenStoreFood_thenFoodFromTheProviderIsObtained() {
        pantry.storeFood();

        verify(foodProvider).provideFood();
    }

    @Test
    public void givenSomeNewlyFoodOrdered_whenStoreFood_PantryNowHasFreshFood() {
        when(foodProvider.provideFood()).thenReturn(foodWithOnlySixBurgers);
        pantry.obtainNewlyOrderedFood(foodWithOnlyOneBurger);

        pantry.storeFood();

        assertFalse(pantry.getAllFreshFood().isEmpty());
    }

    @Test
    public void givenSomeNewlyFoodOrdered_whenStoreFood_thenTheTotalOfNewFoodIsStored() {
        int totalBurgerNewBurgers = 7;
        when(foodProvider.provideFood()).thenReturn(foodWithOnlySixBurgers);
        pantry.obtainNewlyOrderedFood(foodWithOnlyOneBurger);

        pantry.storeFood();
        Optional<Food> freshBurgersInPantry = pantry.getAllFreshFood().peek().stream()
                .filter(food -> food.getType().equals(FoodType.BURGER))
                .findAny();

        assertEquals(totalBurgerNewBurgers, freshBurgersInPantry.get().quantity());
    }

    @Test
    public void givenNotEnoughFoodToProvide_whenGiveExactOrMostPossibleBurgerDesired_thenAllFreshFoodIsConsumed() {
        int requestedQuantityOfBurgers = 10;
        int expectedRemainingFreshBurgers = 0;
        int expectedConsumedBurgers = 7;
        when(foodProvider.provideFood()).thenReturn(foodWithOnlySixBurgers);
        pantry.obtainNewlyOrderedFood(foodWithOnlyOneBurger);

        pantry.giveExactOrMostPossibleBurgerDesired(requestedQuantityOfBurgers);
        int quantityOfAllFreshBurgersInPanty = pantry.getAllFreshFood().stream()
                .flatMap(foodBatch -> foodBatch.stream())
                .filter(foodInTheBatch -> foodInTheBatch.getType().equals(FoodType.BURGER))
                .mapToInt(Food::quantity).sum();
        int quantityOfAllConsumedBurgersInPanty = pantry.getAllConsumedFood().stream()
                .filter(food -> food.getType().equals(FoodType.BURGER))
                .mapToInt(Food::quantity).sum();

//        assertEquals(expectedRemainingFreshBurgers, quantityOfAllFreshBurgersInPanty);
//        assertEquals(expectedConsumedBurgers, quantityOfAllConsumedBurgersInPanty);
    }

    private void initializeFoodWithQuantityZero() {
        Food aFoodItem1 = new Food(FoodType.BURGER, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem2 = new Food(FoodType.SALAD, QUANTITY_OF_FOOD_OF_ZERO);
        Food aFoodItem3 = new Food(FoodType.WATER, QUANTITY_OF_FOOD_OF_ZERO);
        foodWithQuantityZero = new ArrayList<>();

        foodWithQuantityZero.add(aFoodItem1);
        foodWithQuantityZero.add(aFoodItem2);
        foodWithQuantityZero.add(aFoodItem3);
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
