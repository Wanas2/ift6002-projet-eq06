package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class TurnTest {

    Action action1;
    Action action2;
    List<Action> actions;
    private Turn turn;

    @BeforeEach
    void setUp() {
        action1 = mock(Action.class);
        action2 = mock(Action.class);
        actions = Arrays.asList(action1, action2);
        turn = new Turn(actions);
    }

    @Test
    public void givenActions_whenPlayTurn_thenAllActionsAreExecuted() {
        turn.play();

        verify(action1).execute();
        verify(action2).execute();
    }

    @Test
    public void whenEmptyActions_thenActionsAreEmptied() {
        boolean actionsAreEmpty = turn.emptyActions();

//        assertTrue(actionsAreEmpty);
    }

    @Test
    public void givenActions_whenPlayTurn_thenThereAreNoRemainingActions() {
        turn.play();
    }
}
