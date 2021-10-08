package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.food.PantryRepository;

public class PantryRepositoryInMemory implements PantryRepository {

    private Pantry pantry;

    @Override
    public void update(Pantry pantry) {
        this.pantry = pantry;
    }

    @Override
    public Pantry getPantry() {
        return pantry;
    }
}
