package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class Pantry {

    private final Map<FoodType, Food> newBatchOfFreshFood = new HashMap<>();
    private final Queue<Map<FoodType, Food>> freshFoodStored = new LinkedList<>();
    private final Map<FoodType, Food> expiredFood = new HashMap<>();

    public Pantry() {
        initiateNewBatchOfFreshFood();
    }

    private void initiateNewBatchOfFreshFood() {
        Food burgersOfQuantityZero = new Food(FoodType.BURGER, 0); // Todo Mettre zero dans une variable?
        Food saladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food waterOfQuantityZero = new Food(FoodType.WATER, 0);

        newBatchOfFreshFood.put(FoodType.BURGER, burgersOfQuantityZero);
        newBatchOfFreshFood.put(FoodType.SALAD, saladsOfQuantityZero);
        newBatchOfFreshFood.put(FoodType.WATER, waterOfQuantityZero);
    }

    public void addToNewBatchOfFreshFood(Map<FoodType, Food> food) { // Todo Le faire avec une boucle
        newBatchOfFreshFood.get(FoodType.BURGER).increaseQuantity(food.get(FoodType.BURGER));
        newBatchOfFreshFood.get(FoodType.SALAD).increaseQuantity(food.get(FoodType.SALAD));
        newBatchOfFreshFood.get(FoodType.WATER).increaseQuantity(food.get(FoodType.WATER));
    }

    public void addNewFoodToFreshFood() {
        freshFoodStored.add(newBatchOfFreshFood);
    }

    /**************************/
    public boolean provideFood(Map<FoodType, Food> requestedFood) {
        for(Map.Entry<FoodType, Food> requestedFoodEntry: requestedFood.entrySet()) {
            freshFoodStored.stream()
                    .forEach(storedFreshFoodTurnBatch -> {

                    });
        }
        return false;
    }

    public void verifyExpiryDate() {
        for(Map<FoodType, Food> aBatchOfFoodOfATurn: freshFoodStored) {
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
