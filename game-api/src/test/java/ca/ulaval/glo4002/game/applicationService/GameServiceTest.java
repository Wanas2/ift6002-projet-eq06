package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.exceptions.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;

import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.food.*;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;
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

    private DinosaurDTO aDinosaurDTO;
    private Species A_SPECIES = Species.Diplodocus;
    private int SOMME_WEIGHT = 134;
    private String A_NAME = "ehwr";
    private Gender A_GENDER = Gender.M;
    private FoodConsumptionStrategy aFoodConsumptionStrategy;

    private Game game;
    private FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private Dinosaur aDinosaur;
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
        initializeAFoodDTO();
        initializeADinosaurDTO();
        game = mock(Game.class);
        foodQuantitySummaryCalculator = mock(FoodQuantitySummaryCalculator.class);
        herd = mock(Herd.class);
        pantry = mock(Pantry.class);
        aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        aDinosaur = new Dinosaur(A_SPECIES, SOMME_WEIGHT, A_NAME, A_GENDER, aFoodConsumptionStrategy);
        turnAssembler = new TurnAssembler();
        dinosaurAssembler = mock(DinosaurAssembler.class);
        foodAssembler = mock(FoodAssembler.class);
        foodSummaryAssembler = mock(FoodSummaryAssembler.class);
        pantryRepository = mock(PantryRepository.class);
        herdRepository = mock(HerdRepository.class);
        dinosaurFactory = mock(DinosaurFactory.class);

        gameService = new GameService(game, herd, pantry, turnAssembler, dinosaurAssembler, foodAssembler,
                foodSummaryAssembler, pantryRepository, foodQuantitySummaryCalculator, dinosaurFactory, herdRepository);
    }

    @Test
    public void givenAFoodDTO_whenAddFood_thenShouldCreateTheAppropriateFood() {
        gameService.addFood(aFoodDTO);

        verify(foodAssembler).create(aFoodDTO);
    }

    @Test
    public void givenCreatedFood_whenAddFood_thenShouldAddTheAppropriateFood() {
        initializeSomeFood();
        when(foodAssembler.create(aFoodDTO)).thenReturn(someFoodCreated);

        gameService.addFood(aFoodDTO);

        verify(game).addFood(someFoodCreated);
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaur_thenShouldVerifyIfDinosaureWithSameNameExists() {
        gameService.addDinosaur(aDinosaurDTO);

        verify(herd).hasDinoosaurWithName(aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTOWithDuplicateName_whenAddDinosaur_thenShouldThrowException () {
        when(herd.hasDinoosaurWithName(aDinosaurDTO.name)).thenReturn(true);

        assertThrows(DuplicateNameException.class, () -> gameService.addDinosaur(aDinosaurDTO));
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenShouldCreateAppropriateDinosaur() {
        when(herd.hasDinoosaurWithName(aDinosaurDTO.name)).thenReturn(false);

        gameService.addDinosaur(aDinosaurDTO);

        verify(dinosaurFactory).create(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.name,
                aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenGameShouldAddTheDinosaur() {
        when(herd.hasDinoosaurWithName(aDinosaurDTO.name)).thenReturn(false);
        when(dinosaurFactory.create(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.name,
                aDinosaurDTO.name)).thenReturn(aDinosaur);

        gameService.addDinosaur(aDinosaurDTO);

        verify(game).addDinosaur(aDinosaur);
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
        when(game.playTurn()).thenReturn(aTurnNumber);

        gameService.playTurn();

        TurnNumberDTO turnNumberDTO = turnAssembler.assembleTurnNumber(aTurnNumber);
        assertEquals(aTurnNumber, turnNumberDTO.turnNumber);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenSummaryShouldBeCalculated() {
        gameService.getFoodQuantitySummary();

        verify(foodQuantitySummaryCalculator).computeSummaries(pantry);
    }

    @Test
    public void whenGetFoodQuantitySummary_thenAssemblerShouldCreateTheDTOWithAppropriateSummary() {
        initializeFoodSummaryExample();
        when(foodQuantitySummaryCalculator.computeSummaries(pantry)).thenReturn(foodSummaryExample);

        gameService.getFoodQuantitySummary();

        verify(foodSummaryAssembler).createDTO(foodSummaryExample, foodAssembler);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(game).reset();
    }

    private void initializeADinosaurDTO() {
        String aName = "igiyv";
        int someWeight = 134;
        String aGender = "m";
        String aSpecies = "Diplodocus";

        aDinosaurDTO = new DinosaurDTO(aName, someWeight, aGender, aSpecies);
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
