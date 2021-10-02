package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Gender;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private TurnAssembler turnAssembler;
    private DinosaurAssembler dinosaurAssembler;
    private Game game;
    GameService gameService;

    @BeforeEach
    void setUp() {
        turnAssembler = mock(TurnAssembler.class);
        dinosaurAssembler = mock(DinosaurAssembler.class);
        game = mock(Game.class);
        gameService = new GameService(turnAssembler, dinosaurAssembler, game);
    }

    @Test
    public void whenPlayTurn_thenGameIsPlayed() {
        gameService.playTurn();

        verify(game).playTurn();
    }

    @Test
    public void givenATurnNumber_whenPlayTurn_thenTheTurnNumberDTOIsAssembled() {
        int aTurnNumber = 138;
        willReturn(aTurnNumber).given(game).playTurn();

        gameService.playTurn();

        verify(turnAssembler).assembleTurnNumber(aTurnNumber);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(game).reset();
    }

}
