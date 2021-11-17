package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Pantry implements FoodStorage {

    // Todo Enlever les mots carnivorous et herbivorous de Pantry
    private final FoodProvider foodProvider;
    private final FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private List<Food> currentTurnFoodBatch = new ArrayList<>();
    private List<Food> allFreshFood = new LinkedList<>();
    private List<Food> waterForCarnivorous = new LinkedList<>();
    private List<Food> waterForHerbivorous = new LinkedList<>();
    private Map<Integer, Integer> storedWater= new HashMap<>();

    public Pantry(FoodProvider foodProvider, FoodQuantitySummaryCalculator foodQuantitySummaryCalculator) {
        this.foodProvider = foodProvider;
        this.foodQuantitySummaryCalculator = foodQuantitySummaryCalculator;
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
        return giveExactOrMostPossibleFoodType(FoodType.SALAD,requestedSaladQuantity);
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToCarnivorous(int requestedWaterQuantity) {
        return giveExactOrMostPossibleWater(waterForCarnivorous,requestedWaterQuantity);
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToHerbivorous(int requestedWaterQuantity) {
        return giveExactOrMostPossibleWater(waterForHerbivorous,requestedWaterQuantity);
    }

    private int giveExactOrMostPossibleFoodType(FoodType foodTypeToProvide, int requestedQuantity) {
        int remainingFoodQuantityToProvide = requestedQuantity;
        int totalFoodGiven = 0;

        List <Food> allFood = allFreshFood.stream()
                .filter(food -> food.getType().equals(foodTypeToProvide))
                .collect(Collectors.toList());

        List<Food> allFoodToRemove = new ArrayList<>();
        for(Food food : allFood) {
            if(remainingFoodQuantityToProvide <= food.quantity()) {
                food.decreaseQuantity(remainingFoodQuantityToProvide);
                foodQuantitySummaryCalculator.
                        increaseConsumedQuantity(new Food(food.getType(), remainingFoodQuantityToProvide));
                totalFoodGiven += remainingFoodQuantityToProvide;
                break;
            } else {
                foodQuantitySummaryCalculator.increaseConsumedQuantity(food);
                totalFoodGiven += food.quantity();
                remainingFoodQuantityToProvide -= food.quantity();
                allFoodToRemove.add(food);
            }
        }

        allFreshFood.removeAll(allFoodToRemove);
        return totalFoodGiven;
    }

    private int giveExactOrMostPossibleWater(List<Food> waterContainer, int requestedQuantity) {
        int remainingFoodQuantityToProvide = requestedQuantity;
        int totalFoodGiven = 0;

        List<Food> allFoodToRemove = new ArrayList<>();
        for(Food food : waterContainer) {
            if(remainingFoodQuantityToProvide <= food.quantity()) {
                food.decreaseQuantity(remainingFoodQuantityToProvide);
                foodQuantitySummaryCalculator.
                        increaseConsumedQuantity(new Food(food.getType(), remainingFoodQuantityToProvide));
                totalFoodGiven += remainingFoodQuantityToProvide;
                break;
            } else {
                foodQuantitySummaryCalculator.increaseConsumedQuantity(food);
                totalFoodGiven += food.quantity();
                remainingFoodQuantityToProvide -= food.quantity();
                allFoodToRemove.add(food);
            }
        }

        waterContainer.removeAll(allFoodToRemove);
        return totalFoodGiven;
    }

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        return giveExactOrMostPossibleFoodType(FoodType.BURGER,requestedBurgerQuantity);
    }

    public void splitWaterInTwo() {
//        Map<Integer, List<Food>> splittedWater = waterSplitter.splitWater(allFreshFood);
//        waterForCarnivorous = splittedWater.get(1);
//        waterForHerbivorous = splittedWater.get(2);
    }

    public void mergeWater() {

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
                foodQuantitySummaryCalculator.increaseExpiredQuantity(food);
                allFoodsToRemove.add(food);
            }
        }
        allFreshFood.removeAll(allFoodsToRemove);
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
        foodQuantitySummaryCalculator.reset();
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
