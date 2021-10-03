package ca.ulaval.glo4002.game.interfaces.rest.dino.exceptions;


import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralNotFoundException;

public class NonExistentNameException extends GeneralNotFoundException {

    private final static String ERROR_CODE = "NON_EXISTENT_NAME";
    private final static String ERROR_MESSAGE = "The specified name does not exist.";

    public NonExistentNameException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
