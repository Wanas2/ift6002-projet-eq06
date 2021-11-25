package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.*;

import java.util.List;

public class ResourceService {

    private final Pantry pantry;
    private final Game game;

    public ResourceService(Pantry pantry, Game game) {
        this.pantry = pantry;
        this.game = game;
    }

    public void addFood(List<Food> food) {
        game.addFood(food);
    }

    public FoodHistory getFoodQuantitySummary() {
        return pantry.obtainFoodHistory();
    }
}
