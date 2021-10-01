package ca.ulaval.glo4002.game.interfaces.rest.exceptions;

public class GeneralNotFoundException extends RuntimeException {

    private final String error;
    private final String description;

    public GeneralNotFoundException(String error, String description) {
        super(description);
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
