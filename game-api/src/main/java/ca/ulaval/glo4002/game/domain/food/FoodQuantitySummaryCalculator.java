package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class FoodQuantitySummaryCalculator {

    public Map<String, Map<FoodType, Integer>> computeSummaries(Pantry pantry) {
        Map<String, Map<FoodType, Integer>> allFoodsSummary = new HashMap<>();
//        Map<FoodType, Integer> expiredFoodSummary = createQuantitySummaryForFood(pantry.getExpiredFoodQuantity());
//        Map<FoodType, Integer> consumedFoodSummary = createQuantitySummaryForFood(pantry.getConsumedFoodQuantity());

        List<Food> mergeAllBatchedOfFreshFood = mergeAllBatchedOfFreshFood(pantry.getAllFreshFood());
        Map<FoodType, Integer> freshFoodSummary = createQuantitySummaryForFood(mergeAllBatchedOfFreshFood);

        allFoodsSummary.put("fresh", freshFoodSummary);
//        allFoodsSummary.put("expired", expiredFoodSummary);
//        allFoodsSummary.put("consumed", consumedFoodSummary);

        return allFoodsSummary;
    }

    private List<Food> mergeAllBatchedOfFreshFood(List<Food> allFreshFood) {
        List<Food> freshFoodMerged = new ArrayList<>();
//
//        Food burgersOfQuantityZero = new Food(FoodType.BURGER, 0);
//        Food saladsOfQuantityZero = new Food(FoodType.SALAD, 0);
//        Food waterOfQuantityZero = new Food(FoodType.WATER, 0);
//        freshFoodMerged.add(burgersOfQuantityZero);
//        freshFoodMerged.add(saladsOfQuantityZero);
//        freshFoodMerged.add(waterOfQuantityZero);
//
//        allFreshFood.forEach((foodBatchOfATurn) ->
//                foodBatchOfATurn.forEach(foodInTheBatch -> {
//
//                    Food foodToMerge = freshFoodMerged.stream().
//                            filter(food -> food.getType().equals(foodInTheBatch.getType())).
//                            findAny().orElse(null);
//                    try {
//                        foodToMerge.increaseQuantity(foodInTheBatch);
//                    } catch (FoodTypesNotMatchingException e) {
//                        e.printStackTrace();
//                    }
//
//                })
//        );

        return freshFoodMerged;
    }

    private Map<FoodType, Integer> createQuantitySummaryForFood(List<Food> foodNeedingSummary) {
        Map<FoodType, Integer> foodQuantitySummary = new HashMap<>();

        foodNeedingSummary.forEach(food ->
                foodQuantitySummary.put(food.getType(), food.quantity()));

        return foodQuantitySummary;
    }
}
