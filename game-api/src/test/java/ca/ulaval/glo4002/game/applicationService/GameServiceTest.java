package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.parkResources.Food;
import ca.ulaval.glo4002.game.domain.parkResources.FoodsFactory;
import ca.ulaval.glo4002.game.domain.parkResources.Pantry;
import ca.ulaval.glo4002.game.domain.parkResources.PantryRepository;
import ca.ulaval.glo4002.game.interfaces.rest.game.FoodsDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.will;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private final int QUANTITY_OF_BURGER_FOR_A_TURN = 100;
    private final int QUANTITY_OF_SALAD_FOR_A_TURN = 250;
    private final int QUANTITY_OF_WATER_FOR_A_TURN = 10;

    private Food aFoodItem;
    private Food anotherFoodItem;
    List<Food> foods;
    private Game game;
    private Pantry pantry;
    private TurnAssembler turnAssembler;
    private ResourceAssembler resourceAssembler;
    private FoodsFactory foodsFactory;
    private PantryRepository pantryRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        aFoodItem = mock(Food.class);
        anotherFoodItem = mock(Food.class);
        foods = new ArrayList<>();
        game = mock(Game.class);
        pantry = mock(Pantry.class);
        turnAssembler = new TurnAssembler();
        resourceAssembler = new ResourceAssembler();
        foodsFactory = mock(FoodsFactory.class);
        pantryRepository = mock(PantryRepository.class);
        gameService = new GameService(turnAssembler, foodsFactory, pantryRepository, game, pantry);
    }

    @Test
    public void givenFoods_whenPlayTurn_thenGameIsPlayed() {
        willReturn(foods).given(foodsFactory)
                .create(QUANTITY_OF_BURGER_FOR_A_TURN, QUANTITY_OF_SALAD_FOR_A_TURN, QUANTITY_OF_WATER_FOR_A_TURN);

        gameService.playTurn();

        verify(game).playTurn(foods);
    }

    @Test
    public void givenATurnNumber_whenPlayTurn_thenTheAppropriateTurnNumberDTOIsReturned() {
        int aTurnNumber = 143;
        willReturn(aTurnNumber).given(game).playTurn(foods);

        gameService.playTurn();

        TurnNumberDTO turnNumberDTO = turnAssembler.assembleTurnNumber(aTurnNumber);
        assertEquals(aTurnNumber, turnNumberDTO.turnNumber);
    }

    @Test
    public void givenFoodsDTO_whenPlayTurn_thenTheFoodIsAddedToThePantry() {
        FoodsDTO foodsDTO =
                new FoodsDTO(QUANTITY_OF_BURGER_FOR_A_TURN, QUANTITY_OF_SALAD_FOR_A_TURN, QUANTITY_OF_WATER_FOR_A_TURN);

        gameService.playTurn();

//        pantry.addFood(foodsDTO);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(game).reset();
    }
}
