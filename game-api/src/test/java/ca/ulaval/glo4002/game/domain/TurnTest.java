package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.mockito.Mockito.*;

class TurnTest {

    private Action firstAction;
    private Action secondAction;
    private Queue<Action> actions = new LinkedList<>();
    private Playable turn;

    @BeforeEach
    void setUp() {
        firstAction = mock(Action.class);
        secondAction = mock(Action.class);
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
        turn.addAction(firstAction);

        assertTrue(turn.hasActions());
    }

    @Test
    public void givenMultipleActions_whenAddAction_thenAllActionsAreAddedToTurn() {
        turn.addAction(firstAction);
        turn.addAction(secondAction);


    }

//    @Test
//    public void givenActions_whenPlay_ThenAllActionsAreExecuted() {
//        turn.play();
//
//        verify(firstAction).execute();
//        verify(secondAction).execute();
//    }

    // Todo Question: How to test this?
    @Test
    public void givenActions_whenPlay_ThenAllActionsAreExecutedInAFIFOrder() {
        turn.play();


//        verify()
    }

    // Todo Question: Et si les deux ensemble d'actions sont les même par coincidence?
    @Disabled
    @Test
    public void givenActions_whenPlayTwice_thenActionsExecutedInSecondTurnIsDifferentFromThoseOfFirstTurn() {
        Action firstActionOfSecondTurn = mock(Action.class);
        Action secondActionOfSecondTurn = mock(Action.class);
        Queue<Action> actionsOfSecondTurn = new LinkedList<>();
        actionsOfSecondTurn.add(firstAction);
        actionsOfSecondTurn.add(secondAction);

        turn.play();
        turn.play();
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
        turn.addAction(firstAction);
        turn.addAction(secondAction);

        turn.reset();

        assertFalse(turn.hasActions());
    }
}
