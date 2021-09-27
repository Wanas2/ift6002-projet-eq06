package ca.ulaval.glo4002.game.domain.turn;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.game.domain.turn.Action;
import ca.ulaval.glo4002.game.domain.turn.Turn;
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

    private Action firstAction;
    private Action secondAction;
    private Food aFoodItem;
    private Food anotherFoodItem;
    private List<Food> foods;
    private Pantry pantry;
    private Queue<Action> actions;
    private Turn turn;

    @BeforeEach
    void setUp() {
        firstAction = mock(Action.class);
        secondAction = mock(Action.class);
        pantry = mock(Pantry.class);
        aFoodItem = mock(Food.class);
        anotherFoodItem = mock(Food.class);
        foods = new ArrayList<>();
        actions = new LinkedList<>();
        actions.add(firstAction);
        actions.add(secondAction);
        turn = new Turn();
    }

    @Test
    public void whenPlayForTheFirstTime_thenTheTurnNumberIsOne() {
        int expectedTurnNumber = 1;

        int turnNumber = turn.play();

        assertEquals(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenPlayMultipleTimes_thenTheTurnNumberShouldIncreaseByOneAfterEachPlay() {
        int expectedTurnNumber = 2;

        turn.play();
        int currentTurnNumber = turn.play();

        assertEquals(expectedTurnNumber, currentTurnNumber);
    }

    @Test
    public void whenReset_thenTheNextPlay_thenTheTurnNumberIsSetToOne() { // Todo
        int expectedTurnNumber = 1;
        turn.play();
        turn.play();
        turn.play();

        turn.reset();
        int turnNumberAfterReset = turn.play();

        assertEquals(expectedTurnNumber, turnNumberAfterReset);
    }
}
