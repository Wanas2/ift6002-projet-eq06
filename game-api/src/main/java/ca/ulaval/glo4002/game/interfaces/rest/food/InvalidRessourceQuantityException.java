package ca.ulaval.glo4002.game.interfaces.rest.food;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;

public class InvalidRessourceQuantityException extends GeneralBadRequestException {

    private final static String ERROR_CODE = "INVALID_RESOURCE_QUANTITY";
    private final static String ERROR_MESSAGE = "Resource quantities must be positive.";

    public InvalidRessourceQuantityException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}