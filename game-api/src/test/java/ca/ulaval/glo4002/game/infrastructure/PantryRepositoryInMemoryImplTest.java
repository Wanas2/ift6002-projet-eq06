package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.food.CookItSubscription;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.food.PantryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PantryRepositoryInMemoryImplTest {

    private final Pantry A_PANTRY = new Pantry(new CookItSubscription());
    private PantryRepository pantryRepository;

    @BeforeEach
    void setUp() {
        pantryRepository = new PantryRepositoryInMemoryImpl();
    }

    @Test
    public void shouldInitiallyBeEmpty(){
        assertTrue(pantryRepository.find().isEmpty());
    }

    @Test
    public void givenAPantryHasBeenSaved_whenFind_thenTePantryShouldBeFound(){
        pantryRepository.save(A_PANTRY);

        Optional<Pantry> foundPantry = pantryRepository.find();

        assertTrue(foundPantry.isPresent());
        assertEquals(A_PANTRY,foundPantry.get());
    }

    @Test
    public void givenAPantryHasBeenSaved_whenDelete_thenThePantryShouldNotBeFound(){
        pantryRepository.save(A_PANTRY);

        pantryRepository.delete();
        Optional<Pantry> foundPantry = pantryRepository.find();

        assertTrue(foundPantry.isEmpty());
    }
}
