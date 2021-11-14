package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Pantry implements FoodStorage {

    private final FoodProvider foodProvider;
    private List<Food> currentTurnFoodBatch = new ArrayList<>();
    private List<Food> allFreshFood = new LinkedList<>();
    private List<Food> waterForCarnivorous = new LinkedList<>();
    private List<Food> waterForHerbivorous = new LinkedList<>();
    private Map<Integer, Integer> storedWater= new HashMap<>();
    private final Map<FoodType, Integer> expiredFoodQuantities = new HashMap<>();
    private final Map<FoodType, Integer> consumedFoodQuantities = new HashMap<>();

    public Pantry(FoodProvider foodProvider) {
        this.foodProvider = foodProvider;
        initializeExpiredFoodQuantities();
        initiateConsumedFoodQuantities();
    }

    public List<Food> getWaterForCarnivorous() {
        return waterForCarnivorous;
    }

    public List<Food> getWaterForHerbivorous() {
        return waterForHerbivorous;
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
        List<Food> foodToRemove = new ArrayList<>();
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
                foodToRemove.add(food);
            }
        }
        allBurgers.removeAll(foodToRemove);
        allFreshFood.addAll(allBurgers);

        return totalFoodGiven;
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToHerbivorous(int requestedWaterQuantity) {
        FoodType foodTypeToProvide = FoodType.WATER;
        int remainingFooQuantityToProvide = requestedWaterQuantity;
        int totalFoodGiven = 0;

        List <Food> allBurgers = waterForHerbivorous.stream()
                .filter(food -> food.getType().equals(foodTypeToProvide))
                .collect(Collectors.toList());
        waterForHerbivorous.removeAll(allBurgers);
        List<Food> allFoodToRemove = new ArrayList<>();
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
                allFoodToRemove.add(food);
            }
        }
        allBurgers.removeAll(allFoodToRemove);
        waterForHerbivorous.addAll(allBurgers);

        return totalFoodGiven;
    }

    @Override
    public int giveExactOrMostPossibleSaladDesired(int requestedSaladQuantity) {
        FoodType foodTypeToProvide = FoodType.SALAD;
        int remainingFooQuantityToProvide = requestedSaladQuantity;
        int totalFoodGiven = 0;

        List <Food> allBurgers = allFreshFood.stream()
                .filter(food -> food.getType().equals(foodTypeToProvide))
                .collect(Collectors.toList());
        allFreshFood.removeAll(allBurgers);
        List<Food> foodToRemove = new ArrayList<>();
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
                foodToRemove.add(food);
            }
        }
        allBurgers.removeAll(foodToRemove);
        allFreshFood.addAll(allBurgers);

        return totalFoodGiven;
    }

    @Override
    public int giveExactOrMostPossibleWaterDesiredToCarnivorous(int requestedWaterQuantity) {
        FoodType foodTypeToProvide = FoodType.WATER;
        int remainingFooQuantityToProvide = requestedWaterQuantity;
        int totalFoodGiven = 0;

        List <Food> allBurgers = waterForCarnivorous.stream()
                .filter(food -> food.getType().equals(foodTypeToProvide))
                .collect(Collectors.toList());
        waterForCarnivorous.removeAll(allBurgers);
        List<Food> allFoodToRemove = new ArrayList<>();
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
                allFoodToRemove.add(food);
            }
        }
        allBurgers.removeAll(allFoodToRemove);
        waterForCarnivorous.addAll(allBurgers);

        return totalFoodGiven;
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

    public void splitWater() {
        List<Food> allFoodToRemove = new ArrayList<>();
        for(Food food: allFreshFood){
            if(food.getType() == FoodType.WATER) {
                allFoodToRemove.add(food);
                int splitQuantity = splitIn2(food.quantity(), food.getAge());
                Food food1 = new Food(FoodType.WATER, splitQuantity, food.getAge());
                Food food2 = new Food(FoodType.WATER, splitQuantity, food.getAge());
                waterForCarnivorous.add(food1);
                waterForHerbivorous.add(food2);
            }
        }
        allFreshFood.removeAll(allFoodToRemove);
    }

    private int splitIn2(int quantity, int age){
        if(quantity%2!=0){
            storedWater.put(age, quantity%2);
        }
        return quantity/2;
    }

    public void mergeWater(){
        List<Food> foodsToRemove = new ArrayList<>();
        for(Food food: waterForHerbivorous){
            boolean hasBeenMerged = false;
            int quantityToAdd = 0;
            int foodAge = food.getAge();
            Food foodToRemove = null;
            for(Food food2: waterForCarnivorous){
                if (food2.getAge()==foodAge){
                    quantityToAdd+=food2.quantity();
                    if(storedWater.containsKey(food.getAge())){
                        quantityToAdd+=storedWater.get(foodAge);
                        storedWater.remove(foodAge);
                    }
                    food.increaseQuantity(quantityToAdd);
                    allFreshFood.add(food);
                    hasBeenMerged=true;
                    foodToRemove=food2;
                    break;
                }
            }
            if(foodToRemove!=null)
                waterForCarnivorous.remove(foodToRemove);
            if(!hasBeenMerged){
                allFreshFood.add(food);
            }
            foodsToRemove.add(food);
        }
        waterForHerbivorous.removeAll(foodsToRemove);
        foodsToRemove = new ArrayList<>();
        for(Food food:waterForCarnivorous){
            if(storedWater.containsKey(food.getAge())){
                food.increaseQuantity(storedWater.get(food.getAge()));
                storedWater.remove(food.getAge());
            }
            allFreshFood.add(food);
            foodsToRemove.add(food);
        }
        waterForCarnivorous.removeAll(foodsToRemove);
        storedWater.forEach((age, quantity)->allFreshFood.add(new Food(FoodType.WATER, quantity, age)));
        storedWater = new HashMap<>();
    }

    public void incrementFreshFoodAges() {
        List<Food> allFoodsToRemove = new ArrayList<>();
        for(Food food: allFreshFood) {
            food.incrementAgeByOne();
            int currentExpiredFoodQuantity = expiredFoodQuantities.get(food.getType());
            if(food.isExpired()){
                int newExpiredFoodQuantity = currentExpiredFoodQuantity + food.quantity();
                expiredFoodQuantities.put(food.getType(), newExpiredFoodQuantity);
                allFoodsToRemove.add(food);
            }
        }
        allFreshFood.removeAll(allFoodsToRemove);
    }

    public void reset() {
        allFreshFood = new LinkedList<>();
        initiateConsumedFoodQuantities();
        initializeExpiredFoodQuantities();
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
