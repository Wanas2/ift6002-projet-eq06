package ca.ulaval.glo4002.game.domain.food;

import java.util.Map;

public class Pantry {

    private boolean canProvideFood = true;

    public void orderFood() {

    }

    public void addNewBatchOfFoodToFreshFood(CookItSubscription cookItSubscription) {
        cookItSubscription.provideFood();
    }

    public boolean provideFood(Map<FoodType, Food> food) {
        return canProvideFood;
    }
}
