package ca.ulaval.glo4002.game.applicationService.turn;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;

public class TurnService {

    private final TurnAssembler turnAssembler;
    private final Game game;

    public TurnService(TurnAssembler turnAssembler, Game game) {
        this.turnAssembler = turnAssembler;
        this.game = game;
    }



    public TurnNumberDTO playTurn() {

        // Todo Créer Food avec un créateur
        Map<FoodType, Food> foods = new HashMap<>();
        int turnNumber = game.playTurn();

        // Todo Créer Dino avec un créateur

        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public void reset() {
        game.reset();
    }
}
