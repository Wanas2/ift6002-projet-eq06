package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.food.ResourceService;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodQuantitySummaryCalculator;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
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
    private Map<FoodType, Food> someFoodCreated;
    private FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private Pantry pantry;
    private Game game;
    private ResourceService resourceService;

    @BeforeEach
    void setUp() {
        foodQuantitySummaryCalculator = mock(FoodQuantitySummaryCalculator.class);
        pantry = mock(Pantry.class);
        game = mock(Game.class);
        resourceService = new ResourceService(foodQuantitySummaryCalculator, pantry, game);
    }

    @Test
    public void givenCreatedFood_whenAddFood_thenShouldAddTheAppropriateFood() {
        initializeSomeFood();

        resourceService.addFood(someFoodCreated);

        verify(game).addFood(someFoodCreated);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenSummaryShouldBeCalculated() {
        resourceService.getFoodQuantitySummary();

        verify(foodQuantitySummaryCalculator).computeSummaries(pantry);
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
}
