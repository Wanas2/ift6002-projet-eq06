package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class WaterDistributor {

    public int distributeExactOrMostPossible(List<Food> waterContainer, int requestedQuantity, FoodHistory foodHistory) {
        int remainingFoodQuantityToProvide = requestedQuantity;
        int totalFoodGiven = 0;

        List<Food> allFoodToRemove = new ArrayList<>();
        for(Food food : waterContainer) {
            if(remainingFoodQuantityToProvide <= food.quantity()) {
                food.decreaseQuantity(remainingFoodQuantityToProvide);
                foodHistory.
                        increaseConsumedQuantity(new Food(food.getType(), remainingFoodQuantityToProvide));
                totalFoodGiven += remainingFoodQuantityToProvide;
                break;
            } else {
                foodHistory.increaseConsumedQuantity(food);
                totalFoodGiven += food.quantity();
                remainingFoodQuantityToProvide -= food.quantity();
                allFoodToRemove.add(food);
            }
        }

        waterContainer.removeAll(allFoodToRemove);
        return totalFoodGiven;
    }
}
