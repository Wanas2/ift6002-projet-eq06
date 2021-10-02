package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {


    private final int A_QUANTITY_OF_BURGER_ORDERED = 100;
    private final int A_QUANTITY_OF_SALAD_ORDERED = 250;
    private final int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private Map<FoodType, Food> someFoodCreated;

    private Game game;
    private Pantry pantry;
    private TurnAssembler turnAssembler;
    private FoodAssembler foodAssembler;
    private FoodSummaryAssembler foodSummaryAssembler;
    private GameService gameService;

    @BeforeEach
    void setUp() {

        initiateAFoodDTO();
        game = mock(Game.class);
        pantry = mock(Pantry.class);
        turnAssembler = new TurnAssembler();
        foodAssembler = mock(FoodAssembler.class);
        foodSummaryAssembler = mock(FoodSummaryAssembler.class);
        gameService = new GameService(game, pantry, turnAssembler, foodAssembler, foodSummaryAssembler);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenShouldCreateTheAppropriateFood() {
        gameService.orderFood(aFoodDTO);

        verify(foodAssembler).create(aFoodDTO);
    }

    @Test
    public void givenCreatedFood_whenOrderFood_thenShouldOrderTheAppropriateFood() {
        initializeSomeFood();
        willReturn(someFoodCreated).given(foodAssembler).create(aFoodDTO);

        gameService.orderFood(aFoodDTO);

        verify(game).addFood(someFoodCreated);
    }

    @Disabled
    @Test
    public void givenFoods_whenPlayTurn_thenGameIsPlayed() {
        gameService.playTurn();

        verify(game).playTurn();
    }

    @Test
    public void givenATurnNumber_whenPlayTurn_thenTheAppropriateTurnNumberDTOIsReturned() {
        int aTurnNumber = 143;
        willReturn(aTurnNumber).given(game).playTurn();

        gameService.playTurn();

        TurnNumberDTO turnNumberDTO = turnAssembler.assembleTurnNumber(aTurnNumber);
        assertEquals(aTurnNumber, turnNumberDTO.turnNumber);
    }

    @Test
    public void whenGetFoodQuantitySummary_PantryShouldGetFoodQuantitySummary() {
        gameService.getFoodQuantitySummary();

        verify(pantry).getFoodQuantitySummary();
    }

    @Disabled
    @Test
    public void whenGetFoodQuantitySummary_thenAssemblerShouldCreateTheDTOWithAppropriateSummary() { // Todo Finish this test
//        Map<String, Map<FoodType, Integer>> foodQuantitySummary = gameService.getFoodQuantitySummary();

//        verify(foodSummaryAssembler).createDTO();
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(game).reset();
    }


    private void initiateAFoodDTO() {
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
}
