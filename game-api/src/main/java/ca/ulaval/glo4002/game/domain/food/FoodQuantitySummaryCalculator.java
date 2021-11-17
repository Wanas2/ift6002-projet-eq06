package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;

public class FoodQuantitySummaryCalculator {

    private final Map<FoodType, Integer> freshFoodQuantities = new HashMap<>();
    private final Map<FoodType, Integer> expiredFoodQuantities = new HashMap<>();
    private final Map<FoodType, Integer> consumedFoodQuantities = new HashMap<>();

    public Map<FoodType, Integer> getFreshFoodQuantities(List<Food> allFreshFood) {
        computeFreshFoodQuantitySummary(allFreshFood);
        return freshFoodQuantities;
    }

    public Map<FoodType, Integer> getExpiredFoodQuantities() {
        return expiredFoodQuantities;
    }

    public Map<FoodType, Integer> getConsumedFoodQuantities() {
        return consumedFoodQuantities;
    }

    public FoodQuantitySummaryCalculator() {
        initializeExpiredFoodQuantities();
        initiateConsumedFoodQuantities();
    }

    private void initializeExpiredFoodQuantities() {
        expiredFoodQuantities.put(FoodType.BURGER, 0);
        expiredFoodQuantities.put(FoodType.SALAD, 0);
        expiredFoodQuantities.put(FoodType.WATER, 0);
    }

    private void initiateConsumedFoodQuantities() {
        consumedFoodQuantities.put(FoodType.BURGER, 0);
        consumedFoodQuantities.put(FoodType.SALAD, 0);
        consumedFoodQuantities.put(FoodType.WATER, 0);
    }

    public Map<FoodState, Map<FoodType, Integer>> getAllFoodQuantities() {
        Map<FoodState, Map<FoodType, Integer>> allFoodsSummary = new HashMap<>();

        allFoodsSummary.put(FoodState.FRESH, freshFoodQuantities);
        allFoodsSummary.put(FoodState.CONSUMED, consumedFoodQuantities);
        allFoodsSummary.put(FoodState.EXPIRED, expiredFoodQuantities);

        return allFoodsSummary;
    }

    public void computeFreshFoodQuantitySummary(List<Food> allFreshFood) {
        for(FoodType foodType : FoodType.values()) {
            Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(foodType);
            int foodQuantity = allFreshFood.stream()
                    .filter(foodTypeFilter)
                    .mapToInt(Food::quantity).sum();

            freshFoodQuantities.put(foodType, foodQuantity);
        }
    }

    public void increaseExpiredQuantity(Food food) {
        int currentFoodQuantity = expiredFoodQuantities.get(food.getType());
        int newFoodQuantity = currentFoodQuantity + food.quantity();
        expiredFoodQuantities.put(food.getType(), newFoodQuantity);
    }

    public void increaseConsumedQuantity(Food food) {
        int currentFoodQuantity = consumedFoodQuantities.get(food.getType());
        int newFoodQuantity = currentFoodQuantity + food.quantity();
        consumedFoodQuantities.put(food.getType(), newFoodQuantity);
    }

    public void reset() {
        initiateConsumedFoodQuantities();
        initializeExpiredFoodQuantities();
    }
}
