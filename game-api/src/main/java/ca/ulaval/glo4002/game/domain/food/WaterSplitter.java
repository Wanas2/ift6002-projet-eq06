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
            int splitQuantityOfWater = splitBatchOfWaterInTwo(batchOfWater);
            Food food1 = new Food(FoodType.WATER, splitQuantityOfWater, batchOfWater.getAge());
            Food food2 = new Food(FoodType.WATER, splitQuantityOfWater, batchOfWater.getAge());
            waterForCarnivorous.add(food1);
            waterForHerbivorous.add(food2);
        }

        allFreshFood.removeAll(allWater);
    }

    public void mergeWater(List<Food> allFreshFood) {
        for(Food waterBatchForHerbivorous : waterForHerbivorous) {
            addWaterBatchForForCarnivorousIntoWaterBatchForHerbivorous(waterBatchForHerbivorous);
            int quantityOfWaterOfMatchingAgeLeftAfterSplit =
                    waterLeftOutAfterSplit.getOrDefault(waterBatchForHerbivorous.getAge(), 0); // Todo Verifier ceci
            waterBatchForHerbivorous.increaseQuantity(quantityOfWaterOfMatchingAgeLeftAfterSplit);
        }
        allFreshFood.addAll(waterForHerbivorous);

        waterLeftOutAfterSplit.forEach((age, quantity) ->
                allFreshFood.add(new Food(FoodType.WATER, quantity, age))); // Todo Verifier ceci

        reset(); // Todo C'est un bon nom?
        allFreshFood.sort(Comparator.comparing(Food::getAge).reversed());
    }

    private int splitBatchOfWaterInTwo(Food batchOfWater) {
        if(batchOfWater.quantity() % 2 != 0) {
            waterLeftOutAfterSplit.put(batchOfWater.getAge(), batchOfWater.quantity() % 2);
        }
        return batchOfWater.quantity() / 2;
    }

    private Optional<Food> getWaterBatchOfMatchingAge(List<Food> waterBatches, int requiredAge) {
        Predicate<Food> mustBeOfRequiredAge = foodFiltered -> foodFiltered.getAge() == requiredAge;

        return waterBatches.stream()
                .filter(mustBeOfRequiredAge)
                .findFirst();
    }

    private void addWaterBatchForForCarnivorousIntoWaterBatchForHerbivorous(Food waterBatchForHerbivorous) {
        Optional<Food> waterBatchForCarnivorous =
                getWaterBatchOfMatchingAge(waterForCarnivorous, waterBatchForHerbivorous.getAge());

        waterBatchForCarnivorous.ifPresent (
                waterBatch -> {
                    try {
                        waterBatchForHerbivorous.increaseQuantity(waterBatch);
                    } catch (FoodTypesNotMatchingException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void reset() {
        waterLeftOutAfterSplit = new HashMap<>();
        waterForCarnivorous = new LinkedList<>();
        waterForHerbivorous = new LinkedList<>();
    }
}
