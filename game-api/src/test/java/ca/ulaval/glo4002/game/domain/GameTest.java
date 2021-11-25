package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.action.AddDinosaurAction;
import ca.ulaval.glo4002.game.domain.action.AddFoodAction;
import ca.ulaval.glo4002.game.domain.action.SumoFightAction;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.herd.Herd;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameTest {

    private Turn turn;
    private Herd herd;
    private Dinosaur aDinosaur;
    private Dinosaur anotherDinosaur;
    private Game game;
    private Pantry pantry;

    @BeforeEach
    void setUp() {
        turn = mock(Turn.class);
        herd = mock(Herd.class);
        aDinosaur = mock(Dinosaur.class);
        anotherDinosaur = mock(Dinosaur.class);
        pantry = mock(Pantry.class);
        game = new Game(herd, pantry, turn);
    }

    @Test
    public void whenAddFood_thenTurnShouldAcquireANewAction() {
        List<Food> foods = someFoods();

        game.addFood(foods);

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
    public void whenPlayTurn_thenPantryShouldStoreAllNewlyOrderedFoods() {
        game.playTurn();

        verify(pantry).storeAllNewlyOrderedFoods();
    }

    @Test
    public void whenPlayTurn_thenPantryShouldSplitWaterInTwo() {
        game.playTurn();

        verify(pantry).splitWaterInTwo();
    }

    @Test
    public void whenPlayTurn_thenHerdShouldFeedDinosaurs() {
        game.playTurn();

        verify(herd).feedDinosaurs();
    }

    @Test
    public void whenPlayTurn_thenPantryShouldMergeWater() {
        game.playTurn();

        verify(pantry).mergeWater();
    }

    @Test
    public void whenPlayTurn_thenHerdShouldResetSumoFight() {
        game.playTurn();

        verify(herd).resetSumoFight();
    }

    @Test
    public void whenPlayTurn_thenTheReturnedNumberShouldBeTheTurnNumber() {
        int expectedTurnNumber = 12;
        when(turn.playActions()).thenReturn(expectedTurnNumber);

        int turnNumber = game.playTurn();

        assertEquals(expectedTurnNumber, turnNumber);
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

    private List<Food> someFoods() {
        Food aFoodItem = mock(Food.class);
        Food anotherFoodItem = mock(Food.class);
        return List.of(aFoodItem,anotherFoodItem);
    }
}
