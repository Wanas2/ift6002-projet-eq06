package ca.ulaval.glo4002.game.domain.food;

public class PantryFactory {

    public Pantry create() {
        CookItSubscription cookItSubscription = new CookItSubscription();

        return new Pantry(cookItSubscription);
    }
}
