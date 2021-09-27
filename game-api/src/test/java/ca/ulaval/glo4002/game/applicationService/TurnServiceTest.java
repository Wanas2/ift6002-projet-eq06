package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.turn.TurnService;
import ca.ulaval.glo4002.game.applicationService.turn.TurnAssembler;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.game.FoodsDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TurnServiceTest {

    private final int QUANTITY_OF_BURGER_FOR_A_TURN = 100;
    private final int QUANTITY_OF_SALAD_FOR_A_TURN = 250;
    private final int QUANTITY_OF_WATER_FOR_A_TURN = 10;

    private Food aFoodItem;
    private Food anotherFoodItem;
    Map<FoodType, Food> foods;
    private Game game;
    private Pantry pantry;
    private TurnAssembler turnAssembler;
    private TurnService turnService;

    @BeforeEach
    void setUp() {
        aFoodItem = mock(Food.class);
        anotherFoodItem = mock(Food.class);
        foods = new HashMap<>();
        foods.put(FoodType.BURGER, aFoodItem);
        foods.put(FoodType.SALAD, anotherFoodItem);
        game = mock(Game.class);
        pantry = mock(Pantry.class);
        turnAssembler = new TurnAssembler();
        turnService = new TurnService(turnAssembler, game, pantry);
    }

    @Disabled
    @Test
    public void givenFoods_whenPlayTurn_thenGameIsPlayed() {
        turnService.playTurn();

        verify(game).playTurn(foods);
    }

    @Test
    public void givenATurnNumber_whenPlayTurn_thenTheAppropriateTurnNumberDTOIsReturned() {
        int aTurnNumber = 143;
        willReturn(aTurnNumber).given(game).playTurn(foods);

        turnService.playTurn();

        TurnNumberDTO turnNumberDTO = turnAssembler.assembleTurnNumber(aTurnNumber);
        assertEquals(aTurnNumber, turnNumberDTO.turnNumber);
    }

    @Test
    public void givenFoodsDTO_whenPlayTurn_thenTheFoodIsAddedToThePantry() {
        FoodsDTO foodsDTO =
                new FoodsDTO(QUANTITY_OF_BURGER_FOR_A_TURN, QUANTITY_OF_SALAD_FOR_A_TURN, QUANTITY_OF_WATER_FOR_A_TURN);

        turnService.playTurn();

//        pantry.addFood(foodsDTO);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(game).reset();
    }
}
