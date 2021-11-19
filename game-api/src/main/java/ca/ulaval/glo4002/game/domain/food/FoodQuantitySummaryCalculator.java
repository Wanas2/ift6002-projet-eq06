package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;

public class FoodQuantitySummaryCalculator {

    public Map<FoodState, Map<FoodType, Integer>> computeSummaries(Pantry pantry) {
        Map<FoodState, Map<FoodType, Integer>> allFoodsSummary = new HashMap<>();
        Map<FoodType, Integer> freshFoodQuantities = computeFreshFoodQuantitySummary(pantry);
        Map<FoodType, Integer> expiredFoodQuantities = pantry.getExpiredFoodQuantities();
        Map<FoodType, Integer> consumedFoodQuantities = pantry.getConsumedFoodQuantities();

        allFoodsSummary.put(FoodState.FRESH, freshFoodQuantities);
        allFoodsSummary.put(FoodState.EXPIRED, expiredFoodQuantities);
        allFoodsSummary.put(FoodState.CONSUMED, consumedFoodQuantities);

        return allFoodsSummary;
    }

    private Map<FoodType, Integer> computeFreshFoodQuantitySummary(Pantry pantry) {
        Map<FoodType, Integer> foodQuantitySummary = new HashMap<>();

        for(FoodType foodType : FoodType.values()) {
            Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(foodType);
            int foodQuantity = pantry.getAllFreshFood().stream()
                    .filter(foodTypeFilter)
                    .mapToInt(Food::quantity).sum();

            foodQuantitySummary.put(foodType, foodQuantity);
        }

        return foodQuantitySummary;
    }
}
