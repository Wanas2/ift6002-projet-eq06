package ca.ulaval.glo4002.game.interfaces.rest.dinosaur;

public class SumoRequestDTO {

    public String challenger;
    public String challengee;

    public SumoRequestDTO(String challenger, String challengee) {
        this.challenger = challenger;
        this.challengee = challengee;
    }
}
