package ca.ulaval.glo4002.game.domain.turn;

import static org.junit.jupiter.api.Assertions.*;

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
        actions.add(aFirstAction);

        turn.play(actions);

        verify(aFirstAction).execute();
    }

    @Test
    public void givenMultipleActions_whenPlay_thenShouldExecuteAllActions() {
        actions.add(aFirstAction);
        actions.add(aSecondAction);

        turn.play(actions);

        verify(aFirstAction).execute();
        verify(aSecondAction).execute();
    }

    @Test
    public void whenPlayForTheFirstTime_thenTheTurnNumberIsOne() {
        int expectedTurnNumber = 1;

        int turnNumber = turn.play(actions);

        assertEquals(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenPlayMultipleTimes_thenTheTurnNumberShouldIncreaseByOneAfterEachPlay() {
        int expectedTurnNumber = 2;

        turn.play(actions);
        int currentTurnNumber = turn.play(actions);

        assertEquals(expectedTurnNumber, currentTurnNumber);
    }



    @Test
    public void whenReset_thenTheNextPlay_thenTheTurnNumberIsSetToOne() { // Todo Redo
        int expectedTurnNumber = 1;
        turn.play(actions);

        turn.reset();
        int turnNumberAfterReset = turn.play(actions);

        assertEquals(expectedTurnNumber, turnNumberAfterReset);
    }
}
