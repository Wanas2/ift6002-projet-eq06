package ca.ulaval.glo4002.game.domain.Turn;

import ca.ulaval.glo4002.game.domain.parkResources.Food;
import ca.ulaval.glo4002.game.domain.parkResources.Pantry;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Turn {

    private int turnNumber = 0;
    private Queue<Action> actions = new LinkedList<>();

    private final Pantry pantry;

    public Turn(Pantry pantry) {
        this.pantry = pantry;
    }

    public int play(List<Food> foods) {
        while(hasActions()){
            Action action = actions.remove();
            action.execute();
        }

        pantry.addFood(foods);
        turnNumber++;
        return turnNumber;
    }

    public void reset() {
        turnNumber = 0;
        actions.clear();
    }

    public boolean hasActions() {
        return !actions.isEmpty();
    }

    public void addAction(Action action) {
        actions.add(action);
    }
}
