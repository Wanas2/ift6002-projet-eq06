package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;

import java.util.Map;

public class AddFoodAction implements ExecutableAction {

    private final Pantry pantry;
    private final Map<FoodType, Food> foods;

    public AddFoodAction(Pantry pantry, Map<FoodType, Food> foods) {
        this.pantry = pantry;
        this.foods = foods;
    }

    @Override
    public void execute() {
        pantry.addToNewBatchOfFreshFood(foods);
    }
}
