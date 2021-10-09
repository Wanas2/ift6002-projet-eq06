package ca.ulaval.glo4002.game.domain;


import ca.ulaval.glo4002.game.domain.action.AddDinosaurAction;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.action.AddFoodAction;
import ca.ulaval.glo4002.game.domain.action.ExecutableAction;

import java.util.*;

public class Game {

    private final Turn turn;
    private final Herd herd;
    private final Pantry pantry;

    public Game(Herd herd, Pantry pantry, Turn turn) {
        this.herd = herd;
        this.pantry = pantry;
        this.turn = turn;
    }

    public void addDinosaur(Dinosaur dinosaur){
        ExecutableAction addDinosaurAction = new AddDinosaurAction(herd, dinosaur);
        turn.acquireNewAction(addDinosaurAction);
    }

    public void addFood(Map<FoodType, Food> foods) {
        ExecutableAction addFoodAction = new AddFoodAction(pantry, foods);
        turn.acquireNewAction(addFoodAction);
    }

    public int playTurn() {
        int turnNumber = turn.playActions();

        pantry.incrementFreshFoodAges();
        pantry.addCurrentTurnFoodBatchToFreshFood();
        pantry.removeExpiredFoodFromFreshFood();

        herd.feed();
        herd.increaseAge();

        return turnNumber;
    }

    public void reset() {
        turn.reset();
        herd.reset();
        pantry.reset();
    }
}
