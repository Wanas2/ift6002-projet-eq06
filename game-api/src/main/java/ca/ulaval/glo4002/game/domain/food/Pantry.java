package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;

public class Pantry implements FoodStorage {

    private List<Food> currentTurnFoodBatch = new ArrayList<>();
    private List<Food> allFreshFoods = new LinkedList<>();
    private final FoodProvider foodProvider;
    private final FoodDistributor foodDistributor;
    private final WaterSplitter waterSplitter;
    private final FoodHistory foodHistory;

    public Pantry(FoodProvider foodProvider, FoodDistributor foodDistributor, WaterSplitter waterSplitter,
                  FoodHistory foodHistory) {
        this.foodProvider = foodProvider;
        this.foodDistributor = foodDistributor;
        this.waterSplitter = waterSplitter;
        this.foodHistory = foodHistory;
    }

    public List<Food> getAllFreshFoods() {
        return allFreshFoods;
    }

    public void obtainNewlyOrderedFoods(List<Food> orderedFoods) {
        int defaultAgeForNewFood = 0;

        for(FoodType foodType : FoodType.values()) {
            Predicate<Food> foodOfCurrentType = foodFiltered -> foodFiltered.getType().equals(foodType);
            Optional<Food> foodOrderedOfMatchingType = orderedFoods.stream()
                    .filter(foodOfCurrentType)
                    .findFirst();

            foodOrderedOfMatchingType.ifPresent(food ->
                    addToMatchingFood(food, currentTurnFoodBatch, foodType, defaultAgeForNewFood));
        }
    }

    public void storeFood() {
        int newFoodAge = 0;
        List<Food> newBatchOfFood = new ArrayList<>();
        List<Food> foodFromProvider = foodProvider.provideFood();

        for (FoodType foodType : FoodType.values()) {
            Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(foodType);

            Optional<Food> foodFromCurrentTurnFoodBatch = currentTurnFoodBatch.stream()
                    .filter(foodTypeFilter)
                    .findFirst();
            foodFromCurrentTurnFoodBatch.ifPresent(food
                    -> addToMatchingFood(food, newBatchOfFood, foodType, newFoodAge));

            Optional<Food> foodFromFoodProvider = foodFromProvider.stream()
                    .filter(foodTypeFilter)
                    .findFirst();
            foodFromFoodProvider.ifPresent(food
                    -> addToMatchingFood(food, newBatchOfFood, foodType, newFoodAge));
        }

        allFreshFoods.addAll(newBatchOfFood);
        currentTurnFoodBatch = new ArrayList<>();
    }

    public void incrementFreshFoodAges() {
        List<Food> allFoodsToRemove = new ArrayList<>();
        for(Food food: allFreshFoods) {
            food.incrementAgeByOne();
            if(food.isExpired()) {
                foodHistory.increaseExpiredQuantity(food);
                allFoodsToRemove.add(food);
            }
        }
        allFreshFoods.removeAll(allFoodsToRemove);
    }

    public void splitWaterInTwo() {
        waterSplitter.splitWater(allFreshFoods);
    }

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        return foodDistributor
                .distributeExactOrMostPossible(FoodType.SALAD, allFreshFoods, requestedSaladQuantity, foodHistory);
    }

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        return foodDistributor
                .distributeExactOrMostPossible(FoodType.BURGER, allFreshFoods, requestedBurgerQuantity, foodHistory);
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToCarnivorous(int requestedWaterQuantity) {
        List<Food> waterForCarnivorous = waterSplitter.getWaterForCarnivorous();
        return foodDistributor
                .distributeExactOrMostPossible(FoodType.WATER, waterForCarnivorous, requestedWaterQuantity, foodHistory);
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToHerbivorous(int requestedWaterQuantity) {
        List<Food> waterForHerbivorous = waterSplitter.getWaterForHerbivorous();
        return foodDistributor
                .distributeExactOrMostPossible(FoodType.WATER, waterForHerbivorous, requestedWaterQuantity, foodHistory);
    }

    public void mergeWater() {
        waterSplitter.mergeWater(allFreshFoods);
    }

    public void reset() {
        allFreshFoods = new LinkedList<>();
        foodHistory.reset();
    }

    public FoodHistory getFoodHistory() {
        foodHistory.computeFreshFoodQuantities(allFreshFoods);
        return foodHistory;
    }

    private void addToMatchingFood(Food foodToAdd, List<Food> foodToAddTo, FoodType requiredFoodType,
                                   int requiredFoodAge) {
        Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(requiredFoodType);
        Predicate<Food> foodAgeFilter = foodFiltered -> foodFiltered.getAge() == requiredFoodAge;

        if(!containNewFoodOfFoodType(foodToAddTo, requiredFoodType)) {
            foodToAddTo.add(new Food(requiredFoodType, 0));
        }

        Optional<Food> foodOfRequiredTypeToAddTo = foodToAddTo.stream()
                .filter(foodTypeFilter.and(foodAgeFilter))
                .findFirst();

        foodOfRequiredTypeToAddTo.ifPresent((food -> {
            try {
                food.increaseQuantity(foodToAdd);
            } catch (FoodTypesNotMatchingException e) {
                e.printStackTrace();
            }
        }));
    }

    private boolean containNewFoodOfFoodType(List<Food> food, FoodType requiredFoodType) {
        int newFoodAge = 0;
        Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(requiredFoodType);
        Predicate<Food> foodAgeFilter = foodFiltered -> foodFiltered.getAge() == newFoodAge;

        Optional<Food> matchingFood = food.stream()
                .filter(foodTypeFilter.and(foodAgeFilter))
                .findFirst();

        return matchingFood.isPresent();
    }
}
