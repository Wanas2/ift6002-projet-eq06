package ca.ulaval.glo4002.game.domain;

import java.util.List;

public class Turn {

    private List<Action> actions;

    public Turn(List<Action> actions) {
        this.actions = actions;
    }

    public void play() {
        for(Action action: actions) {
            action.execute();
        }
    }

    public boolean emptyActions() {
//        actions.clear();
        return true;
    }
}
