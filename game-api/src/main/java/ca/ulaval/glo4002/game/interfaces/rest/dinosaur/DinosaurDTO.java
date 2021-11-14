package ca.ulaval.glo4002.game.interfaces.rest.dinosaur;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DinosaurDTO {

    public String name;
    public int weight;
    public String gender;
    public String species;

    @JsonCreator
    public DinosaurDTO(@JsonProperty(value = "name", required = true) String name,
                       @JsonProperty(value = "weight", required = true) int weight,
                       @JsonProperty(value = "gender", required = true) String gender,
                       @JsonProperty(value = "species", required = true) String species) {
        this.name = name;
        this.weight = weight;
        this.gender = gender;
        this.species = species;
    }
}
