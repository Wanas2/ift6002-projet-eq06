package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Pantry implements FoodStorage {

    private final FoodProvider foodProvider;
    private final FoodDistributor foodDistributor;
    private final WaterDistributor waterDistributor;
    private final FoodHistory foodHistory;
    private List<Food> currentTurnFoodBatch = new ArrayList<>();
    private List<Food> allFreshFood = new LinkedList<>();
    private List<Food> waterForCarnivorous = new LinkedList<>();
    private List<Food> waterForHerbivorous = new LinkedList<>();

    public Pantry(FoodProvider foodProvider, FoodDistributor foodDistributor, WaterDistributor waterDistributor,
                  FoodHistory foodHistory) {
        this.foodProvider = foodProvider;
        this.foodDistributor = foodDistributor;
        this.waterDistributor = waterDistributor;
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

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        return foodDistributor.giveExactOrMostPossibleFoodType(FoodType.SALAD, allFreshFood, requestedSaladQuantity,
                foodHistory);
    }

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        return foodDistributor.giveExactOrMostPossibleFoodType(FoodType.BURGER, allFreshFood, requestedBurgerQuantity,
                foodHistory);
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToCarnivorous(int requestedWaterQuantity) {
        return waterDistributor.giveExactOrMostPossibleWater(waterForCarnivorous, requestedWaterQuantity, foodHistory);
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToHerbivorous(int requestedWaterQuantity) {
        return waterDistributor.giveExactOrMostPossibleWater(waterForHerbivorous, requestedWaterQuantity, foodHistory);
    }

    public void splitWaterInTwo() {
        waterDistributor.splitWater(allFreshFood);
    }

    public void mergeWater() {
        waterDistributor.mergeWater(allFreshFood);
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

    public void reset() {
        allFreshFood = new LinkedList<>();
        foodHistory.reset();
    }

    public FoodHistory getFoodHistory() {
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
