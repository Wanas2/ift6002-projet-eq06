package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;

class PantryServiceTest {

    private final int A_QUANTITY_OF_BURGER_ORDERED = 3;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private Map<FoodType, Food> someFood;
    private FoodAssembler foodAssembler;
    private Pantry pantry;
    private PantryService pantryService;

    @BeforeEach
    void setUp() {
        foodAssembler = mock(FoodAssembler.class);
        pantry = mock(Pantry.class);
        pantryService = new PantryService(foodAssembler, pantry);
    }

    @Disabled
    @Test
    public void whenOrderFood_thenShouldCreateFood() {
        pantryService.orderFood();

//        verify(foodAssembler).create(aFoodDTO); // Todo J'arr
    }

    @Disabled
    @Test
    public void givenCreatedFood_whenOrderFood_thenPantryShouldOrderFood() {
//        willReturn(someFood).given(foodAssembler).create(aFoodDTO); // Todo J'arr

        pantryService.orderFood();

        verify(pantry).orderFood();
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        someFood = new HashMap<>();
        someFood.put(FoodType.BURGER, aFoodItem1);
        someFood.put(FoodType.SALAD, aFoodItem2);
        someFood.put(FoodType.WATER, aFoodItem3);
    }
}