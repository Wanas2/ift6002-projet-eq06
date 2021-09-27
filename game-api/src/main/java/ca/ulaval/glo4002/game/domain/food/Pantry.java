package ca.ulaval.glo4002.game.domain.food;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Pantry {

    private Map<FoodType, Food> orderedFoodForATurn = new HashMap<>();
    private final Queue<Map<FoodType, Food>> allBatchesOfFreshFoodForEachTurn = new LinkedList<>();
    private final Map<FoodType, Food> expiredFood = new HashMap<>();

    public Pantry() {
        initializePantry();
    }

    private void initializePantry() {
        Food burgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food saladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food waterOfQuantityZero = new Food(FoodType.WATER, 0);

        orderedFoodForATurn.put(FoodType.BURGER, burgersOfQuantityZero);
        orderedFoodForATurn.put(FoodType.SALAD, saladsOfQuantityZero);
        orderedFoodForATurn.put(FoodType.WATER, waterOfQuantityZero);
    }

    public void orderFood(Map<FoodType, Food> freshFoodOrdered) {
        addUpTwoSetsOfFood(orderedFoodForATurn, freshFoodOrdered);
    }

    public boolean provideFood(Map<FoodType, Food> requestedFood) {
        for(Map.Entry<FoodType, Food> requestedFoodEntry: requestedFood.entrySet()) { // Todo DINO
            allBatchesOfFreshFoodForEachTurn.stream()
                    .forEach(storedFreshFoodTurnBatch -> {

                    });
        }
        return false;
    }

    public void addNewFoodToFreshFoodStorage() {
        allBatchesOfFreshFoodForEachTurn.add(orderedFoodForATurn);
    }

    private void provideOneFoodType(FoodType foodType, Food food) { // Todo DINO

    }

    private void addUpTwoSetsOfFood(Map<FoodType, Food> foodTo, Map<FoodType, Food> foodFrom) {
        foodTo.get(FoodType.BURGER)
                .increaseQuantity(foodFrom.get(FoodType.BURGER));
        foodTo.get(FoodType.SALAD)
                .increaseQuantity(foodFrom.get(FoodType.SALAD));
        foodTo.get(FoodType.WATER)
                .increaseQuantity(foodFrom.get(FoodType.WATER));
    }

    public void verifyExpiryDate() {
        for(Map<FoodType, Food> aBatchOfFoodOfATurn: allBatchesOfFreshFoodForEachTurn) {
            aBatchOfFoodOfATurn.entrySet()
                    .forEach(food -> {
                        if (food.getValue().isExpired())
                            moveFoodFromFreshFoodToExpiredFood(food);
                        food.getValue().incrementCurrentNumberOfTurns();
                    });
        }
    }

    public void moveFoodFromFreshFoodToExpiredFood(Map.Entry<FoodType, Food> foodEntry) {
        if(!expiredFood.containsKey(foodEntry.getKey()))
            expiredFood.put(foodEntry.getKey(), foodEntry.getValue());
        else
            expiredFood.get(foodEntry.getKey())
                    .increaseQuantity(foodEntry.getValue());
    }
}
