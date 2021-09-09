package ca.ulaval.glo4002.game.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnTest {

    private Turn turn;

    @BeforeEach
    void setUp() {
        turn = new Turn();
    }

    @Test
    public void givenTheCurrentNumber_whenPlay_thenTurnNumberShouldIncrementedByOne() {
        int initialTurnNumber = turn.getTurnNumber();
        int expectedTurnNumber = initialTurnNumber + 1;

        turn.play();

        assertEquals(expectedTurnNumber, turn.getTurnNumber());
    }

    @Test
    public void givenTheCurrentTurnNumberGreaterThanZero_whenReset_thenTurnNumberShouldSetToZero() {
        turn.play();
        int expectedTurnNumber = 0;

        turn.reset();

        int resetTurnNumber = turn.getTurnNumber();
        assertEquals(expectedTurnNumber, resetTurnNumber);
    }
}
