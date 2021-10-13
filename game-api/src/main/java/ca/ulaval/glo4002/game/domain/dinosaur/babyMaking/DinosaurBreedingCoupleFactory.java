package ca.ulaval.glo4002.game.domain.dinosaur.babyMaking;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;

public class DinosaurBreedingCoupleFactory {

    private final Herd herd;

    public DinosaurBreedingCoupleFactory(Herd herd) {
        this.herd = herd;
    }

    public DinosaurBreedingCouple create(String fatherName, String motherName) {
        Dinosaur fatherDinosaur = herd.getDinosaurWithName(fatherName);
        Dinosaur motherDinosaur = herd.getDinosaurWithName(motherName);
        return new DinosaurBreedingCouple(fatherDinosaur, motherDinosaur);
    }
}
