package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;

public class InvalidWeightException extends GeneralBadRequestException {

    private final static String ERROR_CODE = "INVALID_WEIGHT";
    private final static String ERROR_MESSAGE = "The specified weight must be greater than 0.";

    public InvalidWeightException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
