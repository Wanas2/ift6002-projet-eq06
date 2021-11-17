package ca.ulaval.glo4002.game.domain;


import ca.ulaval.glo4002.game.domain.action.AddDinosaurAction;
import ca.ulaval.glo4002.game.domain.action.SumoFightAction;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.dinosaur.SumoFightOrganizer;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.action.AddFoodAction;
import ca.ulaval.glo4002.game.domain.action.ExecutableAction;

import java.util.*;

public class Game {

    private final Turn turn;
    private final Herd herd;
    private final Pantry pantry;
    private final SumoFightOrganizer sumoFightOrganizer;

    public Game(Herd herd, Pantry pantry, Turn turn) {
        this.herd = herd;
        this.pantry = pantry;
        this.turn = turn;
        sumoFightOrganizer = new SumoFightOrganizer();
    }

    public void addDinosaur(Dinosaur dinosaur) {
        ExecutableAction addDinosaurAction = new AddDinosaurAction(herd, dinosaur);
        turn.acquireNewAction(addDinosaurAction);
    }

    public void addFood(List<Food> foods) {
        ExecutableAction addFoodAction = new AddFoodAction(pantry, foods);
        turn.acquireNewAction(addFoodAction);
    }

    public void addSumoFight(Dinosaur firstDinosaurFighter, Dinosaur secondDinosaurFighter) {
        ExecutableAction addSumoFightAction = new SumoFightAction(herd, firstDinosaurFighter, secondDinosaurFighter);
        turn.acquireNewAction(addSumoFightAction);
    }

    public int playTurn() {
        int turnNumber = turn.playActions();

        pantry.incrementFreshFoodAges();
        pantry.storeFood();

        pantry.splitWater();
        herd.feedDinosaurs();
        pantry.mergeWater();

        herd.increaseDinosaursAge();

        sumoFightOrganizer.reset();

        return turnNumber;
    }

    public void reset() {
        turn.reset();
        herd.reset();
        pantry.reset();
    }
}
