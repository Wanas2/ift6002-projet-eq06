package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.FoodAssembler;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class FoodAssemblerTest {

    private final int A_QUANTITY_OF_BURGER_ORDERED = 3;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private FoodAssembler foodAssembler;

    @BeforeEach
    void setUp() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;

        foodAssembler = new FoodAssembler();
    }


    @Test
    public void givenAFood_whenCreateDTO_thenShouldBeCorrectlyMapped() {
        Map<FoodType, Integer> aFood = new HashMap<>();

        aFood.put(FoodType.BURGER, 10);
        aFood.put(FoodType.SALAD, 50);
        aFood.put(FoodType.WATER, 250);

        FoodDTO foodDTO = foodAssembler.createDTO(aFood);
        assertEquals(10, foodDTO.qtyBurger);
        assertEquals(50, foodDTO.qtySalad);
        assertEquals(250, foodDTO.qtyWater);
    }
}
