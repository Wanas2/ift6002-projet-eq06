package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

class AddFoodActionTest {

    private Pantry pantry;
    private Map<FoodType, Food> foods;
    private AddFoodAction addFoodAction;

    @BeforeEach
    void setUp() {
        pantry = mock(Pantry.class);
        foods = new HashMap<>();
        addFoodAction = new AddFoodAction(pantry, foods);
    }

    @Test
    public void givenFood_whenExecute_thenAddTheFoodToNewBatchOfFreshFood() {
        addFoodAction.execute();
        verify(pantry).addToNewBatchOfFreshFood(foods);
    }
}
