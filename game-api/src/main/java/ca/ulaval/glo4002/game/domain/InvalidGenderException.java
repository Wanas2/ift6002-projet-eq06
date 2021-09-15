package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;

public class InvalidGenderException extends GeneralBadRequestException {

    private final static String ERROR_CODE = "INVALID_GENDER";
    private final static String ERROR_MESSAGE = "The specified gender must be \"m\" or \"f\".";

    public InvalidGenderException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
