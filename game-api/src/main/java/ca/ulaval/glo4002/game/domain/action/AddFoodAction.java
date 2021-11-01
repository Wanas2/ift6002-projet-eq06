package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;

import java.util.List;
import java.util.Map;

public class AddFoodAction implements ExecutableAction {

    private final Pantry pantry;
    private final List<Food> foods;

    public AddFoodAction(Pantry pantry, List<Food> foods) {
        this.pantry = pantry;
        this.foods = foods;
    }

    @Override
    public void execute() {
        pantry.addOrderedFoodToCurrentTurnFoodBatch(foods);
    }
}
