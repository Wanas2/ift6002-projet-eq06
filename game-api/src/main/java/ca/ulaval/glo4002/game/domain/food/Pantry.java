package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Pantry implements FoodStorage {

    private final FoodProvider foodProvider;
    private List<Food> currentTurnFoodBatch = new ArrayList<>();
    private Queue<List<Food>> allFreshFood = new LinkedList<>();
    private final List<Food> allExpiredFood = new ArrayList<>();
    private final List<Food> allConsumedFood = new ArrayList<>();

    public Pantry(FoodProvider foodProvider) {
        this.foodProvider = foodProvider;
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

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        FoodType foodTypeToProvide = FoodType.BURGER;
        int quantityOfFoodToProvide = quantityOfFoodToProvide(foodTypeToProvide, requestedBurgerQuantity);



        return quantityOfFoodToProvide;
    }

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        return 1;
    }

    @Override
    public int giveExactOrMostPossibleWaterDesired(int requestedWaterQuantity) {
        return 1;
    }

    public void addCurrentTurnFoodBatchToFreshFood() {
    }

    public void removeExpiredFoodFromFreshFood() {
    }

    private int giveExactOrMostPossibleFoodDesired(FoodType foodType, int requestedFoodQuantity) {
        return 1;
    }

    public void obtainNewlyOrderedFood(List<Food> orderedFood) {
        FoodState newFoodState = FoodState.FRESH;

        for(FoodType foodType : FoodType.values()) {
            Optional<Food> foodOrderedOfMatchingType = orderedFood.stream()
                    .filter(food -> food.getType().equals(foodType))
                    .findFirst();

            foodOrderedOfMatchingType.ifPresent(food ->
                    incrementFoodQuantityOfOneType(food, currentTurnFoodBatch, foodType, newFoodState));
        }
    }

    public void storeFood() {
        List<Food> newBatchOfFood = new ArrayList<>();
        FoodState newFoodState = FoodState.FRESH;
        List<Food> foodFromProvider = foodProvider.provideFood();

        for (FoodType foodType : FoodType.values()) {
            Optional<Food> foodFromCurrentTurnFoodBatch = currentTurnFoodBatch.stream()
                    .filter(food -> food.getType().equals(foodType))
                    .findFirst();

            Optional<Food> foodFromFoodProvider = foodFromProvider.stream()
                    .filter(food -> food.getType().equals(foodType))
                    .findFirst();

            foodFromCurrentTurnFoodBatch.ifPresent(food
                    -> incrementFoodQuantityOfOneType(food, newBatchOfFood, foodType, newFoodState));

            foodFromFoodProvider.ifPresent(food
                    -> incrementFoodQuantityOfOneType(food, newBatchOfFood, foodType, newFoodState));
        }

        allFreshFood.add(newBatchOfFood);
        currentTurnFoodBatch = new ArrayList<>();
    }

    public void incrementFreshFoodAges() {
        allFreshFood.forEach(foodBatch ->
                        foodBatch.forEach(food -> food.incrementAgeByOne()));
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
    }

    private void incrementFoodQuantityOfOneType(Food foodToAdd, List<Food> foodToAddTo,
                                                FoodType requiredFoodType, FoodState requiredFoodState) {
        Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(requiredFoodType);

        if(!containFoodOfFoodTypeAndState(foodToAddTo, requiredFoodType, requiredFoodState)) {
            foodToAddTo.add(new Food(requiredFoodType, 0));
        }

        Optional<Food> foodOfRequiredTypeToAddTo = foodToAddTo.stream()
                .filter(foodTypeFilter).findFirst();
        foodOfRequiredTypeToAddTo.ifPresent((food -> {
            try {
                foodOfRequiredTypeToAddTo.get().increaseQuantity(foodToAdd);
            } catch (FoodTypesNotMatchingException e) {
                e.printStackTrace();
            }
        }));
    }

    private boolean containFoodOfFoodTypeAndState(List<Food> food, FoodType requiredFoodType,
                                                  FoodState requiredFoodState) {
        Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(requiredFoodType);
        Predicate<Food> foodStateFilter = foodFiltered -> foodFiltered.getState().equals(requiredFoodState);

        Optional<Food> matchingFood = food.stream().
                filter(foodTypeFilter.and(foodStateFilter)).
                findFirst();

        return matchingFood.isPresent();
    }

    private int quantityOfFoodToProvide(FoodType foodType, int requestedFoodQuantity) {
        int availableFoodQuantity = allFreshFood.stream()
                .flatMap(foodBatch -> foodBatch.stream())
                .filter(food -> food.getType().equals(foodType))
                .mapToInt(Food::quantity).sum();

        return Math.min(requestedFoodQuantity, availableFoodQuantity);
    }
}
