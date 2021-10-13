package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.food.PantryRepository;

import java.util.Optional;

public class PantryRepositoryInMemoryImpl implements PantryRepository {

    private Pantry pantry;

    @Override
    public void save(Pantry pantry) {
        this.pantry = pantry;
    }

    @Override
    public Optional<Pantry> find() {
        return Optional.ofNullable(pantry);
    }

    @Override
    public void delete() {
        pantry = null;
    }
}

