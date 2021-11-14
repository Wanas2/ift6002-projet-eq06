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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.verify;
import static org.mockito.Mockito.when;

class FoodResourceTest {

    private final static int STATUS_200_OK = 200;
    private final static int A_QUANTITY_OF_BURGER = 3;
    private final static int A_QUANTITY_OF_SALAD = 2;
    private final static int A_QUANTITY_OF_WATER_IN_LITERS = 10;

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
        aFoodDTO.qtyBurger = A_QUANTITY_OF_BURGER;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS;

        resourceService = mock(ResourceService.class);
        foodValidator = new FoodValidator();
        foodAssembler = new FoodAssembler();
        foodSummaryAssembler = new FoodSummaryAssembler();
        foodResource = new FoodResource(resourceService, foodValidator, foodAssembler, foodSummaryAssembler);
    }

    /*@Test
    public void givenAFoodDTO_whenAddFood_thenTheFoodCorrespondingToTheDTOShouldBeAdded() {
        initializeSomeFood();

        foodResource.addFood(aFoodDTO);

        verify(resourceService).addFood(argThat(this::isTheSameAsSomeFood));
    }*/

    @Test
    public void givenAFoodDTO_whenAddFood_thenResponseStatusShouldBe200() {
        Response response = foodResource.addFood(aFoodDTO);

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void whenGetFoodQuantitySummary_thenGameServiceShouldGetFoodQuantitySummary() {
        initializeFoodSummaryExample();
        when(resourceService.getFoodQuantitySummary()).thenReturn(foodSummaryExample);

        Response response = foodResource.getFoodQuantitySummary();
        FoodSummaryDTO expectedFoodSummaryDTO = (FoodSummaryDTO) response.getEntity();

        assertTrue(isTheSameFoodDTO(expectedFoodSummaryDTO.fresh));
        assertTrue(isTheSameFoodDTO(expectedFoodSummaryDTO.consumed));
        assertTrue(isTheSameFoodDTO(expectedFoodSummaryDTO.expired));
    }

    @Test
    public void whenGetFoodQuantitySummary_thenResponseStatusShouldBe200() {
        initializeFoodSummaryExample();
        when(resourceService.getFoodQuantitySummary()).thenReturn(foodSummaryExample);

        Response response = foodResource.getFoodQuantitySummary();

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_BURGER);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS);
        someFood = new ArrayList<>();
        someFood.add(aFoodItem1);
        someFood.add(aFoodItem2);
        someFood.add(aFoodItem3);
    }

    private void initializeFoodSummaryExample() {
        Map<FoodType, Integer> expiredFoodSummary = new HashMap<>(){{
            put(FoodType.BURGER, A_QUANTITY_OF_BURGER);
            put(FoodType.SALAD, A_QUANTITY_OF_SALAD);
            put(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS);
        }};
        Map<FoodType, Integer> consumedFoodSummary = new HashMap<>(){{
            put(FoodType.BURGER, A_QUANTITY_OF_BURGER);
            put(FoodType.SALAD, A_QUANTITY_OF_SALAD);
            put(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS);
        }};
        Map<FoodType, Integer> freshFoodSummary = new HashMap<>(){{
            put(FoodType.BURGER, A_QUANTITY_OF_BURGER);
            put(FoodType.SALAD, A_QUANTITY_OF_SALAD);
            put(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS);
        }};

        foodSummaryExample = new HashMap<>();
        foodSummaryExample.put("fresh", freshFoodSummary);
        foodSummaryExample.put("expired", expiredFoodSummary);
        foodSummaryExample.put("consumed", consumedFoodSummary);
    }

    /*private boolean isTheSameAsSomeFood(Map<FoodType, Food> matcher){
        return matcher.get(FoodType.SALAD).quantity() == someFood.get(FoodType.SALAD).quantity() &&
                matcher.get(FoodType.BURGER).quantity() == someFood.get(FoodType.BURGER).quantity() &&
                matcher.get(FoodType.WATER).quantity() == someFood.get(FoodType.WATER).quantity();
    }*/

    private boolean isTheSameFoodDTO(FoodDTO foodDTO) {
        return foodDTO.qtySalad == aFoodDTO.qtySalad && foodDTO.qtyBurger == aFoodDTO.qtyBurger
                && foodDTO.qtyWater == aFoodDTO.qtyWater;
    }
}
