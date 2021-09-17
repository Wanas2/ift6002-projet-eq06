package ca.ulaval.glo4002.game.domain;

public class Game {

    private final Turn turn;

    public Game(Turn turn) {
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
