package ca.ulaval.glo4002.game.domain.food;

import ca.ulaval.glo4002.game.domain.food.foodDistribution.FoodDistributor;
import ca.ulaval.glo4002.game.domain.food.foodDistribution.WaterSplitter;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.*;


class PantryTest {

    private FoodHistory foodHistory;
    private FoodDistributor foodDistributor;
    private WaterSplitter waterSplitter;
    private FoodProvider foodProvider;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        foodProvider = mock(CookItSubscription.class);
        foodDistributor = new FoodDistributor();
        waterSplitter = new WaterSplitter();
        foodHistory = new FoodHistory();
        pantry = new Pantry(foodProvider, foodDistributor, waterSplitter, foodHistory);
    }

    @Test
    public void initiallyNoFoodIsStoredInPantry() {
        assertTrue(pantry.getAllFreshFoods().isEmpty());
    }

    @Test
    public void givenSomeFoodOrdered_whenObtainNewlyOrderedFood_thenPantryStillHasNoFreshFood() {
//        pantry.obtainNewlyOrderedFoods(foodWithOnlyOneBurger);
//
//        assertTrue(pantry.getAllFreshFoods().isEmpty());
    }

    @Test
    public void whenStoreFood_thenFoodFromTheProviderIsObtained() {
//        pantry.storeAllNewlyOrderedFoods();
//
//        verify(foodProvider).provideFood();
    }

    @Test
    public void givenSomeNewlyFoodOrdered_whenStoreFood_PantryNowHasFreshFood() {
//        when(foodProvider.provideFood()).thenReturn(foodWithOnlySixBurgers);
//        pantry.obtainNewlyOrderedFoods(foodWithOnlyOneBurger);
//
//        pantry.storeAllNewlyOrderedFoods();
//
//        pantry.getAllFreshFoods().forEach(food -> System.out.println(food.quantity()));
//
//        assertFalse(pantry.getAllFreshFoods().isEmpty());
    }
}
