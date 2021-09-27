package ca.ulaval.glo4002.game.domain.turn;

import java.util.Queue;

public class Turn {

    private int turnNumber = 0;

    public int play(Queue<ExecutableAction> actions) {
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
