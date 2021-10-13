package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import javax.ws.rs.client.WebTarget;

public interface DinoBreeder {

    BabyDinoResponseDTO breed(WebTarget data, BreedingRequestExternalDTO breedingRequestExternalDTO)
            throws SpeciesWillNotBreedException;
}
