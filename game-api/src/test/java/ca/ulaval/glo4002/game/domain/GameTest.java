package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;

class GameTest {

    private Playable turn;
    private Game game;

    @BeforeEach
    void setUp() {
        turn = mock(Turn.class);
        game = new Game(turn);
    }


    @Test
    public void whenPlayTurn_thenTurnIsPlayed() {
        game.playTurn();

        verify(turn).play();
    }

    @Test
    public void whenPlayTurn_thenShouldReturnTheTurnNumber() {
        int expectedTurnNumber = 12;
        willReturn(expectedTurnNumber).given(turn).play();

        int turnNumber = game.playTurn();

        assertSame(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(turn).reset();
    }
}
