package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.food.PantryRepository;
import org.junit.jupiter.api.BeforeEach;

class PantryRepositoryInMemoryImplTest {

    private PantryRepository pantryRepositoryInMemory;

    @BeforeEach
    void setUp() {
        pantryRepositoryInMemory = new PantryRepositoryInMemoryImpl();
    }

}