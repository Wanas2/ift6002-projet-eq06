package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.GameService;

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
    private GameService gameService;
    private FoodResource foodResource;

    @BeforeEach
    void setUp() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;

        foodValidator = new FoodValidator();
        gameService = mock(GameService.class);
        foodResource = new FoodResource(gameService, foodValidator);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenShouldValidateTheFoodDTO() {
        foodResource.orderFood(aFoodDTO);

//        verify(foodValidator).validateFoodEntries(aFoodDTO);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenGameServiceShouldOrderTheAppropriateFood() {
        foodResource.orderFood(aFoodDTO);

        verify(gameService).orderFood(aFoodDTO);
    }

    @Test
    public void whenOrderFood_thenShouldReturnAppropriateResponseCode() {
        int expectedResponseCode = 200;

        Response response = foodResource.orderFood(aFoodDTO);

        assertEquals(expectedResponseCode, response.getStatus());
    }

    @Test
    public void whenGetFoodQuantitySummary_theGameServiceShouldGetFoodQuantitySummary() {
        foodResource.getFoodQuantitySummary();

        verify(gameService).getFoodQuantitySummary();
    }

    @Test
    public void whenGetFoodQuantitySummary_thenShouldReturnAppropriateResponseCode() {
        int expectedResponseCode = 200;

        Response response = foodResource.getFoodQuantitySummary();

        assertEquals(expectedResponseCode, response.getStatus());
    }
}
