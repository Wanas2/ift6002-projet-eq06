package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.infrastructure.dinosaurBreederExternal.BabyDinoReponseDTO;

public interface Breeder {

    BabyDinoReponseDTO breed(String fatherSpecies, String motherSpecies); // Todo Qu'est-ce que ceci devrait retourner?
}
