package ca.ulaval.glo4002.game.domain.turn;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddFoodActionTest {

    private Pantry pantry;
    private Map<FoodType, Food> food;
    private AddFoodAction addFoodAction;

    @BeforeEach
    void setUp() {
        pantry = new Pantry();
        food = new HashMap<>();
        addFoodAction = new AddFoodAction(pantry, food);
    }

//    @Test
//    public void whenExecute_thenPantryShouldAddNewBatchOfFoodToFreshFood() {
//        addFoodAction.execute();
//
//        verify(pantry).addNewFoodToFreshFoodStorage();
//    }

//    @Disabled
//    @Test
//    public void whenExecute_thenPantryShouldVerifyExpiryDate() {
//        addFoodAction.execute();
//
//        verify(pantry).verifyExpiryDate();
//    }
}
