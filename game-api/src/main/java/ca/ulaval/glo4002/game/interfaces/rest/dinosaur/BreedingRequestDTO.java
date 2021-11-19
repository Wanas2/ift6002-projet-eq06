package ca.ulaval.glo4002.game.interfaces.rest.dinosaur;

public class BreedingRequestDTO {

    public final String name;
    public final String fatherName;
    public final String motherName;

    public BreedingRequestDTO(String name, String fatherName, String motherName) {
        this.name = name;
        this.fatherName = fatherName;
        this.motherName = motherName;
    }
}
