package ca.ulaval.glo4002.game.domain.food;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodDistributor {

    public int distributeExactOrMostPossible(FoodType foodTypeToProvide, List<Food> allFreshFood, int requestedQuantity,
                                             FoodHistory foodHistory) {
        int remainingFoodQuantityToProvide = requestedQuantity;
        int totalFoodGiven = 0;

        List<Food> allFood = allFreshFood.stream()
                .filter(food -> food.getType().equals(foodTypeToProvide))
                .collect(Collectors.toList());

        List<Food> allFoodToRemove = new ArrayList<>();
        for(Food food : allFood) {
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

        allFreshFood.removeAll(allFoodToRemove);
        return totalFoodGiven;
    }
}
