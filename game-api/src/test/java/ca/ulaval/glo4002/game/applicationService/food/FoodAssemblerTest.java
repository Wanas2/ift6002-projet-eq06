package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class FoodAssemblerTest {

    private final int A_QUANTITY_OF_BURGER_ORDERED = 3;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private Map<FoodType, Food> aFoodDTO;

    private FoodAssembler foodAssembler;

    @BeforeEach
    void setUp() {
        foodAssembler = new FoodAssembler();
    }

    @Test
    public void givenFoodDTO_whenCreate_thenShouldCreateFood() {
        foodAssembler.create(aFoodDTO);
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        aFoodDTO = new HashMap<>();
        aFoodDTO.put(FoodType.BURGER, aFoodItem1);
        aFoodDTO.put(FoodType.SALAD, aFoodItem2);
        aFoodDTO.put(FoodType.WATER, aFoodItem3);
    }
}
