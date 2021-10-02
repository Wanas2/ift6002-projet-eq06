package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.game.domain.action.ExecutableAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Mockito.*;

class TurnTest {

    private ExecutableAction firstAction;
    private ExecutableAction secondAction;
    private Queue<ExecutableAction> actions;
    private Turn turn;

    @BeforeEach
    void setUp() {
        firstAction = mock(ExecutableAction.class);
        secondAction = mock(ExecutableAction.class);
        actions = new LinkedList<>();
        actions.add(firstAction);
        actions.add(secondAction);
        turn = new Turn();
    }

    @Test
    public void turnHasInitiallyNotActions() {
        boolean turnHasActions = turn.hasActions();

        assertFalse(turnHasActions);
    }

    @Test
    public void givenAnAction_whenAddAction_thenTheActionIsAddedToTurn() {
        turn.acquireNewAction(firstAction);

        assertTrue(turn.hasActions());
    }

    @Test
    public void givenActions_whenPlay_thenTurnShouldHaveNoActionsLeft() {
        turn.play();

        assertFalse(turn.hasActions());
    }

    @Test
    public void whenPlayForTheFirstTime_thenTheTurnNumberIsOne() {
        int expectedTrnNumber = 1;

        int turnNumber = turn.play();

        assertEquals(expectedTrnNumber, turnNumber);
    }

    @Test
    public void whenPlayMultipleTimes_thenTheTurnNumberShouldIncreaseByOneAfterEachPlay() {
        int expectedTrnNumber = 2;

        turn.play();
        int currentTurnNumber = turn.play();

        assertEquals(expectedTrnNumber, currentTurnNumber);
    }

    @Test
    public void givenATurnNumberGreaterThanZero_whenResetThenPlay_thenTheTurnNumberIsSetToOne() {
        int expectedTurnNumber = 1;
        turn.play();
        turn.play();
        turn.play();

        turn.reset();
        int turnNumberAfterReset = turn.play();

        assertEquals(expectedTurnNumber, turnNumberAfterReset);
    }

    @Test
    public void whenReset_thenTurnShouldHaveNoActions() {
        turn.acquireNewAction(firstAction);
        turn.acquireNewAction(secondAction);

        turn.reset();

        assertFalse(turn.hasActions());
    }
}
