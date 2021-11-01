package ca.ulaval.glo4002.game.domain.food;

import java.util.*;

public class Pantry implements FoodStorage {

    private final CookItSubscription cookItSubscription;
    private List<Food> currentTurnFoodBatch;
    private Queue<List<Food>> allFreshFood = new LinkedList<>();
    private final List<Food> allExpiredFood = new ArrayList<>();
    private final List<Food> allConsumedFood = new ArrayList<>();

    public Pantry(CookItSubscription cookItSubscription) {
        this.cookItSubscription = cookItSubscription;
        initializeNewBatchOFreshFood();
        initializeExpiredFood();
        initiateConsumedFood();
    }

    public Queue<List<Food>> getAllFreshFood() {
        return allFreshFood;
    }

    public List<Food> getAllExpiredFood() {
        return allExpiredFood;
    }

    public List<Food> getAllConsumedFood() {
        return allConsumedFood;
    }

    private void initializeNewBatchOFreshFood() {
        currentTurnFoodBatch = new ArrayList<>();
        Food freshNewBurgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food freshNewSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food freshNewWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        currentTurnFoodBatch.add(freshNewBurgersOfQuantityZero);
        currentTurnFoodBatch.add(freshNewSaladsOfQuantityZero);
        currentTurnFoodBatch.add(freshNewWaterOfQuantityZero);
    }

    private void initializeExpiredFood() {
        Food expiredBurgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food expiredSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food expiredWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        allExpiredFood.add(expiredBurgersOfQuantityZero);
        allExpiredFood.add(expiredSaladsOfQuantityZero);
        allExpiredFood.add(expiredWaterOfQuantityZero);
    }

    private void initiateConsumedFood() {
        Food consumedBurgersOfQuantityZero = new Food(FoodType.BURGER, 0);
        Food consumedSaladsOfQuantityZero = new Food(FoodType.SALAD, 0);
        Food consumedWaterOfQuantityZero = new Food(FoodType.WATER, 0);
        allConsumedFood.add(consumedBurgersOfQuantityZero);
        allConsumedFood.add(consumedSaladsOfQuantityZero);
        allConsumedFood.add(consumedWaterOfQuantityZero);
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

    public void addOrderedFoodToCurrentTurnFoodBatch(List<Food> groupOfOrderedFood) {
        currentTurnFoodBatch.forEach(foodInCurrentBatch -> {
            try {
                Food food = groupOfOrderedFood.stream().filter(
                        orderedFood -> orderedFood.getType().equals(foodInCurrentBatch.getType()))
                        .findAny().orElse(null);  // Todo Comment gerer ceci

                foodInCurrentBatch.increaseQuantity(food);
            } catch (FoodTypesNotMatchingException e) {
                e.printStackTrace();
            }
        });
    }

    public void addCurrentTurnFoodBatchToFreshFood() {
        List<Food> foodFromCookIt = cookItSubscription.provideFood();
        addOrderedFoodToCurrentTurnFoodBatch(foodFromCookIt);
        allFreshFood.add(currentTurnFoodBatch);

        initializeNewBatchOFreshFood();
    }

    public void removeExpiredFoodFromFreshFood() { // Todo Bien tester cette methode. Avant elle faisait remove des FoodTypes
        for(Iterator<List<Food>> iterator = allFreshFood.iterator(); iterator.hasNext(); ) {
            List<Food> foodBatchOfOneTurn = iterator.next();
            List<Food> allFoodToRemoveFromThisBatch = obtainAllExpiredFoodOfOneTurnBatch(foodBatchOfOneTurn);
            allFoodToRemoveFromThisBatch.forEach(foodBatchOfOneTurn::remove);
        }
    }

    public void incrementFreshFoodAges() {
        allFreshFood.forEach((foodBatchOfOneTurn)->
                foodBatchOfOneTurn.forEach(food->
                        food.incrementAgeByOne()));
    }

    private int giveExactOrMostPossibleFoodDesired(FoodType foodType, int requestedFoodQuantity) {
        int quantityOfFoodToProvide = quantityOfFoodToProvide(foodType, requestedFoodQuantity);
        moveFreshFoodToConsumedFood(quantityOfFoodToProvide, foodType);
        return quantityOfFoodToProvide;
    }

    private List<Food> obtainAllExpiredFoodOfOneTurnBatch(List<Food> foodBatchOfOneTurn) {
        List<Food> allExpiredFoodFromTheTurnBatch = new ArrayList<>();
        foodBatchOfOneTurn.forEach((foodInTheBatch -> {
            if(foodInTheBatch.isExpired()) {
                try {
                    Food food = allExpiredFood.stream().
                            filter(expiredFood -> expiredFood.getType().equals(foodInTheBatch.getType())).
                            findAny().orElse(null);

                    food.increaseQuantity(foodInTheBatch);
                    allExpiredFoodFromTheTurnBatch.add(food);
                } catch(FoodTypesNotMatchingException exception) {
                    exception.printStackTrace();
                }
            }
        }));

        return allExpiredFoodFromTheTurnBatch;
    }

    private int quantityOfFoodToProvide(FoodType foodType, int requestedFoodQuantity) {
        int availableFoodQuantity = 0;

        for(List<Food> foodBatchOfATurn : allFreshFood) {
            Optional<Food> foodOfFoodBatch = foodBatchOfATurn.stream().
                    filter(food -> food.getType().equals(foodType)).
                    findFirst();

            if(foodOfFoodBatch.isPresent()) {
                int foodQuantityAvailableInBatch =  foodBatchOfATurn.stream().
                        filter(food -> food.getType().equals(foodType)).
                        findAny().orElse(null).quantity();

                availableFoodQuantity += foodQuantityAvailableInBatch;
            }
        }

        return Math.min(requestedFoodQuantity, availableFoodQuantity);
    }

    private void moveFreshFoodToConsumedFood(int quantityToMove, FoodType foodType) {
        int quantityMoved;
        for(List<Food> foodBatchOfATurn : allFreshFood) {
            Optional<Food> foodOfFoodBatch = foodBatchOfATurn.stream().
                    filter(food -> food.getType().equals(foodType)).
                    findFirst();

            if(foodOfFoodBatch.isPresent()) {
                quantityMoved = moveFoodFromOneBatchOfATurn(quantityToMove, foodBatchOfATurn, foodType);
                quantityToMove -= quantityMoved;
            }

            if(quantityToMove <= 0)
                break;
        }
    }

    private int moveFoodFromOneBatchOfATurn(int quantityToMove, List<Food> foodBatchOfATurn,
                                            FoodType foodType) {
        int quantityMoved = quantityToMove;
        Food foodInTheBatch = foodBatchOfATurn.stream().
                filter(food -> food.getType().equals(foodType)).
                findAny().orElse(null);

        if(quantityToMove <= foodInTheBatch.quantity()) {
            foodInTheBatch.decreaseQuantity(quantityToMove);

            Food foodConsumed = allConsumedFood.stream().
                    filter(food -> food.getType().equals(foodType)).
                    findAny().orElse(null);
            foodConsumed.increaseQuantity(quantityToMove);
        } else {
            quantityMoved = foodInTheBatch.quantity();
            foodBatchOfATurn.remove(foodInTheBatch);
            try {
                Food foodConsumed = allConsumedFood.stream().
                        filter(food -> food.getType().equals(foodType)).
                        findAny().orElse(null);
                foodConsumed.increaseQuantity(foodInTheBatch);
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
