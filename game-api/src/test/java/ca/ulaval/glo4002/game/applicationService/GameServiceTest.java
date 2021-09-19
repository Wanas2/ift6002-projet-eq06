package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.parkResources.Food;
import ca.ulaval.glo4002.game.domain.parkResources.FoodsFactory;
import ca.ulaval.glo4002.game.domain.parkResources.Pantry;
import ca.ulaval.glo4002.game.domain.parkResources.PantryRepository;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private Game game;
    private Pantry pantry;
    private TurnAssembler turnAssembler;
    private ResourceAssembler resourceAssembler;
    private FoodsFactory foodsFactory;
    private PantryRepository pantryRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        turnAssembler = new TurnAssembler();
        resourceAssembler = new ResourceAssembler();
        game = mock(Game.class);
        pantry = mock(Pantry.class);
        foodsFactory = mock(FoodsFactory.class);
        pantryRepository = mock(PantryRepository.class);
        gameService = new GameService(turnAssembler, foodsFactory, pantryRepository, game, pantry);
    }

    @Test
    public void whenPlayTurn_thenGameIsPlayed() {
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
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(game).reset();
    }
}
