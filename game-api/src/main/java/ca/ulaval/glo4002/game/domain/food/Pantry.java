package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class Pantry implements FoodStorage {

    private Map<FoodType, Food> CurrentTurnFoodBatch;
    private Queue<Map<FoodType, Food>> allFreshFood = new LinkedList<>();
    private final Map<FoodType, Food> allExpiredFood = new HashMap<>();
    private final Map<FoodType, Food> allConsumedFood = new HashMap<>();

    public Pantry() {
        initializeNewBatchOFreshFood();
        initializeExpiredFood();
        initiateConsumedFood();
    }

    private void initializeNewBatchOFreshFood() {
        CurrentTurnFoodBatch = new HashMap<>();
        Food freshNewBurgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food freshNewSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food freshNewWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        CurrentTurnFoodBatch.put(FoodType.BURGER, freshNewBurgersOfQuantityZero);
        CurrentTurnFoodBatch.put(FoodType.SALAD, freshNewSaladsOfQuantityZero);
        CurrentTurnFoodBatch.put(FoodType.WATER, freshNewWaterOfQuantityZero);
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

    public void addToCurrentTurnFoodBatch(Map<FoodType, Food> foodToBeAdded) {
        CurrentTurnFoodBatch.forEach((foodType, food) -> {
            if(CurrentTurnFoodBatch.containsKey(foodType))
                food.increaseQuantity(foodToBeAdded.get(foodType));
        });
    }

    public void addFoodFromCookITToCurrentTurnFoodBatch(CookItSubscription cookItSubscription) {
        Map<FoodType, Food> foodFromCookIt = cookItSubscription.provideFood();
        addToCurrentTurnFoodBatch(foodFromCookIt);
    }

    public void addCurrentTurnFoodBatchToFreshFood() {
        allFreshFood.add(CurrentTurnFoodBatch);
        initializeNewBatchOFreshFood();
    }

    public void removeExpiredFoodFromFreshFood() {
        for(Iterator<Map<FoodType, Food>> iterator = allFreshFood.iterator(); iterator.hasNext(); ) {
            Map<FoodType, Food> foodBatchOfOneTurn = iterator.next();
            List<FoodType> allFoodToRemoveFromThisBatch = obtainAllExpiredFoodOfOneTurnBatch(foodBatchOfOneTurn);
            allFoodToRemoveFromThisBatch.forEach(foodBatchOfOneTurn::remove);
        }
    }

    private List<FoodType> obtainAllExpiredFoodOfOneTurnBatch(Map<FoodType, Food> foodBatchOfOneTurn) {
        List<FoodType> allExpiredFoodFromTheTurnBatch = new ArrayList<>();
        foodBatchOfOneTurn.forEach(((foodType, food) -> {
            if(food.isExpired()) {
                allExpiredFood.get(foodType).increaseQuantity(food);
                allExpiredFoodFromTheTurnBatch.add(foodType);
            }
        }));

        return allExpiredFoodFromTheTurnBatch;
    }

    public void incrementFreshFoodAges() {
        allFreshFood.forEach((foodBatch) ->
                foodBatch.forEach((foodType, food) ->
                        food.incrementAgeByOne()));
    }

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        int quantityOfBurgerToProvide = quantityOfFoodToProvide(FoodType.BURGER, requestedBurgerQuantity);
        moveFreshFoodToConsumedFood(quantityOfBurgerToProvide, FoodType.BURGER);
        return quantityOfBurgerToProvide;
    }

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        int quantityOfSaladToProvide = quantityOfFoodToProvide(FoodType.SALAD, requestedSaladQuantity);
        moveFreshFoodToConsumedFood(quantityOfSaladToProvide, FoodType.SALAD);
        return quantityOfSaladToProvide;
    }

    @Override
    public int giveExactOrMostPossibleWaterDesired(int requestedWaterQuantity) {
        int quantityOfWaterToProvide = quantityOfFoodToProvide(FoodType.WATER, requestedWaterQuantity);
        moveFreshFoodToConsumedFood(quantityOfWaterToProvide, FoodType.WATER);
        return quantityOfWaterToProvide;
    }

    private void moveFreshFoodToConsumedFood (int quantityToMove, FoodType foodType) {
        int quantityMoved;
        for(Map<FoodType, Food> foodBatchOfATurn : allFreshFood) {
            if(foodBatchOfATurn.containsKey(foodType)) {
                quantityMoved = moveFoodFromOneBatchOfATurn(quantityToMove, foodBatchOfATurn, foodType);
                quantityToMove -= quantityMoved;
            }
            if(quantityToMove <= 0)
                break;
        }
    }

    private int moveFoodFromOneBatchOfATurn(int quantityToMove, Map<FoodType, Food> foodBatchOfATurn,
                                             FoodType foodType) {
        int quantityMoved = quantityToMove;
        if(quantityToMove <= foodBatchOfATurn.get(foodType).quantity()) {
            foodBatchOfATurn.get(foodType).decreaseQuantity(quantityToMove);
            allConsumedFood.get(foodType).increaseQuantity(quantityToMove);
        } else {
            quantityMoved = foodBatchOfATurn.get(foodType).quantity();
            Food removedFood = foodBatchOfATurn.remove(foodType);
            allConsumedFood.get(foodType).increaseQuantity(removedFood);
        }
        return quantityMoved;
    }

    private int quantityOfFoodToProvide(FoodType foodType,  int requestedFoodQuantity) {
        int availableFoodQuantity = 0;

        for(Map<FoodType, Food> foodBatchOfATurn : allFreshFood) {
            if(foodBatchOfATurn.containsKey(foodType))
                availableFoodQuantity += foodBatchOfATurn.get(foodType).quantity();
        }

        if(requestedFoodQuantity <= availableFoodQuantity)
            return requestedFoodQuantity;
        else
            return availableFoodQuantity;
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
        initializeExpiredFood();
        initiateConsumedFood();
    }
}
