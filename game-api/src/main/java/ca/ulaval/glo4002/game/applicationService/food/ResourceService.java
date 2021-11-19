package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.*;

import java.util.List;

public class ResourceService {

    private final FoodHistory foodHistory;
    private final Pantry pantry;
    private final Game game;

    public ResourceService(FoodHistory foodHistory, Pantry pantry, Game game) {
        this.foodHistory = foodHistory;
        this.pantry = pantry;
        this.game = game;
    }

    public void addFood(List<Food> food) {
        game.addFood(food);
    }

    public FoodHistory getFoodQuantitySummary() {
        foodHistory.computeFreshFoodQuantities(pantry.getAllFreshFood());
        return pantry.getFoodHistory();
    }
}
