package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.food.PantryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PantryRepositoryInMemoryTest {

    private PantryRepository pantryRepositoryInMemory;

    @BeforeEach
    void setUp() {
        pantryRepositoryInMemory = new PantryRepositoryInMemory();
    }

}