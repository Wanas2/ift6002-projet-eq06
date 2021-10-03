package ca.ulaval.glo4002.game.interfaces.rest.dino.exceptions;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;

public class InvalidSpeciesException extends GeneralBadRequestException {

    private final static String ERROR_CODE = "INVALID_SPECIES";
    private final static String ERROR_MESSAGE = "The specified species is not supported.";

    public InvalidSpeciesException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
