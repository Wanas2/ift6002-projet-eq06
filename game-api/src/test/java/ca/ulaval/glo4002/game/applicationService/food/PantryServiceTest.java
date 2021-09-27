package ca.ulaval.glo4002.game.applicationService.food;

import ca.ulaval.glo4002.game.domain.food.Pantry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class PantryServiceTest {

    private Pantry pantry;
    private PantryService pantryService;

    @BeforeEach
    void setUp() {
        pantry = mock(Pantry.class);
        pantryService = new PantryService();
    }

    @Test
    public void whenOrderFood_thenPantryShouldOrderFood() {
        pantryService.orderFood();

        verify(pantry).orderFood(); // Todo À terminer
    }
}