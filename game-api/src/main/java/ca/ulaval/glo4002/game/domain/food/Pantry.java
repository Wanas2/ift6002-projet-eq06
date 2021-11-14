package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Pantry implements FoodStorage {

    private final FoodProvider foodProvider;
    private List<Food> currentTurnFoodBatch = new ArrayList<>();
    private List<Food> allFreshFood = new LinkedList<>();
    private final Map<FoodType, Integer> expiredFoodQuantities = new HashMap<>();
    private final Map<FoodType, Integer> consumedFoodQuantities = new HashMap<>();

    public Pantry(FoodProvider foodProvider) {
        this.foodProvider = foodProvider;
        initializeExpiredFoodQuantities();
        initiateConsumedFoodQuantities();
    }

    private void initializeExpiredFoodQuantities() {
        expiredFoodQuantities.put(FoodType.BURGER, 0);
        expiredFoodQuantities.put(FoodType.SALAD, 0);
        expiredFoodQuantities.put(FoodType.WATER, 0);
    }

    private void initiateConsumedFoodQuantities() {
        consumedFoodQuantities.put(FoodType.BURGER, 0);
        consumedFoodQuantities.put(FoodType.SALAD, 0);
        consumedFoodQuantities.put(FoodType.WATER, 0);
    }

    public List<Food> getAllFreshFood() {
        return allFreshFood;
    }

    public Map<FoodType, Integer> getExpiredFoodQuantities() {
        return expiredFoodQuantities;
    }

    public Map<FoodType, Integer> getConsumedFoodQuantities() {
        return consumedFoodQuantities;
    }

    @Override
    public int giveExactOrMostPossibleBurgerDesired(int requestedBurgerQuantity) {
        FoodType foodTypeToProvide = FoodType.BURGER;
        int remainingFooQuantityToProvide = requestedBurgerQuantity;
        int totalFoodGiven = 0;

        List <Food> allBurgers = allFreshFood.stream()
                .filter(food -> food.getType().equals(foodTypeToProvide))
                .collect(Collectors.toList());
        allFreshFood.removeAll(allBurgers);

        for(Food food : allBurgers) {
            int currentConsumedFoodQuantity = consumedFoodQuantities.get(foodTypeToProvide);

            if(remainingFooQuantityToProvide <= food.quantity()) {
                food.decreaseQuantity(remainingFooQuantityToProvide);
                int newConsumedFoodQuantity = currentConsumedFoodQuantity + remainingFooQuantityToProvide;
                consumedFoodQuantities.put(food.getType(), newConsumedFoodQuantity);
                remainingFooQuantityToProvide = 0;
                totalFoodGiven += remainingFooQuantityToProvide;
                break;
            } else {
                int newConsumedFoodQuantity = currentConsumedFoodQuantity + food.quantity();
                consumedFoodQuantities.put(food.getType(), newConsumedFoodQuantity);
                totalFoodGiven += food.quantity();
                allBurgers.remove(food);
            }
        }

        allFreshFood.addAll(allBurgers);

        return totalFoodGiven;
    }

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        return 1;
    }

    @Override
    public int giveExactOrMostPossibleWaterDesired(int requestedWaterQuantity) {
        return 1;
    }

    private int giveExactOrMostPossibleFoodDesired(FoodType foodType, int requestedFoodQuantity) {
        return 1;
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
        for(Food food: allFreshFood) {
            food.incrementAgeByOne();

            int currentExpiredFoodQuantity = expiredFoodQuantities.get(food.getType());

            if(food.isExpired()){
                int newExpiredFoodQuantity = currentExpiredFoodQuantity + food.quantity();
                expiredFoodQuantities.put(food.getType(), newExpiredFoodQuantity);
                allFreshFood.remove(food);
            }
        }
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
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

    private boolean containNewFoodOfFoodType(List<Food> food, FoodType requiredFoodType) { // Todo To rename
        int newFoodAge = 0;
        Predicate<Food> foodTypeFilter = foodFiltered -> foodFiltered.getType().equals(requiredFoodType);
        Predicate<Food> foodAgeFilter = foodFiltered -> foodFiltered.getAge() == newFoodAge;

        Optional<Food> matchingFood = food.stream()
                .filter(foodTypeFilter.and(foodAgeFilter))
                .findFirst();

        return matchingFood.isPresent();
    }
}
