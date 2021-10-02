package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.action.ExecutableAction;

import java.util.LinkedList;
import java.util.Queue;

public class Turn {

    private int turnNumber = 0;
    private Queue<ExecutableAction> actions = new LinkedList<>();

    public int play() {
        for(ExecutableAction action : actions) {
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

    public void acquireNewAction(ExecutableAction action) {
        actions.add(action);
    }
}