package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;

public class BreedingAssembler {

    public BreedingRequestExternalDTO createExternalAPIDTO(Dinosaur fatherDinosaur, Dinosaur motherDinosaur) {
        BreedingRequestExternalDTO breedingRequestExternalDTO = new BreedingRequestExternalDTO();
        breedingRequestExternalDTO.fatherSpecies = fatherDinosaur.getSpecies().toString();
        breedingRequestExternalDTO.motherSpecies = motherDinosaur.getSpecies().toString();
        return breedingRequestExternalDTO;
    }
}
