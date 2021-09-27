package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
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
    private Map<FoodType, Food> someFoodCreate;

    private FoodDTO aFoodDTO;
    private FoodAssembler foodAssembler;
    private Pantry pantry;
    private PantryService pantryService;

    @BeforeEach
    void setUp() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger =  A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad =  A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater =  A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;

        initializeSomeFood();

        foodAssembler = mock(FoodAssembler.class);
        pantry = mock(Pantry.class);
        pantryService = new PantryService(foodAssembler, pantry);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenShouldCreateTheAppropriateFood() {
        pantryService.orderFood(aFoodDTO);

        verify(foodAssembler).create(aFoodDTO);
    }

    @Test
    public void givenCreatedFood_whenOrderFood_thenPantryShouldOrderTheAppropriateFood() {
        willReturn(someFoodCreate).given(foodAssembler).create(aFoodDTO);

        pantryService.orderFood(aFoodDTO);

        verify(pantry).orderFood(someFoodCreate);
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        someFoodCreate = new HashMap<>();
        someFoodCreate.put(FoodType.BURGER, aFoodItem1);
        someFoodCreate.put(FoodType.SALAD, aFoodItem2);
        someFoodCreate.put(FoodType.WATER, aFoodItem3);
    }
}
