package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.food.ResourceService;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodHistory;
import ca.ulaval.glo4002.game.domain.food.FoodState;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.when;

class FoodResourceTest {

    private final static int STATUS_200_OK = 200;
    private final static int A_QUANTITY_OF_BURGER = 3;
    private final static int A_QUANTITY_OF_SALAD = 2;
    private final static int A_QUANTITY_OF_WATER_IN_LITERS = 10;

    private FoodDTO aFoodDTO;
    private FoodHistory foodHistory;
    private ResourceService resourceService;
    private FoodValidator foodValidator;
    private FoodAssembler foodAssembler;
    private FoodSummaryAssembler foodSummaryAssembler;
    private FoodResource foodResource;

    @BeforeEach
    void setUp() {
        foodHistory = mock(FoodHistory.class);
        resourceService = mock(ResourceService.class);
        foodValidator = mock(FoodValidator.class);
        foodAssembler = mock(FoodAssembler.class);
        foodSummaryAssembler = mock(FoodSummaryAssembler.class);
        foodResource = new FoodResource(resourceService, foodValidator, foodAssembler, foodSummaryAssembler);
    }

    @Test
    public void givenAFoodDTO_whenAddFood_thenResponseStatusShouldBe200() {
        aFoodDTO = new FoodDTO(A_QUANTITY_OF_BURGER, A_QUANTITY_OF_SALAD, A_QUANTITY_OF_WATER_IN_LITERS);

        Response response = foodResource.addFood(aFoodDTO);

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void whenGetFoodQuantitySummary_thenShouldGetFoodQuantitySummary(){
        foodResource.getFoodQuantitySummary();

        verify(resourceService).getFoodQuantitySummary();
    }

    @Test
    public void whenGetFoodQuantitySummary_thenShouldPrepareDTO(){
        when(resourceService.getFoodQuantitySummary()).thenReturn(foodHistory);
        foodResource.getFoodQuantitySummary();

        verify(foodSummaryAssembler).toDTO(foodHistory);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenResponseStatusShouldBe200(){
        Response response = foodResource.getFoodQuantitySummary();

        assertEquals(STATUS_200_OK, response.getStatus());
    }
}
