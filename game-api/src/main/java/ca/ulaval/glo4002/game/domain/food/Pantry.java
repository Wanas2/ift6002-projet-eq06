package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class Pantry implements FoodStorage {

    private Map<FoodType, Food> newFreshFood;
    private Queue<Map<FoodType, Food>> allFreshFood = new LinkedList<>();
    private Map<FoodType, Food> allExpiredFood = new HashMap<>();
    private Map<FoodType, Food> allConsumedFood = new HashMap<>();

    public Pantry() {
        initializeNewBatchOFreshFood();
        initializeExpiredFood();
        initiateConsumedFood();
    }

    private void initializeNewBatchOFreshFood() {
        newFreshFood = new HashMap<>();
        Food freshNewBurgersOfQuantityZero = new Food(FoodType.BURGER, 0); // Todo Mettre zero dans une variable?
        Food freshNewSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food freshNewWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        newFreshFood.put(FoodType.BURGER, freshNewBurgersOfQuantityZero);
        newFreshFood.put(FoodType.SALAD, freshNewSaladsOfQuantityZero);
        newFreshFood.put(FoodType.WATER, freshNewWaterOfQuantityZero);
    }

    private void initializeExpiredFood() {
        Food expiredBurgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food expiredSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food expiredWaterOfQuantityZero = new Food(FoodType.WATER, 0);

        allExpiredFood.put(FoodType.BURGER, expiredBurgersOfQuantityZero);
        allExpiredFood.put(FoodType.SALAD, expiredSaladsOfQuantityZero);
        allExpiredFood.put(FoodType.WATER, expiredWaterOfQuantityZero);
    }

    private void initiateConsumedFood() {
        Food consumedBurgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food consumedSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food consumedWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        allConsumedFood.put(FoodType.BURGER, consumedBurgersOfQuantityZero);
        allConsumedFood.put(FoodType.SALAD, consumedSaladsOfQuantityZero);
        allConsumedFood.put(FoodType.WATER, consumedWaterOfQuantityZero);
    }

    public void addToNewBatchOfFreshFood(Map<FoodType, Food> allFoodToBeAdded) {
        newFreshFood.forEach((foodType, food) -> {
            if(newFreshFood.containsKey(foodType))
                food.increase(allFoodToBeAdded.get(foodType));
        });
    }

    public void addFoodFromCookITToNewFood(CookItSubscription cookItSubscription) {
        Map<FoodType, Food> foodFromCookIt = cookItSubscription.provideFood();
        addToNewBatchOfFreshFood(foodFromCookIt);
    }

    public void addNewFoodToFreshFood() {
        allFreshFood.add(newFreshFood);
        initializeNewBatchOFreshFood();
    }

    public void removeExpiredFoodFromFreshFood() {
        for(Iterator<Map<FoodType, Food>> iterator = allFreshFood.iterator(); iterator.hasNext(); ) {
            Map<FoodType, Food> foodBatchOfATurn = iterator.next();
            List<FoodType> allFoodTypesToRemoveFromBatch = new ArrayList<>();

            foodBatchOfATurn.forEach(((foodType, food) -> {
                if(food.isExpired()) {
                    allExpiredFood.get(foodType).increase(food);
                    allFoodTypesToRemoveFromBatch.add(foodType);
                }
            }));

            allFoodTypesToRemoveFromBatch.forEach(foodBatchOfATurn::remove);
        }
    }

    public void incrementFreshFoodAges() {
        allFreshFood.forEach((foodBatch) ->
                foodBatch.forEach((foodType, food) ->
                        food.incrementAgeByOne()));
    }

    @Override
    public boolean provideFood(Map<FoodType, Food> allRequestedFood) {
        int availableFreshBurgersQuantity = availableQuantityOf(FoodType.BURGER);
        int availableFreshSaladsQuantity = availableQuantityOf(FoodType.SALAD);
        int availableFreshWaterQuantity = availableQuantityOf(FoodType.WATER);

        int requestedBurgers = allRequestedFood.get(FoodType.BURGER).quantity();
        int requestedSalads = allRequestedFood.get(FoodType.SALAD).quantity();
        int requestedWater = allRequestedFood.get(FoodType.WATER).quantity();

        while(requestedBurgers >= 0) {
            allFreshFood.forEach((foodBatchOfATurn) -> {
//                foodBatchOfATurn.get(FoodType.BURGER).decreaseQuantity();
            });
        }

        return false;
    }

    private int availableQuantityOf(FoodType foodType) {
        int availableFoodQuantity = 0;

        for(Map<FoodType, Food> foodBatchOfATurn : allFreshFood) {
            availableFoodQuantity += foodBatchOfATurn.get(foodType).quantity();
        }

        return availableFoodQuantity;
    }

    public Map<String, Map<FoodType, Integer>> getFoodQuantitySummary() {
        Map<String, Map<FoodType, Integer>> allFoodsSummary = new HashMap<>();
        Map<FoodType, Integer> expiredFoodSummary = createQuantitySummaryForFood(allExpiredFood);
        Map<FoodType, Integer> consumedFoodSummary = createQuantitySummaryForFood(allConsumedFood);

        Map<FoodType, Food> mergeAllBatchedOfFreshFood = mergeAllBatchedOfFreshFood();
        Map<FoodType, Integer> freshFoodSummary = createQuantitySummaryForFood(mergeAllBatchedOfFreshFood);

        allFoodsSummary.put("fresh", freshFoodSummary);
        allFoodsSummary.put("expired", expiredFoodSummary);
        allFoodsSummary.put("consumed", consumedFoodSummary);

        return allFoodsSummary;
    }

    private Map<FoodType, Food> mergeAllBatchedOfFreshFood() {
        Map<FoodType, Food> freshFoodMerged = new HashMap<>();

        Food burgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food saladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food waterOfQuantityZero = new Food(FoodType.WATER, 0);
        freshFoodMerged.put(FoodType.BURGER, burgersOfQuantityZero);
        freshFoodMerged.put(FoodType.SALAD, saladsOfQuantityZero);
        freshFoodMerged.put(FoodType.WATER, waterOfQuantityZero);

        allFreshFood.forEach((foodBatchOfATurn) ->
                foodBatchOfATurn.forEach((foodType, food) ->
                        freshFoodMerged.get(foodType).increase(food)
                )
        );

        return  freshFoodMerged;
    }

    private Map<FoodType, Integer> createQuantitySummaryForFood(Map<FoodType, Food> foodNeedingSummary) {
        Map<FoodType, Integer> foodQuantitySummary = new HashMap<>();

        foodNeedingSummary.forEach((foodType, food) ->
                foodQuantitySummary.put(foodType, food.quantity()));

        return foodQuantitySummary;
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
        initializeExpiredFood();
        initiateConsumedFood();
    }
}
