package ca.ulaval.glo4002.game.domain.dinosaur.babyMaking;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;

public class DinosaurBreedingCouple {

    Dinosaur fatherDinosaur;
    Dinosaur motherDinosaur;

    public DinosaurBreedingCouple(Dinosaur fatherDinosaur, Dinosaur motherDinosaur) {
        this.fatherDinosaur = fatherDinosaur;
        this.motherDinosaur = motherDinosaur;
    }

    public Dinosaur getFatherDinosaur() {
        return fatherDinosaur;
    }

    public Dinosaur getMotherDinosaur() {
        return motherDinosaur;
    }

    public void breed() {

    }
}
