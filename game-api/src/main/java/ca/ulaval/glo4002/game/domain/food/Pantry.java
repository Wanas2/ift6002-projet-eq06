package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;

public class Pantry implements FoodStorage {

    private List<Food> currentTurnFoodBatch = new ArrayList<>();
    private List<Food> allFreshFood = new LinkedList<>();
    private List<Food> waterForCarnivorous = new LinkedList<>();
    private List<Food> waterForHerbivorous = new LinkedList<>();
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

    public List<Food> getWaterForCarnivorous() {
        return waterForCarnivorous;
    }

    public List<Food> getWaterForHerbivorous() {
        return waterForHerbivorous;
    }

    public List<Food> getAllFreshFood() {
        return allFreshFood;
    }

    public void obtainNewlyOrderedFood(List<Food> orderedFood) {
        int newFoodAge = 0;

        for(FoodType foodType : FoodType.values()) {
            Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(foodType);
            Optional<Food> foodOrderedOfMatchingType = orderedFood.stream()
                    .filter(foodTypeFilter)
                    .findFirst();

            foodOrderedOfMatchingType.ifPresent(food ->
                    addToMatchingFood(food, currentTurnFoodBatch, foodType, newFoodAge));
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

        allFreshFood.addAll(newBatchOfFood);
        currentTurnFoodBatch = new ArrayList<>();
    }

    public void incrementFreshFoodAges() {
        List<Food> allFoodsToRemove = new ArrayList<>();
        for(Food food: allFreshFood) {
            food.incrementAgeByOne();
            if(food.isExpired()) {
                foodHistory.increaseExpiredQuantity(food);
                allFoodsToRemove.add(food);
            }
        }
        allFreshFood.removeAll(allFoodsToRemove);
    }

    public void splitWaterInTwo() {
        waterSplitter.splitWater(allFreshFood);
    }

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        return foodDistributor
                .distributeExactOrMostPossible(FoodType.SALAD, allFreshFood, requestedSaladQuantity, foodHistory);
    }

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        return foodDistributor
                .distributeExactOrMostPossible(FoodType.BURGER, allFreshFood, requestedBurgerQuantity, foodHistory);
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
        waterSplitter.mergeWater(allFreshFood);
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
        foodHistory.reset();
    }

    public FoodHistory getFoodHistory() {
        foodHistory.computeFreshFoodQuantities(allFreshFood);
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
