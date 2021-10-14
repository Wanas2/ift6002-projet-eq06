package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class Pantry implements FoodStorage {

    private final CookItSubscription cookItSubscription;
    private Map<FoodType, Food> currentTurnFoodBatch;
    private Queue<Map<FoodType, Food>> allFreshFood = new LinkedList<>();
    private final Map<FoodType, Food> allExpiredFood = new HashMap<>();
    private final Map<FoodType, Food> allConsumedFood = new HashMap<>();

    public Pantry(CookItSubscription cookItSubscription) {
        this.cookItSubscription = cookItSubscription;
        initializeNewBatchOFreshFood();
        initializeExpiredFood();
        initiateConsumedFood();
    }

    public Queue<Map<FoodType, Food>> getAllFreshFood() {
        return allFreshFood;
    }

    public Map<FoodType, Food> getAllExpiredFood() {
        return allExpiredFood;
    }

    public Map<FoodType, Food> getAllConsumedFood() {
        return allConsumedFood;
    }

    private void initializeNewBatchOFreshFood() {
        currentTurnFoodBatch = new HashMap<>();
        Food freshNewBurgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food freshNewSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food freshNewWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        currentTurnFoodBatch.put(FoodType.BURGER, freshNewBurgersOfQuantityZero);
        currentTurnFoodBatch.put(FoodType.SALAD, freshNewSaladsOfQuantityZero);
        currentTurnFoodBatch.put(FoodType.WATER, freshNewWaterOfQuantityZero);
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

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        return giveExactOrMostPossibleFoodDesired(FoodType.BURGER, requestedBurgerQuantity);
    }

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        return giveExactOrMostPossibleFoodDesired(FoodType.SALAD, requestedSaladQuantity);
    }

    @Override
    public int giveExactOrMostPossibleWaterDesired(int requestedWaterQuantity) {
        return giveExactOrMostPossibleFoodDesired(FoodType.WATER, requestedWaterQuantity);
    }

    public void addOrderedFoodToCurrentTurnFoodBatch(Map<FoodType, Food> orderedFood) {
        currentTurnFoodBatch.forEach((foodTypeInCurrentBatch, foodInCurrentBatch)->{
            Food foodToAddToTheBatch = orderedFood.get(foodTypeInCurrentBatch);
            try {
                foodInCurrentBatch.increaseQuantity(foodToAddToTheBatch);
            } catch(FoodTypesNotMatchingException exception) {
                exception.printStackTrace();
            }
        });
    }

    public void addCurrentTurnFoodBatchToFreshFood() {
        Map<FoodType, Food> foodFromCookIt = cookItSubscription.provideFood();
        addOrderedFoodToCurrentTurnFoodBatch(foodFromCookIt);
        allFreshFood.add(currentTurnFoodBatch);

        initializeNewBatchOFreshFood();
    }

    public void removeExpiredFoodFromFreshFood() {
        for(Iterator<Map<FoodType, Food>> iterator = allFreshFood.iterator(); iterator.hasNext(); ) {
            Map<FoodType, Food> foodBatchOfOneTurn = iterator.next();
            List<FoodType> allFoodToRemoveFromThisBatch = obtainAllExpiredFoodOfOneTurnBatch(foodBatchOfOneTurn);
            allFoodToRemoveFromThisBatch.forEach(foodBatchOfOneTurn::remove);
        }
    }

    public void incrementFreshFoodAges() {
        allFreshFood.forEach((foodBatchOfOneTurn)->
                foodBatchOfOneTurn.forEach((foodType, food)->
                        food.incrementAgeByOne()));
    }

    private int giveExactOrMostPossibleFoodDesired(FoodType foodType, int requestedFoodQuantity) {
        int quantityOfFoodToProvide = quantityOfFoodToProvide(foodType, requestedFoodQuantity);
        moveFreshFoodToConsumedFood(quantityOfFoodToProvide, foodType);
        return quantityOfFoodToProvide;
    }

    private List<FoodType> obtainAllExpiredFoodOfOneTurnBatch(Map<FoodType, Food> foodBatchOfOneTurn) {
        List<FoodType> allExpiredFoodFromTheTurnBatch = new ArrayList<>();
        foodBatchOfOneTurn.forEach(((foodType, food)->{
            if(food.isExpired()) {
                try {
                    allExpiredFood.get(foodType).increaseQuantity(food);
                } catch(FoodTypesNotMatchingException exception) {
                    exception.printStackTrace();
                }
                allExpiredFoodFromTheTurnBatch.add(foodType);
            }
        }));

        return allExpiredFoodFromTheTurnBatch;
    }

    private int quantityOfFoodToProvide(FoodType foodType, int requestedFoodQuantity) {
        int availableFoodQuantity = 0;

        for(Map<FoodType, Food> foodBatchOfATurn : allFreshFood) {
            if(foodBatchOfATurn.containsKey(foodType))
                availableFoodQuantity += foodBatchOfATurn.get(foodType).quantity();
        }

        return Math.min(requestedFoodQuantity, availableFoodQuantity);
    }

    private void moveFreshFoodToConsumedFood(int quantityToMove, FoodType foodType) {
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
            try {
                allConsumedFood.get(foodType).increaseQuantity(removedFood);
            } catch(FoodTypesNotMatchingException exception) {
                exception.printStackTrace();
            }
        }
        return quantityMoved;
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
        initializeExpiredFood();
        initiateConsumedFood();
    }
}
