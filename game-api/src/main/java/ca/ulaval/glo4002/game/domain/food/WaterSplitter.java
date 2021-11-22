package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WaterSplitter {

    private List<Food> waterForCarnivorous = new LinkedList<>();
    private List<Food> waterForHerbivorous = new LinkedList<>();
    private Map<Integer, Integer> waterLeftOutAfterSplit = new HashMap<>();

    public List<Food> getWaterForCarnivorous() {
        return waterForCarnivorous;
    }

    public List<Food> getWaterForHerbivorous() {
        return waterForHerbivorous;
    }

    public void splitWater(List<Food> allFreshFood) {
        Predicate<Food> mustBeWater = foodFiltered -> foodFiltered.getType().equals(FoodType.WATER);
        List<Food> allWater = allFreshFood.stream()
                .filter(mustBeWater)
                .collect(Collectors.toList());

        for(Food batchOfWater: allWater) {
            int splitQuantityOfWater = splitCurrentBatchOfWaterInTwo(batchOfWater);
            Food food1 = new Food(FoodType.WATER, splitQuantityOfWater, batchOfWater.getAge());
            Food food2 = new Food(FoodType.WATER, splitQuantityOfWater, batchOfWater.getAge());
            waterForCarnivorous.add(food1);
            waterForHerbivorous.add(food2);
        }

        allFreshFood.removeAll(allWater);
    }

    private int splitCurrentBatchOfWaterInTwo(Food batchOfWater) {
        if(batchOfWater.quantity() % 2 != 0) {
            waterLeftOutAfterSplit.put(batchOfWater.getAge(), batchOfWater.quantity() % 2);
        }
        return batchOfWater.quantity() / 2;
    }

    public void mergeWater(List<Food> allFreshFood) {
        List<Food> foodsToRemove = new ArrayList<>();
        for(Food food: waterForHerbivorous){
            boolean hasBeenMerged = false;
            int quantityToAdd = 0;
            int foodAge = food.getAge();
            Food foodToRemove = null;
            for(Food food2: waterForCarnivorous){
                if (food2.getAge()==foodAge){
                    quantityToAdd+=food2.quantity();
                    if(waterLeftOutAfterSplit.containsKey(food.getAge())){
                        quantityToAdd+= waterLeftOutAfterSplit.get(foodAge);
                        waterLeftOutAfterSplit.remove(foodAge);
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
        for(Food food: waterForCarnivorous){
            if(waterLeftOutAfterSplit.containsKey(food.getAge())){
                food.increaseQuantity(waterLeftOutAfterSplit.get(food.getAge()));
                waterLeftOutAfterSplit.remove(food.getAge());
            }
            allFreshFood.add(food);
            foodsToRemove.add(food);
        }
        waterForCarnivorous.removeAll(foodsToRemove);
        waterLeftOutAfterSplit.forEach((age, quantity) ->
                allFreshFood.add(new Food(FoodType.WATER, quantity, age)));
        waterLeftOutAfterSplit = new HashMap<>();
        allFreshFood.sort(Comparator.comparing(Food::getAge).reversed());
    }
}
