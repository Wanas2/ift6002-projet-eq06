package ca.ulaval.glo4002.game.interfaces.rest.dinosaur;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SumoRequestDTO {

    public String challenger;
    public String challengee;

    @JsonCreator
    public SumoRequestDTO(@JsonProperty(value = "challenger", required = true) String challenger,
                          @JsonProperty(value = "challengee", required = true)String challengee) {
        this.challenger = challenger;
        this.challengee = challengee;
    }
}