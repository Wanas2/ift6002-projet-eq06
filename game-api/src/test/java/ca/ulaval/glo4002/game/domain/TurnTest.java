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
        int initialTurnNumber = turn.play();
        int expectedTurnNumber = initialTurnNumber + 1;

        int turnNumber = turn.play();

        assertEquals(expectedTurnNumber, turnNumber);
    }

    @Test
    public void givenPlayedSeveralTimes_whenReset_thenTurnNumberShouldBeOneAfterFirstPlay() {
        turn.play();
        turn.play();
        int expectedTurnNumber = 1;

        turn.reset();

        assertEquals(expectedTurnNumber, turn.play());
    }
}
