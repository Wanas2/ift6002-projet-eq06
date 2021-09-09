package ca.ulaval.glo4002.game.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnFactoryTest {

    private TurnFactory turnFactory;

    @BeforeEach
    void setUp() {
        turnFactory = new TurnFactory();
    }

    @Test
    public void whenCreateTurn_thenATurnIsCreated() {
        Turn turn = turnFactory.createTurn();

        assertNotNull(turn);
    }
}