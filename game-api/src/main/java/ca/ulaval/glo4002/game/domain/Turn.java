package ca.ulaval.glo4002.game.domain;

import java.util.LinkedList;
import java.util.Queue;

public class Turn implements Playable {

    private int turnNumber = 0;
    private Queue<Action> actions = new LinkedList<>();

    @Override
    public int play() {
        while(hasActions()){
            Action action = actions.remove();
            action.execute();
        }

        turnNumber++;
        return turnNumber;
    }

    @Override
    public void reset() {
        turnNumber = 0;
        actions.clear();
    }

    @Override
    public boolean hasActions() {
        return !actions.isEmpty();
    }

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }
}
