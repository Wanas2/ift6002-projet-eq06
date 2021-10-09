package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;

import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.*;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
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
    private Map<String, Map<FoodType, Integer>> foodSummaryExample;

    private Game game;
    private FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private Herd herd;
    private Pantry pantry;
    private TurnAssembler turnAssembler;
    private DinosaurAssembler dinosaurAssembler;
    private FoodAssembler foodAssembler;
    private FoodSummaryAssembler foodSummaryAssembler;
    private PantryRepository pantryRepository;
    private HerdRepository herdRepository;
    private GameService gameService;
    private DinosaurFactory dinosaurFactory;

    @BeforeEach
    void setUp() {

        initiateAFoodDTO();
        game = mock(Game.class);
        foodQuantitySummaryCalculator = mock(FoodQuantitySummaryCalculator.class);
        pantry = mock(Pantry.class);
        foodQuantitySummaryCalculator = mock(FoodQuantitySummaryCalculator.class);
        turnAssembler = new TurnAssembler();
        dinosaurAssembler = mock(DinosaurAssembler.class);
        foodAssembler = mock(FoodAssembler.class);
        foodSummaryAssembler = mock(FoodSummaryAssembler.class);
        pantryRepository = mock(PantryRepository.class);
        herdRepository = mock(HerdRepository.class);

        gameService = new GameService(game, herd, pantry, turnAssembler, dinosaurAssembler, foodAssembler, foodSummaryAssembler,
            pantryRepository, foodQuantitySummaryCalculator, dinosaurFactory, herdRepository);
    }

    @Test
    public void givenAFoodDTO_whenOrderFood_thenShouldCreateTheAppropriateFood() {
        gameService.addFood(aFoodDTO);

        verify(foodAssembler).create(aFoodDTO);
    }

    @Test
    public void givenCreatedFood_whenOrderFood_thenShouldOrderTheAppropriateFood() {
        initializeSomeFood();
        willReturn(someFoodCreated).given(foodAssembler).create(aFoodDTO);

        gameService.addFood(aFoodDTO);

        verify(game).addFood(someFoodCreated);
    }

    @Test
    public void givenFoods_whenPlayTurn_thenGameIsPlayed() {
        gameService.playTurn();

        verify(game).playTurn();
    }

    @Test
    public void whenPlayTurn_thenPantryIsUpdated() {
        gameService.playTurn();

        verify(pantryRepository).update(pantry);
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
    public void whenGetFoodQuantitySummary_PantryRepositoryShouldGetPantry() {
        gameService.getFoodQuantitySummary();

        verify(pantryRepository).getPantry();
    }

    @Test
    public void whenGetFoodQuantitySummary_thenSummaryShouldBeCalculated() {
        willReturn(pantry).given(pantryRepository).getPantry();

        gameService.getFoodQuantitySummary();

        verify(foodQuantitySummaryCalculator).computeSummaries(pantry);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenAssemblerShouldCreateTheDTOWithAppropriateSummary() {
        initiateFoodSummaryExample();
        willReturn(pantry).given(pantryRepository).getPantry();
        willReturn(foodSummaryExample).given(foodQuantitySummaryCalculator).computeSummaries(pantry);

        gameService.getFoodQuantitySummary();

        verify(foodSummaryAssembler).createDTO(foodSummaryExample, foodAssembler);
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

    private void initiateFoodSummaryExample() {
        Map<FoodType, Integer> expiredFoodSummary = new HashMap<>();
        Map<FoodType, Integer> consumedFoodSummary = new HashMap<>();
        Map<FoodType, Integer> freshFoodSummary = new HashMap<>();

        foodSummaryExample = new HashMap<>();
        foodSummaryExample.put("fresh", freshFoodSummary);
        foodSummaryExample.put("expired", expiredFoodSummary);
        foodSummaryExample.put("consumed", consumedFoodSummary);
    }
}
