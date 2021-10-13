package ca.ulaval.glo4002.game.domain.dinosaur.babyMaking;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BabyDinoResponseDTO;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.SpeciesWillNotBreedException;

public interface BabyFetcher {

    BabyDinoResponseDTO fetch(Dinosaur fatherDinosaur, Dinosaur motherDinosaur) throws SpeciesWillNotBreedException;
}
