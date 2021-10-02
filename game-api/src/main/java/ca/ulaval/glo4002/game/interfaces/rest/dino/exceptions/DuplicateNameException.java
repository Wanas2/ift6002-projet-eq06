package ca.ulaval.glo4002.game.interfaces.rest.dino.exceptions;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;

public class DuplicateNameException extends GeneralBadRequestException {

    private final static String ERROR_CODE = "DUPLICATE_NAME";
    private final static String ERROR_MESSAGE = "The specified name already exists and must be unique.";

    public DuplicateNameException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
