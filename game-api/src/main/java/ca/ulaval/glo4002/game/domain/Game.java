package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.Turn.Turn;
import ca.ulaval.glo4002.game.domain.parkResources.Food;

import java.util.List;

public class Game {

    private final Turn turn;

    public Game(Turn turn) {
        this.turn = turn;
    }

    public int playTurn(List<Food> foods) {
        int turnNumber = turn.play(foods);

        return turnNumber;
    }

    public void reset() {
        turn.reset();
    }
}
