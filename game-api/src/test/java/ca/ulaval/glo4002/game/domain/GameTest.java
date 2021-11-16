package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.action.AddDinosaurAction;
import ca.ulaval.glo4002.game.domain.action.AddFoodAction;
import ca.ulaval.glo4002.game.domain.action.SumoFightAction;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class GameTest {

    private Turn turn;
    private Herd herd;
    private Dinosaur aDinosaur;
    private Dinosaur anotherDinosaur;
    private Game game;
    private Pantry pantry;
    private List<Food> food;

    @BeforeEach
    void setUp() {
        initializeFood();
        turn = mock(Turn.class);
        herd = mock(Herd.class);
        aDinosaur = mock(Dinosaur.class);
        anotherDinosaur = mock(Dinosaur.class);
        pantry = mock(Pantry.class);
        game = new Game(herd, pantry, turn);
    }

    @Test
    public void whenAddFood_thenTurnShouldAcquireANewAction() {
        game.addFood(food);

        verify(turn).acquireNewAction(any(AddFoodAction.class));
    }

    @Test
    public void whenAddDinosaur_thenTurnShouldAcquireANewAction() {
        game.addDinosaur(aDinosaur);

        verify(turn).acquireNewAction(any(AddDinosaurAction.class));
    }

    @Test
    public void whenAddSumoFight_thenTurnShouldAcquireANewAction() {
        game.addSumoFight(aDinosaur,anotherDinosaur);

        verify(turn).acquireNewAction(any(SumoFightAction.class));
    }

    @Test
    public void whenPlayTurn_thenTurnShouldPlayActions() {
        game.playTurn();

        verify(turn).playActions();
    }

    @Test
    public void whenPlayTurn_pantryShouldIncrementAllFreshFoodAges() {
        game.playTurn();

        verify(pantry).incrementFreshFoodAges();
    }

    @Test
    public void whenPlayTurn_thenPantryShouldAddNewFoodToFreshFood() {
//        game.playTurn();
//
//        verify(pantry).addCurrentTurnFoodBatchToFreshFood();
    }

    @Test
    public void whenPlayTurn_thenShouldReturnTheTurnNumber() {
//        int expectedTurnNumber = 12;
//        willReturn(expectedTurnNumber).given(turn).playActions();
//
//        int turnNumber = game.playTurn();
//
//        assertSame(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(turn).reset();
    }

    @Test
    public void whenReset_thenPantryShouldBeReset() {
        game.reset();

        verify(pantry).reset();
    }

    @Test
    public void whenReset_thenHerdShouldBeReset() {
        game.reset();

        verify(herd).reset();
    }

    private void initializeFood() {
        Food aFoodItem = mock(Food.class);
        Food anotherFoodItem = mock(Food.class);
        food = new ArrayList<>();
        food.add(aFoodItem);
        food.add(anotherFoodItem);
    }
}
