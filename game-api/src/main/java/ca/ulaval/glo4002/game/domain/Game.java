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

    public Game(Pantry pantry, Turn turn) {
        this.pantry = pantry;
        this.turn = turn;
    }

    public void addFood(Map<FoodType, Food> foods) {
        ExecutableAction addFoodAction = new AddFoodAction(pantry, foods);
        turn.acquireNewAction(addFoodAction);
    }

    public void addDinosaur() {
        ExecutableAction addDinosaureAction = new AddDinosaureAction();
    }

    public int playTurn() {
        int turnNumber = turn.playActions();
        pantry.addNewFoodToFreshFood();
        return turnNumber;
    }

    public void reset() {
        turn.reset();
    }
}
