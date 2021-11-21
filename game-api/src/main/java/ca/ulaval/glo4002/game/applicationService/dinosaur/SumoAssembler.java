package ca.ulaval.glo4002.game.applicationService.dinosaur;

import ca.ulaval.glo4002.game.interfaces.rest.dinosaur.SumoResponseDTO;

public class SumoAssembler {

    public SumoResponseDTO toDTO(String predictedWinner) {
       return new SumoResponseDTO(predictedWinner);
    }
}
