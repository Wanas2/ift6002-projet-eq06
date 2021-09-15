package ca.ulaval.glo4002.game.domain;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;

public class NonExistentNameException extends GeneralBadRequestException {

    private final static String ERROR_CODE = "NON_EXISTENT_NAME";
    private final static String ERROR_MESSAGE = "The specified name does not exist.";

    public NonExistentNameException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
