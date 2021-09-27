package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;

import java.util.Map;

public class PantryService {

    private final FoodAssembler foodAssembler;
    private final Pantry pantry;

    public PantryService(FoodAssembler foodAssembler, Pantry pantry) {
        this.foodAssembler = foodAssembler;
        this.pantry = pantry;
    }

    public void orderFood(FoodDTO foodDTO) {
        Map<FoodType, Food> food = foodAssembler.create(foodDTO);
        pantry.orderFood(food);
    }
}
