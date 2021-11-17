package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.food.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PantryRepositoryInMemoryImplTest {

    private FoodProvider cookItSubscription;
    private FoodQuantitySummaryCalculator foodQuantitySummaryCalculator;
    private Pantry aPantry;
    private PantryRepository pantryRepository;

    @BeforeEach
    void setUp() {
        pantryRepository = new PantryRepositoryInMemoryImpl();
        cookItSubscription = mock(CookItSubscription.class);
        foodQuantitySummaryCalculator = new FoodQuantitySummaryCalculator();
        aPantry = new Pantry(cookItSubscription, foodQuantitySummaryCalculator);
    }

    @Test
    public void shouldInitiallyBeEmpty() {
        assertTrue(pantryRepository.find().isEmpty());
    }

    @Test
    public void givenAPantryHasBeenSaved_whenFind_thenTePantryShouldBeFound() {
        pantryRepository.save(aPantry);

        Optional<Pantry> foundPantry = pantryRepository.find();

        assertTrue(foundPantry.isPresent());
        assertEquals(aPantry, foundPantry.get());
    }

    @Test
    public void givenAPantryHasBeenSaved_whenDelete_thenThePantryShouldNotBeFound() {
        pantryRepository.save(aPantry);

        pantryRepository.delete();
        Optional<Pantry> foundPantry = pantryRepository.find();

        assertTrue(foundPantry.isEmpty());
    }
}
