package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.turn.Turn;
import ca.ulaval.glo4002.game.domain.food.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;

class GameTest {

    private Turn turn;
    private Game game;
    private Pantry pantry;
    private Food aFoodItem1;
    private Food aFoodItem2;
    private Food aFoodItem3;
    private Map<FoodType, Food> foods;

    @BeforeEach
    void setUp() {
        aFoodItem1 = mock(Food.class);
        aFoodItem2 = mock(Food.class);
        aFoodItem3 = mock(Food.class);
        foods = new HashMap<>();
        foods.put(FoodType.BURGER, aFoodItem1);
        foods.put(FoodType.SALAD, aFoodItem2);
        foods.put(FoodType.WATER, aFoodItem3);
        turn = mock(Turn.class);
        pantry = mock(Pantry.class);
        game = new Game(turn, pantry);
    }

    @Test
    public void initiallyTheGameHasNoOrderedFood(){
        boolean hasFoodWaiting = game.hasFoodWaitingForPantry();

        assertFalse(hasFoodWaiting);
    }

    @Test
    public void whenOrderFood_thenFoodIsAddedToFoodWaitingForPantry() {
        game.orderFood(foods);
        boolean hasFoodWaiting = game.hasFoodWaitingForPantry();

        assertTrue(hasFoodWaiting);
    }

    @Test
    public void whenPlayTurn_thenTurnIsPlayed() {
        game.playTurn(foods);

        verify(turn).play();
    }

    @Test
    public void whenPlayTurn_thenShouldReturnTheTurnNumber() {
        int expectedTurnNumber = 12;
        willReturn(expectedTurnNumber).given(turn).play();

        int turnNumber = game.playTurn(foods);

        assertSame(expectedTurnNumber, turnNumber);
    }

    @Test
    public void givenFoods_whenPlayTurn_thenAddDefaultFoodForATurnToPantry() {
        game.playTurn(foods);

        verify(pantry).addFood(foods);
    }

    @Test
    public void whenPlayTurn_thenAgeOfFoodGetUpdated() {
        game.playTurn(foods);

        verify(pantry).updateFoodsExpiryDate();
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(turn).reset();
    }
}
