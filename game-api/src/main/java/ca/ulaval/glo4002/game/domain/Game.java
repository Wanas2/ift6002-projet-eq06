package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.action.AddDinosaureAction;
import ca.ulaval.glo4002.game.domain.action.AddFoodAction;
import ca.ulaval.glo4002.game.domain.action.ExecutableAction;

import java.util.*;

public class Game {

    private Turn turn;
    private Pantry pantry;
    private Queue<ExecutableAction> actions = new LinkedList<>();

    public Game(Pantry pantry) {
        this.pantry = pantry;
        turn = new Turn(); // Todo Créer Turn dans la config
    }

    public void orderFood(Map<FoodType, Food> foods) {
        pantry.orderFood(foods);
        ExecutableAction addFoodCommand = new AddFoodAction(pantry, foods);
        actions.add(addFoodCommand);
    }

    public void addDinosaur() {
        ExecutableAction addDinosaureCommand = new AddDinosaureAction();
        actions.add(addDinosaureCommand);
    }

    public int playTurn() {
        return turn.play(actions);
    }

    public void reset() {
        turn.reset();
    }
}
