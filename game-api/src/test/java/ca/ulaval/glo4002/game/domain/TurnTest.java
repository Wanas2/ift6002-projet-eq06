package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.game.domain.action.AddDinosaureAction;
import ca.ulaval.glo4002.game.domain.action.AddFoodAction;
import ca.ulaval.glo4002.game.domain.action.ExecutableAction;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.mockito.Mockito.*;

class TurnTest {

    private ExecutableAction aFirstAction;
    private ExecutableAction aSecondAction;
    private Food aFoodItem;
    private Food anotherFoodItem;
    private List<Food> foods;
    private Pantry pantry;
    private Queue<ExecutableAction> actions;
    private Turn turn;

    @BeforeEach
    void setUp() {
        aFirstAction = mock(AddFoodAction.class);
        aSecondAction = mock(AddDinosaureAction.class);
        actions = new LinkedList<>();
        pantry = mock(Pantry.class);
        aFoodItem = mock(Food.class);
        anotherFoodItem = mock(Food.class);
        foods = new ArrayList<>();
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
    public void whenReset_thenTheNextPlay_thenTheTurnNumberIsSetToOne() { // Todo Redo
        int expectedTurnNumber = 1;
        turn.playActions();

        turn.reset();
        int turnNumberAfterReset = turn.playActions();

        assertEquals(expectedTurnNumber, turnNumberAfterReset);
    }
}
