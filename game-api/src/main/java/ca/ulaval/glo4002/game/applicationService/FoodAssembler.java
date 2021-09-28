package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;

import java.util.HashMap;
import java.util.Map;

public class FoodAssembler {

    public Map<FoodType, Food> create(FoodDTO foodDTO) {
        Map<FoodType, Food> food = new HashMap<>();

        Food burgers = new Food(FoodType.BURGER, foodDTO.qtyBurger);
        Food salads = new Food(FoodType.SALAD, foodDTO.qtySalad);
        Food water = new Food(FoodType.WATER, foodDTO.qtySalad);

        food.put(FoodType.BURGER, burgers);
        food.put(FoodType.SALAD, salads);
        food.put(FoodType.WATER, water);

        return food;
    }
}
