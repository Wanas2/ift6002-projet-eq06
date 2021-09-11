package ca.ulaval.glo4002.game.domain;

public class Turn {

    private int turnNumber = 0;

    public int play() {
        turnNumber += 1;

        return turnNumber;
    }

    public void reset() {
        turnNumber = 0;
    }
}
