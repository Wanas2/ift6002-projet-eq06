package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

public class BabyDinosaurResponseDTO {

    public final String gender;
    public final String offspring;

    public BabyDinosaurResponseDTO(String gender, String offspring) {
        this.gender = gender;
        this.offspring = offspring;
    }
}
