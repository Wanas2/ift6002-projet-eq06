package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.domain.action.AddDinosaurAction;
import ca.ulaval.glo4002.game.domain.action.ExecutableAction;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;

public class Game {

    private final Turn turn;
    private final Herd herd;

    public Game(Turn turn, Herd herd) {
        this.turn = turn;
        this.herd = herd;
    }

    public int playTurn() {
        int turnNumber = turn.play();
        return turnNumber;
    }

    public void addDinosaur(Dinosaur dinosaur){
        ExecutableAction addDinosaurAction = new AddDinosaurAction(herd, dinosaur);
        turn.acquireNewAction(addDinosaurAction);
    }

    public void reset() {
        turn.reset();
        herd.reset();
    }
}
