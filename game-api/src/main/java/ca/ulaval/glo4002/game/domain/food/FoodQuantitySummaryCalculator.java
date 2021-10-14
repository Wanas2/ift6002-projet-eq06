package ca.ulaval.glo4002.game.domain.food;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class FoodQuantitySummaryCalculator {

    public Map<String, Map<FoodType, Integer>> computeSummaries(Pantry pantry) {
        Map<String, Map<FoodType, Integer>> allFoodsSummary = new HashMap<>();
        Map<FoodType, Integer> expiredFoodSummary = createQuantitySummaryForFood(pantry.getAllExpiredFood());
        Map<FoodType, Integer> consumedFoodSummary = createQuantitySummaryForFood(pantry.getAllConsumedFood());

        Map<FoodType, Food> mergeAllBatchedOfFreshFood = mergeAllBatchedOfFreshFood(pantry.getAllFreshFood());
        Map<FoodType, Integer> freshFoodSummary = createQuantitySummaryForFood(mergeAllBatchedOfFreshFood);

        allFoodsSummary.put("fresh", freshFoodSummary);
        allFoodsSummary.put("expired", expiredFoodSummary);
        allFoodsSummary.put("consumed", consumedFoodSummary);

        return allFoodsSummary;
    }

    private Map<FoodType, Food> mergeAllBatchedOfFreshFood(Queue<Map<FoodType, Food>> allFreshFood) {
        Map<FoodType, Food> freshFoodMerged = new HashMap<>();

        Food burgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food saladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food waterOfQuantityZero = new Food(FoodType.WATER, 0);
        freshFoodMerged.put(FoodType.BURGER, burgersOfQuantityZero);
        freshFoodMerged.put(FoodType.SALAD, saladsOfQuantityZero);
        freshFoodMerged.put(FoodType.WATER, waterOfQuantityZero);

        allFreshFood.forEach((foodBatchOfATurn)->
                foodBatchOfATurn.forEach((foodType, food)->
                        {
                            try {
                                freshFoodMerged.get(foodType).increaseQuantity(food);
                            } catch(FoodTypesNotMatchingException exception) {
                                exception.printStackTrace();
                            }
                        }
                )
        );

        return freshFoodMerged;
    }

    private Map<FoodType, Integer> createQuantitySummaryForFood(Map<FoodType, Food> foodNeedingSummary) {
        Map<FoodType, Integer> foodQuantitySummary = new HashMap<>();

        foodNeedingSummary.forEach((foodType, food)->
                foodQuantitySummary.put(foodType, food.quantity()));

        return foodQuantitySummary;
    }
}
