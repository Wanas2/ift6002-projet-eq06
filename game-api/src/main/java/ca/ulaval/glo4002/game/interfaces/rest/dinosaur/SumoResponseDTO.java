package ca.ulaval.glo4002.game.interfaces.rest.dinosaur;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SumoResponseDTO {

    public final String predictedWinner;

    @JsonCreator
    public SumoResponseDTO(String predictedWinner) {
        this.predictedWinner = predictedWinner;
    }
}
