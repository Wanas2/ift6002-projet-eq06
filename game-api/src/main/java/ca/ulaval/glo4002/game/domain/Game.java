package ca.ulaval.glo4002.game.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<String> dinosaures;
    private List<String> resources;

    private TurnFactory turnFactory;
    private Turn turn;

    public Game(TurnFactory turnFactory) {
        this.turnFactory = turnFactory;
        dinosaures = new ArrayList<>();
        resources = new ArrayList<>();
        turn = turnFactory.createTurn();
    }

    public int playTurn(){
        turn.play();

        return turn.getTurnNumber();
    }

    public void resetGame() {
        dinosaures = new ArrayList<>();
        resources = new ArrayList<>();
        turn.reset();
    }
}
