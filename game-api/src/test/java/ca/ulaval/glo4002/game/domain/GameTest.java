package ca.ulaval.glo4002.game.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private TurnFactory turnFactory;
    private Game game;

    @BeforeEach
    void setUp() {
        turnFactory = new TurnFactory();
        game = new Game(turnFactory);
    }

    @Test
    public void whenPlayTurn_thenTheTurnNumberIsReturned() {
        int returnedTurnNumber = game.playTurn();


    }
}