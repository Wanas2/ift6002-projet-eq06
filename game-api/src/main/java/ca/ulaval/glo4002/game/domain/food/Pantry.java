package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class Pantry {

    private final Map<FoodType, Food> newFreshFood = new HashMap<>();
    private Queue<Map<FoodType, Food>> freshFood = new LinkedList<>();
    private Map<FoodType, Food> expiredFood = new HashMap<>();
    private Map<FoodType, Food> consumedFood = new HashMap<>();

    public Pantry() {
        initiatePantryFood();
    }

    private void initiatePantryFood() {
        Food freshNewBurgersOfQuantityZero = new Food(FoodType.BURGER, 0); // Todo Mettre zero dans une variable?
        Food freshNewSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food freshNewWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        newFreshFood.put(FoodType.BURGER, freshNewBurgersOfQuantityZero);
        newFreshFood.put(FoodType.SALAD, freshNewSaladsOfQuantityZero);
        newFreshFood.put(FoodType.WATER, freshNewWaterOfQuantityZero);

        Food expiredBurgersOfQuantityZero = new Food(FoodType.BURGER, 0); // Todo Mettre zero dans une variable?
        Food expiredSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food expiredWaterOfQuantityZero = new Food(FoodType.WATER, 0);

        expiredFood.put(FoodType.BURGER, expiredBurgersOfQuantityZero);
        expiredFood.put(FoodType.SALAD, expiredSaladsOfQuantityZero);
        expiredFood.put(FoodType.WATER, expiredWaterOfQuantityZero);

        Food burgersOfQuantityZero = new Food(FoodType.BURGER, 0); // Todo Mettre zero dans une variable?
        Food saladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food waterOfQuantityZero = new Food(FoodType.WATER, 0);
        consumedFood.put(FoodType.BURGER, burgersOfQuantityZero);
        consumedFood.put(FoodType.SALAD, saladsOfQuantityZero);
        consumedFood.put(FoodType.WATER, waterOfQuantityZero);
    }

    public void addToNewBatchOfFreshFood(Map<FoodType, Food> food) { // Todo Le faire avec une boucle
        newFreshFood.get(FoodType.BURGER).increaseQuantity(food.get(FoodType.BURGER));
        newFreshFood.get(FoodType.SALAD).increaseQuantity(food.get(FoodType.SALAD));
        newFreshFood.get(FoodType.WATER).increaseQuantity(food.get(FoodType.WATER));
    }

    public void addFoodFromCookITToNewFood(CookItSubscription cookItSubscription) { // Todo Rename?
        Map<FoodType, Food> foodFromCookIt = cookItSubscription.provideFood();
        addToNewBatchOfFreshFood(foodFromCookIt);
    }

    public void addNewFoodToFreshFood() { // Todo Rename?
        freshFood.add(newFreshFood);
    }

    public void removeExpiredFoodFromFreshFood() { // Todo Ce n'est pas clean
        for(Map<FoodType, Food> foodOfATurn: freshFood) {
            foodOfATurn.forEach((foodType, food) -> {
                if (food.isExpired()) {
                    expiredFood.get(foodType).increaseQuantity(food);
                    foodOfATurn.remove(foodType);
                }
            });
        }
    }

    public void reset() {
        freshFood = new LinkedList<>();
        expiredFood = new HashMap<>();
        consumedFood = new HashMap<>();
    }

    public boolean provideFood(Map<FoodType, Food> requestedFood) { // Todo For Dino
        for(Map.Entry<FoodType, Food> requestedFoodEntry: requestedFood.entrySet()) {
            freshFood.stream()
                    .forEach(storedFreshFoodTurnBatch -> {

                    });
        }
        return false;
    }

    public Map<String, Map<FoodType, Integer>> getFoodQuantitySummary() {
        Map<String, Map<FoodType, Integer>> allFoodsSummary = new HashMap();
        Map<FoodType, Integer> expiredFoodSummary = addUpFoodQuantitiesOfOneGroupOfFood(expiredFood);
        Map<FoodType, Integer> consumedFoodSummary = addUpFoodQuantitiesOfOneGroupOfFood(consumedFood);

        Map<FoodType, Integer> freshFoodSummary = new HashMap<>();
        freshFoodSummary.put(FoodType.BURGER, 0);
        freshFoodSummary.put(FoodType.SALAD, 0);
        freshFoodSummary.put(FoodType.WATER, 0);
        int quantityBurger = 0;
        int quantitySalad = 0;
        int quantityWater = 0;
        for(Map<FoodType, Food> foodBatch : freshFood) {
            Map<FoodType, Integer> foodBatchSummary = addUpFoodQuantitiesOfOneGroupOfFood(foodBatch);
            quantityBurger += foodBatchSummary.get(FoodType.BURGER);
            quantitySalad += foodBatchSummary.get(FoodType.SALAD);
            quantityWater += foodBatchSummary.get(FoodType.WATER);

            freshFoodSummary.replace(FoodType.BURGER, quantityBurger);
            freshFoodSummary.replace(FoodType.SALAD, quantitySalad);
            freshFoodSummary.replace(FoodType.WATER, quantityWater);
        }

        allFoodsSummary.put("fresh", freshFoodSummary);
        allFoodsSummary.put("expired", expiredFoodSummary);
        allFoodsSummary.put("consumed", consumedFoodSummary);

        return allFoodsSummary;
    }

    private Map<FoodType, Integer> addUpFoodQuantitiesOfOneGroupOfFood(Map<FoodType, Food> food) { // Todo Rename
        Map<FoodType, Integer> foodQuantitySummary = new HashMap<>();

        int quantityBurger = food.get(FoodType.BURGER).getQuantity();
        int quantitySalad = food.get(FoodType.SALAD).getQuantity();
        int quantityWater = food.get(FoodType.WATER).getQuantity();

        foodQuantitySummary.put(FoodType.BURGER, quantityBurger);
        foodQuantitySummary.put(FoodType.SALAD, quantitySalad);
        foodQuantitySummary.put(FoodType.WATER, quantityWater);

        return foodQuantitySummary;
    }
}
