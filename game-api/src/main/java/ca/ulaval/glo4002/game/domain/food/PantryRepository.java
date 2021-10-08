package ca.ulaval.glo4002.game.domain.food;

public interface PantryRepository {

    void update(Pantry pantry);

    Pantry getPantry();
}
