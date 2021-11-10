package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.*;


class PantryTest {

    private final int QUANTITY_OF_FOOD_OF_ZERO = 0;
    private final int A_QUANTITY_OF_ONE_BURGER_ORDERED = 1;
    private final int A_QUANTITY_OF_TWO_BURGER_ORDERED = 2;
    private final int A_QUANTITY_OF_SIX_BURGER_ORDERED = 6;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

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
    public void whenStoreFoodInPantry_thenFoodFromTheProviderIsObtained() {
        pantry.storeFood();

        verify(foodProvider).provideFood();
    }

    @Test
    public void givenZeroFoodOrdered_whenObtainNewlyOrderedFood_theFoodIsNotYetStored() {
        initializeFoodWithQuantityZero();
        int pantryBurgerQuantity;

//        pantry.obtainNewlyOrderedFood(foodWithQuantityZero);
//        pantryBurgerQuantity = pantry.getAllFreshFood().
//                peek().stream().
//                filter(food -> food.getType().equals(FoodType.BURGER)).
//                mapToInt(Food::quantity).findAny().orElse(0);
//
//        assertFalse(pantryBurgerQuantity > 0);
    }

//    @Test
//    public void givenSomeFoodOrdered_whenObtainNewlyOrderedFood_thenPantryStillHasNoFreshFood() {
//        initializeFoodWithQuantityZero();
//        int pantryBurgerQuantity;
//
//        pantry.obtainNewlyOrderedFood(foodWithOnlyOneBurger);
//        pantryBurgerQuantity = pantry.getAllFreshFood().peek().stream().
//                filter(food -> food.getType().equals(FoodType.BURGER)).
//                mapToInt(Food::quantity).findAny().orElse(0);
//
//        assertFalse(pantryBurgerQuantity > 0);
//    }

//    @Test
//    public void givenSomeFoodOrdered_whenStoreFood_PantryNowHasFreshFood() {
//        when(foodProvider.provideFood()).thenReturn(foodWithOnlySixBurgers);
//        int pantryBurgerQuantity;
//
//        pantry.obtainNewlyOrderedFood(foodWithOnlyOneBurger);
//        pantry.storeFood();
//        pantryBurgerQuantity = pantry.getAllFreshFood().peek().stream().
//                filter(food -> food.getType().equals(FoodType.BURGER)).
//                mapToInt(Food::quantity).findAny().orElse(0);
//
//        assertTrue(pantryBurgerQuantity > 0);
//    }

//    @Test
//    public void givenSomeFoodOrderedInCurrentTurn_whenStoreFood_thenTheFoodStoredIsFresh() {
//        when(foodProvider.provideFood()).thenReturn(foodWithOnlySixBurgers);
//
//        pantry.obtainNewlyOrderedFood(foodWithOnlyTwoBurgers);
//        pantry.storeFood();
//        int allFreshFoodQuantityInPantry = pantry.getAllFreshFood().peek().stream().
//                filter(food -> food.getState().equals(FoodState.FRESH)).
//                mapToInt(Food::quantity).sum();
//
//        assertTrue(allFreshFoodQuantityInPantry > 0); // Todo modifier le then
//    }

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
