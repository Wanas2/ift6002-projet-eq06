package ca.ulaval.glo4002.game.domain;

import java.util.ArrayList;
import java.util.List;

public class Turn {

    private int turnNumber = 0;
    private List<String> actions = new ArrayList<>();

    public void acquireAction() {
        actions.add("An action");
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void play() {
        actions = new ArrayList<>();
        turnNumber += 1;
    }

    public void reset() {
        turnNumber = 0;
        actions = new ArrayList<>();
    }
}
