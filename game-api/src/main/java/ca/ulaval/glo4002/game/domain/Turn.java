package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.action.ExecutableAction;

import java.util.LinkedList;
import java.util.Queue;

public class Turn {

    private int turnNumber = 0;
    private final Queue<ExecutableAction> actions = new LinkedList<>();

    public void acquireNewAction(ExecutableAction action) {
        actions.add(action);
    }

    public int playActions() {
        for(ExecutableAction action : actions) {
            action.execute();
        }
        turnNumber++;
        return turnNumber;
    }

    public void reset() {
        turnNumber = 0;
    }
}
