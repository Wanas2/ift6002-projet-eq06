package ca.ulaval.glo4002.game.domain;

import java.util.LinkedList;
import java.util.Queue;

public class Turn {

    private int turnNumber = 0;
    private Queue<Action> actions = new LinkedList<>();

    public int play() {
        while(hasActions()){
            Action action = actions.remove();
            action.execute();
        }

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
