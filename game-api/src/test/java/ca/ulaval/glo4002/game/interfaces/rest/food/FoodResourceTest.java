package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.applicationService.food.FoodAssembler;
import ca.ulaval.glo4002.game.applicationService.food.FoodSummaryAssembler;
import ca.ulaval.glo4002.game.applicationService.food.ResourceService;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.when;

class FoodResourceTest {

    private final static int STATUS_200_OK = 200;
    private final static int A_QUANTITY_OF_BURGER_ORDERED = 3;
    private final static int A_QUANTITY_OF_SALAD_ORDERED = 2;
    private final static int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private List<Food> someFood;
    private FoodDTO aFoodDTO;
    private Map<String, Map<FoodType, Integer>> foodSummaryExample;
    private ResourceService resourceService;
    private FoodValidator foodValidator;
    private FoodAssembler foodAssembler;
    private FoodSummaryAssembler foodSummaryAssembler;
    private FoodResource foodResource;

    @BeforeEach
    void setUp() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;

        resourceService = mock(ResourceService.class);
        foodValidator = new FoodValidator();
        foodAssembler = mock(FoodAssembler.class);
        foodSummaryAssembler = mock(FoodSummaryAssembler.class);
        foodResource = new FoodResource(resourceService, foodValidator, foodAssembler, foodSummaryAssembler);
    }

    @Test
    public void givenAFoodDTO_whenAddFood_thenTheFoodShouldBeCreated() {
        foodResource.addFood(aFoodDTO);

        verify(foodAssembler).fromDTO(aFoodDTO);
    }

    @Test
    public void givenAFoodDTO_whenAddFood_thenTheFoodCorrespondingToTheDTOShouldBeAdded() {
        initializeSomeFood();
        when(foodAssembler.fromDTO(aFoodDTO)).thenReturn(someFood);

        foodResource.addFood(aFoodDTO);

        verify(resourceService).addFood(someFood);
    }

    @Test
    public void givenAFoodDTO_whenAddFood_thenResponseStatusShouldBe200() {
        Response response = foodResource.addFood(aFoodDTO);

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void whenGetFoodQuantitySummary_theGameServiceShouldGetFoodQuantitySummary() {
        foodResource.getFoodQuantitySummary();

        verify(resourceService).getFoodQuantitySummary();
    }

    @Test
    public void givenAFoodSummary_whenGetFoodQuantitySummary_thenTheDTOCorrespondingToTheSummaryShouldBeCreated() {
        initializeFoodSummaryExample();
        when(resourceService.getFoodQuantitySummary()).thenReturn(foodSummaryExample);

        foodResource.getFoodQuantitySummary();

        verify(foodSummaryAssembler).toDTO(foodSummaryExample, foodAssembler);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenResponseStatusShouldBe200() {
        Response response = foodResource.getFoodQuantitySummary();

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        someFood = new ArrayList<>();
        someFood.add(aFoodItem1);
        someFood.add(aFoodItem2);
        someFood.add(aFoodItem3);
    }

    private void initializeFoodSummaryExample() {
        Map<FoodType, Integer> expiredFoodSummary = new HashMap<>();
        Map<FoodType, Integer> consumedFoodSummary = new HashMap<>();
        Map<FoodType, Integer> freshFoodSummary = new HashMap<>();

        foodSummaryExample = new HashMap<>();
        foodSummaryExample.put("fresh", freshFoodSummary);
        foodSummaryExample.put("expired", expiredFoodSummary);
        foodSummaryExample.put("consumed", consumedFoodSummary);
    }
}
