package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BabyDinoResponseDTO;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BreedingRequestExternalDTO;

public interface Breeder {

    BabyDinoResponseDTO breed(BreedingRequestExternalDTO breedingRequestExternalDTO);
}
