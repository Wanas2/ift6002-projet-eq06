package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.Food.FoodAssembler;
import ca.ulaval.glo4002.game.applicationService.Food.FoodSummaryAssembler;
import ca.ulaval.glo4002.game.applicationService.Food.ResourceService;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodQuantitySummaryCalculator;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class ResourceServiceTest {

    private final int A_QUANTITY_OF_BURGER_ORDERED = 100;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 250;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private FoodDTO aFoodDTO;
    private Map<FoodType, Food> someFoodCreated;
    private Map<String, Map<FoodType, Integer>> foodSummaryExample;
    private FoodAssembler foodAssembler;
    private FoodSummaryAssembler foodSummaryAssembler;
    private FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private Pantry pantry;
    private Game game;
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        initializeAFoodDTO();
        foodAssembler = mock(FoodAssembler.class);
        foodSummaryAssembler = mock(FoodSummaryAssembler.class);
        foodQuantitySummaryCalculator = mock(FoodQuantitySummaryCalculator.class);
        pantry = mock(Pantry.class);
        game = mock(Game.class);
        resourceService = new ResourceService(foodQuantitySummaryCalculator, pantry, game, foodAssembler,
                foodSummaryAssembler);
    }

    @Test
    public void givenAFoodDTO_whenAddFood_thenShouldCreateTheAppropriateFood() {
        resourceService.addFood(aFoodDTO);

        verify(foodAssembler).create(aFoodDTO);
    }

    @Test
    public void givenCreatedFood_whenAddFood_thenShouldAddTheAppropriateFood() {
        initializeSomeFood();
        when(foodAssembler.create(aFoodDTO)).thenReturn(someFoodCreated);

        resourceService.addFood(aFoodDTO);

        verify(game).addFood(someFoodCreated);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenSummaryShouldBeCalculated() {
        resourceService.getFoodQuantitySummary();

        verify(foodQuantitySummaryCalculator).computeSummaries(pantry);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenAssemblerShouldCreateTheDTOWithAppropriateSummary() {
        initializeFoodSummaryExample();
        when(foodQuantitySummaryCalculator.computeSummaries(pantry)).thenReturn(foodSummaryExample);

        resourceService.getFoodQuantitySummary();

        verify(foodSummaryAssembler).createDTO(foodSummaryExample, foodAssembler);
    }

    private void initializeAFoodDTO() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger =  A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad =  A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater =  A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;
    }

    private void initializeSomeFood() {
        aFoodItem1 = new Food(FoodType.BURGER, A_QUANTITY_OF_BURGER_ORDERED);
        aFoodItem2 = new Food(FoodType.SALAD, A_QUANTITY_OF_SALAD_ORDERED);
        aFoodItem3 = new Food(FoodType.WATER, A_QUANTITY_OF_WATER_IN_LITERS_ORDERED);
        someFoodCreated = new HashMap<>();
        someFoodCreated.put(FoodType.BURGER, aFoodItem1);
        someFoodCreated.put(FoodType.SALAD, aFoodItem2);
        someFoodCreated.put(FoodType.WATER, aFoodItem3);
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
