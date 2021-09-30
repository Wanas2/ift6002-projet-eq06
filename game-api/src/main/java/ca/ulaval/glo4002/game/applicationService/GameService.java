package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodSummaryDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class GameService {

    private final Game game;
    private final Pantry pantry;
    private final TurnAssembler turnAssembler;
    private final FoodAssembler foodAssembler;
    private final FoodSummaryAssembler foodSummaryAssembler;

    public GameService(Game game, Pantry pantry,TurnAssembler turnAssembler, FoodAssembler foodAssembler,
                       FoodSummaryAssembler foodSummaryAssembler) {
        this.game = game;
        this.pantry = pantry;
        this.turnAssembler = turnAssembler;
        this.foodAssembler = foodAssembler;
        this.foodSummaryAssembler = foodSummaryAssembler;
    }

    public void orderFood(FoodDTO foodDTO) {
        Map<FoodType, Food> food = foodAssembler.create(foodDTO);
        game.addFood(food);
    }

    // Todo Order Dinosaure ici


    public TurnNumberDTO playTurn() {
        int turnNumber = game.playTurn();
        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public FoodSummaryDTO getFoodQuantitySummary() {
        Map<String, Map<FoodType, Integer>> allFoodSummary = pantry.getFoodQuantitySummary();
        return foodSummaryAssembler.createDTO(allFoodSummary, foodAssembler);
    }

    public void reset() {
        game.reset();
    }
}
