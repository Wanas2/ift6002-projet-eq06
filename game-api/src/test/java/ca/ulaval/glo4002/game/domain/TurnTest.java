package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.action.AddDinosaurAction;
import ca.ulaval.glo4002.game.domain.action.AddFoodAction;
import ca.ulaval.glo4002.game.domain.action.ExecutableAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TurnTest {


    private ExecutableAction aFirstAction;
    private ExecutableAction aSecondAction;
    private Turn turn;

    @BeforeEach
    void setUp() {

        aFirstAction = mock(AddFoodAction.class);
        aSecondAction = mock(AddDinosaurAction.class);
        turn = new Turn();
    }

    @Test
    public void givenAnAction_whenPlay_thenShouldExecuteTheAction() {
        turn.acquireNewAction(aFirstAction);

        turn.playActions();

        verify(aFirstAction).execute();
    }

    @Test

    public void givenMultipleActions_whenPlay_thenShouldExecuteAllActions() {
        turn.acquireNewAction(aFirstAction);
        turn.acquireNewAction(aSecondAction);

        turn.playActions();

        verify(aFirstAction).execute();
        verify(aSecondAction).execute();
    }

    @Test
    public void givenActions_whenPlay_thenTurnShouldHaveNoActionsLeft() {
        turn.playActions();

        assertFalse(turn.hasActions());
    }

    @Test
    public void whenPlayForTheFirstTime_thenTheTurnNumberIsOne() {
        int expectedTurnNumber = 1;

        int turnNumber = turn.playActions();

        assertEquals(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenPlayMultipleTimes_thenTheTurnNumberShouldIncreaseByOneAfterEachPlay() {
        int expectedTurnNumber = 2;

        turn.playActions();
        int currentTurnNumber = turn.playActions();

        assertEquals(expectedTurnNumber, currentTurnNumber);
    }

    @Test
    public void whenReset_thenTheNextPlay_thenTheTurnNumberIsSetToOne() {
        int expectedTurnNumber = 1;
        turn.playActions();

        turn.reset();
        int turnNumberAfterReset = turn.playActions();

        assertEquals(expectedTurnNumber, turnNumberAfterReset);
    }

    @Test
    public void whenReset_thenTurnShouldHaveNoActions() {
        turn.acquireNewAction(aFirstAction);
        turn.acquireNewAction(aSecondAction);

        turn.reset();

        assertFalse(turn.hasActions());
    }

}
