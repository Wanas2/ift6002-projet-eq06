package ca.ulaval.glo4002.game.interfaces.exceptions.dto;

public class ExceptionDTO {

    public final String error;
    public final String description;

    public ExceptionDTO(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
