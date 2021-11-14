package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;

import java.util.HashMap;
import java.util.Map;

public class FoodAssembler {

    public Map<FoodType, Food> fromDTO(int qtyBurger, int qtySalad, int qtyWater) {
        Map<FoodType, Food> food = new HashMap<>();

        Food burgers = new Food(FoodType.BURGER, qtyBurger);
        Food salads = new Food(FoodType.SALAD, qtySalad);
        Food water = new Food(FoodType.WATER, qtyWater);

        food.put(FoodType.BURGER, burgers);
        food.put(FoodType.SALAD, salads);
        food.put(FoodType.WATER, water);

        return food;
    }

    public FoodDTO toDTO(Map<FoodType, Integer> food) {
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.qtyBurger = food.get(FoodType.BURGER);
        foodDTO.qtySalad = food.get(FoodType.SALAD);
        foodDTO.qtyWater = food.get(FoodType.WATER);
        return foodDTO;
    }
}
