package ca.ulaval.glo4002.game.domain;

import java.util.List;
import java.util.Queue;

public class Game {

    private Playable turn;

    public Game(Playable turn) {
        this.turn = turn;
    }

    public int playTurn() {
        return turn.play();
    }

    public void reset() {
        turn.reset();
    }
}
