package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodQuantitySummaryCalculator;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodSummaryDTO;

import java.util.Map;

public class ResourceService {

    private final FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private final Pantry pantry;
    private final Game game;
    private final FoodAssembler foodAssembler;
    private final FoodSummaryAssembler foodSummaryAssembler;

    public ResourceService(FoodQuantitySummaryCalculator foodQuantitySummaryCalculator, Pantry pantry, Game game,
                           FoodAssembler foodAssembler, FoodSummaryAssembler foodSummaryAssembler) {
        this.foodQuantitySummaryCalculator = foodQuantitySummaryCalculator;
        this.pantry = pantry;
        this.game = game;
        this.foodAssembler = foodAssembler;
        this.foodSummaryAssembler = foodSummaryAssembler;
    }

    public void addFood(FoodDTO foodDTO) {
        Map<FoodType, Food> food = foodAssembler.create(foodDTO);
        game.addFood(food);
    }

    public FoodSummaryDTO getFoodQuantitySummary() {
        Map<String, Map<FoodType, Integer>> allFoodSummary = foodQuantitySummaryCalculator.computeSummaries(pantry);

        return foodSummaryAssembler.createDTO(allFoodSummary, foodAssembler);
    }
}
