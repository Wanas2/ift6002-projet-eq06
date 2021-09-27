package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.turn.AddDinosaureAction;
import ca.ulaval.glo4002.game.domain.turn.AddFoodAction;
import ca.ulaval.glo4002.game.domain.turn.ExecutableAction;
import ca.ulaval.glo4002.game.domain.turn.Turn;

import java.util.*;

public class Game {

    private Turn turn;
    private Pantry pantry;
    private Queue<ExecutableAction> actions = new LinkedList<>();

    public Game(Pantry pantry) { // Todo C'est bon ça?
        this.pantry = pantry;
        turn = new Turn(); // Todo Ici? Et les tests
    }

    public void createOrderFoodAction(Map<FoodType, Food> foods) {
        ExecutableAction addFoodCommand = new AddFoodAction(pantry, foods);

        actions.add(addFoodCommand);
    }

    public void createAddDinosaurAction() {
        ExecutableAction addDinosaureCommand = new AddDinosaureAction();
        actions.add(addDinosaureCommand);
    }

    public int playTurn() {
        int turnNumber = turn.play(actions);
        return turnNumber;
    }

    public void reset() {
        turn.reset();
    }
}
