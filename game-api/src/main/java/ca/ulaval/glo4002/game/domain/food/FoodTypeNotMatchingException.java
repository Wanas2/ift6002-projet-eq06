package ca.ulaval.glo4002.game.domain.food;

public class FoodTypeNotMatchingException extends Exception {

    public FoodTypeNotMatchingException(String message) {
        super(message);
    }
}
