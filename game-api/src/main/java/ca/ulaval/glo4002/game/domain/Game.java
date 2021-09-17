package ca.ulaval.glo4002.game.domain;

public class Game {

    private final Playable turn;

    public Game(Playable turn) {
        this.turn = turn;
    }

    public int playTurn() {
        int turnNumber = turn.play();
        return turnNumber;
    }

    public void reset() {
        turn.reset();
    }
}
