package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

public class BreedingRequestExternalDTO {

    public final String fatherSpecies;
    public final String motherSpecies;

    public BreedingRequestExternalDTO(String fatherSpecies, String motherSpecies) {
        this.fatherSpecies = fatherSpecies;
        this.motherSpecies = motherSpecies;
    }
}
