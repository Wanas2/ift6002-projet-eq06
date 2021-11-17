package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.*;

import java.util.List;
import java.util.Map;

public class ResourceService {

    private final FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private final Pantry pantry;
    private final Game game;

    public ResourceService(FoodQuantitySummaryCalculator foodQuantitySummaryCalculator, Pantry pantry, Game game) {
        this.foodQuantitySummaryCalculator = foodQuantitySummaryCalculator;
        this.pantry = pantry;
        this.game = game;
    }

    public void addFood(List<Food> food) {
        game.addFood(food);
    }

    public Map<FoodState, Map<FoodType, Integer>> getFoodQuantitySummary() {
        foodQuantitySummaryCalculator.computeFreshFoodQuantitySummary(pantry.getAllFreshFood());
        return foodQuantitySummaryCalculator.getAllFoodQuantities();
    }
}
