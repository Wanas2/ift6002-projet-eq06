package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.game.domain.Turn.Action;
import ca.ulaval.glo4002.game.domain.Turn.Turn;
import ca.ulaval.glo4002.game.domain.parkResources.Food;
import ca.ulaval.glo4002.game.domain.parkResources.Pantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.mockito.Mockito.*;

class TurnTest {

    private final int QUANTITY_OF_BURGER_FOR_A_TURN = 100;
    private final int QUANTITY_OF_SALAD_FOR_A_TURN = 250;
    private final int QUANTITY_OF_WATER_FOR_A_TURN = 10;

    private Action firstAction;
    private Action secondAction;
    private Food aFoodItem;
    private Food anotherFoodItem;
    List<Food> foods;
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
        turn = new Turn(pantry);
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
    public void givenActions_whenPlay_thenTurnShouldHaveNoActionsLeft() {
        turn.play(foods);

        assertFalse(turn.hasActions());
    }

    @Test
    public void whenPlayForTheFirstTime_thenTheTurnNumberIsOne() {
        int expectedTurnNumber = 1;

        int turnNumber = turn.play(foods);

        assertEquals(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenPlayMultipleTimes_thenTheTurnNumberShouldIncreaseByOneAfterEachPlay() {
        int expectedTurnNumber = 2;

        turn.play(foods);
        int currentTurnNumber = turn.play(foods);

        assertEquals(expectedTurnNumber, currentTurnNumber);
    }

    @Test
    public void givenFoods_whenPlay_thenFoodsIsAddedToPantry() {
        turn.play(foods);

        verify(pantry).addFood(foods);
    }

    @Test
    public void whenReset_thenTheNextPlay_thenTheTurnNumberIsSetToOne() { // Todo
        int expectedTurnNumber = 1;
        turn.play(foods);
        turn.play(foods);
        turn.play(foods);

        turn.reset();
        int turnNumberAfterReset = turn.play(foods);

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
