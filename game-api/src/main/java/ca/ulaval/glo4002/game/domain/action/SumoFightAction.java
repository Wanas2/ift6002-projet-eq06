package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;

public class SumoFightAction implements ExecutableAction {

    private final Herd herd;
    private final Dinosaur firstDinosaurFighter;
    private final Dinosaur secondDinosaurFighter;

    public SumoFightAction(Herd herd, Dinosaur firstDinosaurFighter, Dinosaur secondDinosaurFighter) {
        this.herd = herd;
        this.firstDinosaurFighter = firstDinosaurFighter;
        this.secondDinosaurFighter = secondDinosaurFighter;
    }

    @Override
    public void execute() {
        herd.organizeSumoFight(firstDinosaurFighter,secondDinosaurFighter);
    }
}
