package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.game.domain.Turn.Turn;
import ca.ulaval.glo4002.game.domain.parkResources.Food;
import ca.ulaval.glo4002.game.domain.parkResources.FoodsFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;

class GameTest {

    private final int QUANTITY_OF_BURGER_FOR_A_TURN = 100;
    private final int QUANTITY_OF_SALAD_FOR_A_TURN = 250;
    private final int QUANTITY_OF_WATER_FOR_A_TURN = 10;

    private Turn turn;
    private FoodsFactory foodsFactory;
    private Game game;
    private Food aFoodItem;
    private Food anotherFoodItem;
    List<Food> foods;

    @BeforeEach
    void setUp() {
        aFoodItem = mock(Food.class);
        anotherFoodItem = mock(Food.class);
        foods = new ArrayList<>();
        turn = mock(Turn.class);
        foodsFactory = mock(FoodsFactory.class);
        game = new Game(turn);
    }


    @Test
    public void whenPlayTurn_thenTurnIsPlayed() {
        game.playTurn(foods);

        verify(turn).play(foods);
    }

    @Test
    public void whenPlayTurn_thenShouldReturnTheTurnNumber() {
        int expectedTurnNumber = 12;
        willReturn(expectedTurnNumber).given(turn).play(foods);

        int turnNumber = game.playTurn(foods);

        assertSame(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(turn).reset();
    }
}
