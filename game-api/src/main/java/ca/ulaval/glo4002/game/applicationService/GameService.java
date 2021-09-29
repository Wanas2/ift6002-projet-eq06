package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class GameService {

    private final TurnAssembler turnAssembler;
    private final FoodAssembler foodAssembler;
    private final Game game;

    public GameService(Game game, TurnAssembler turnAssembler, FoodAssembler foodAssembler) {
        this.turnAssembler = turnAssembler;
        this.foodAssembler = foodAssembler;
        this.game = game;
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

    public void reset() {
        game.reset();
    }
}
