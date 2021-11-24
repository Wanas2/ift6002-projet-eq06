package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;

public class BreedingAssembler {

    public BreedingRequestExternalDTO toDTO(Dinosaur fatherDinosaur, Dinosaur motherDinosaur) {
        return new BreedingRequestExternalDTO(fatherDinosaur.getSpecies().toString(),
                motherDinosaur.getSpecies().toString());
    }
}
