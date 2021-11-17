package ca.ulaval.glo4002.game.domain.food;

import java.util.*;
import java.util.stream.Collectors;

public class WaterSplitter {

    private List<Food> firstHalfOfWater = new LinkedList<>();
    private List<Food> secondHalfOfWater = new LinkedList<>();
    private Map<Integer, Integer> waterLeftOutAfterSplit = new HashMap<>();

    public Map<Integer, List<Food>> splitWater(List<Food> allFreshFood) {
        Map<Integer, List<Food>> splittedWater = new HashMap<>();

        List<Food> allFoodToRemove = new ArrayList<>();
        for(Food food: allFreshFood) {
            if(food.getType() == FoodType.WATER) {
                allFoodToRemove.add(food);
                int splitQuantity = splitIn2(food.quantity(), food.getAge());
                Food food1 = new Food(FoodType.WATER, splitQuantity, food.getAge());
                Food food2 = new Food(FoodType.WATER, splitQuantity, food.getAge());
                firstHalfOfWater.add(food1);
                secondHalfOfWater.add(food2);
            }
        }

        allFreshFood.removeAll(allFoodToRemove);
        splittedWater.put(1, firstHalfOfWater);
        splittedWater.put(2, secondHalfOfWater);


        /********************************************/
        List <Food> allWater = allFreshFood.stream()
                .filter(food -> food.getType().equals(FoodType.WATER))
                .collect(Collectors.toList());

        for(Food water : allWater) {

        }
//        int splitQuantity = splitIn2(food.quantity(), food.getAge());


        allFreshFood.removeAll(allWater);

        return splittedWater;
    }

    private int splitIn2(int quantity, int age) {
        if(quantity % 2 != 0) {
            waterLeftOutAfterSplit.put(age, quantity % 2);
        }
        return quantity / 2;
    }

    public void mergeWater(List<Food> allFreshFood) {
        List<Food> foodsToRemove = new ArrayList<>();
        for(Food food: secondHalfOfWater){
            boolean hasBeenMerged = false;
            int quantityToAdd = 0;
            int foodAge = food.getAge();
            Food foodToRemove = null;
            for(Food food2: firstHalfOfWater){
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
                firstHalfOfWater.remove(foodToRemove);
            if(!hasBeenMerged){
                allFreshFood.add(food);
            }
            foodsToRemove.add(food);
        }
        secondHalfOfWater.removeAll(foodsToRemove);
        foodsToRemove = new ArrayList<>();
        for(Food food: firstHalfOfWater){
            if(waterLeftOutAfterSplit.containsKey(food.getAge())){
                food.increaseQuantity(waterLeftOutAfterSplit.get(food.getAge()));
                waterLeftOutAfterSplit.remove(food.getAge());
            }
            allFreshFood.add(food);
            foodsToRemove.add(food);
        }
        firstHalfOfWater.removeAll(foodsToRemove);
        waterLeftOutAfterSplit.forEach((age, quantity) ->
                allFreshFood.add(new Food(FoodType.WATER, quantity, age)));
        waterLeftOutAfterSplit = new HashMap<>();
        allFreshFood.sort(Comparator.comparing(Food::getAge).reversed());
    }
}
