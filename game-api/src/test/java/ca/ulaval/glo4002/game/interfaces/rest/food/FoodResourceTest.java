package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.food.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.verify;

class FoodResourceTest {

    private final static int A_QUANTITY_OF_BURGER_ORDERED = 3;
    private final static int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final static int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private FoodValidator foodValidator;
    private ResourceService resourceService;
    private FoodResource foodResource;

    @BeforeEach
    void setUp() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;

        foodValidator = new FoodValidator();
        resourceService = mock(ResourceService.class);
        foodResource = new FoodResource(resourceService, foodValidator);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenGameServiceShouldOrderTheAppropriateFood() {
        foodResource.addFood(aFoodDTO);

        verify(resourceService).addFood(aFoodDTO);
    }

    @Test
    public void whenOrderFood_thenShouldReturnAppropriateResponseCode() {
        int expectedResponseCode = 200;

        Response response = foodResource.addFood(aFoodDTO);

        assertEquals(expectedResponseCode, response.getStatus());
    }

    @Test
    public void whenGetFoodQuantitySummary_theGameServiceShouldGetFoodQuantitySummary() {
        foodResource.getFoodQuantitySummary();

        verify(resourceService).getFoodQuantitySummary();
    }

    @Test
    public void whenGetFoodQuantitySummary_thenShouldReturnAppropriateResponseCode() {
        int expectedResponseCode = 200;

        Response response = foodResource.getFoodQuantitySummary();

        assertEquals(expectedResponseCode, response.getStatus());
    }
}
