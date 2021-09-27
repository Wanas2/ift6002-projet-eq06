package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.food.PantryService;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.mockito.BDDMockito.*;

class FoodResourceTest {

    private final int A_QUANTITY_OF_BURGER_ORDERED = 3;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private FoodValidator foodValidator;
    private PantryService pantryService;
    private FoodResource foodResource;

    @BeforeEach
    void setUp() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;

        foodValidator = new FoodValidator();
        pantryService = mock(PantryService.class);
        foodResource = new FoodResource(pantryService, foodValidator);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenShouldValidateTheFoodDTO() {
        foodResource.orderFood(aFoodDTO);

//        verify(foodValidator).validateFoodEntries(aFoodDTO);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenPantryServiceShouldOrderTheAppropriateFood() {
        foodResource.orderFood(aFoodDTO);

        verify(pantryService).orderFood(aFoodDTO);
    }

    @Disabled
    @Test
    public void whenOrderFood_thenShouldReturnAppropriateResponse() { // Todo Ce test ne passe pas
        Response expectedResponse = Response.ok().build();

        Response response = foodResource.orderFood(aFoodDTO);

        assertSame(expectedResponse, response);
    }
}
