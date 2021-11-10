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
        int quantityOfFoodToProvide = quantityOfFoodToProvide(FoodType.BURGER, requestedBurgerQuantity);

        Queue<Food> availableBurgers = allFreshFood.stream().
                flatMap(foodBatch -> foodBatch.stream()).
                filter(food -> food.getType().equals(FoodType.BURGER)).
                collect(Collectors.toCollection(LinkedList::new));

        availableBurgers.forEach(burger -> {
//            burger.decreaseQuantity(); // Todo continue from here
        });


        return 1;
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
            incrementFoodQuantityOfOneType(orderedFood, currentTurnFoodBatch, foodType, newFoodState);
        }
    }

    public void storeFood() {
        List<Food> newBatchOfFood = new ArrayList<>();
        FoodState newFoodState = FoodState.FRESH;
        List<Food> foodFromProvider = foodProvider.provideFood();

        for (FoodType foodType : FoodType.values()) {
            incrementFoodQuantityOfOneType(currentTurnFoodBatch, newBatchOfFood, foodType, newFoodState);
            incrementFoodQuantityOfOneType(foodFromProvider, newBatchOfFood, foodType, newFoodState);
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

    private void incrementFoodQuantityOfOneType(List<Food> foodToAdd, List<Food> foodToAddTo,
                                                FoodType requiredFoodType, FoodState requiredFoodState) {
        Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(requiredFoodType);

        if(!containFoodOfFoodTypeAndState(foodToAddTo, requiredFoodType, requiredFoodState)) {
            foodToAddTo.add(new Food(requiredFoodType, 0));
        }

        Optional<Food> foodOfRequiredTypeToAddTo = foodToAddTo.stream().
                filter(foodTypeFilter).findFirst();

        Optional<Food> foodOfRequiredTypeToAdd = foodToAdd.stream().
                filter(food -> food.getType().equals(requiredFoodType)).
                findFirst();

        if(foodOfRequiredTypeToAdd.isPresent()) {
            try {
                foodOfRequiredTypeToAddTo.get().increaseQuantity(foodOfRequiredTypeToAdd.get());
            } catch (FoodTypesNotMatchingException e) {
                e.printStackTrace();
            }
        }
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
        List<Food> availableBurgers = allFreshFood.stream().
                flatMap(foodBatch -> foodBatch.stream()).
                filter(food -> food.getType().equals(foodType)).
                collect(Collectors.toList());

        int availableFoodQuantity =  availableBurgers.stream().mapToInt(Food::quantity).sum();

        return Math.min(requestedFoodQuantity, availableFoodQuantity);
    }
}
