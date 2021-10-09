package ca.ulaval.glo4002.game.domain.food;

import java.util.Optional;

public interface PantryRepository {

    void update(Pantry pantry);

    Optional<Pantry> find();

    void delete();
}
