package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.herd.Herd;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.food.PantryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class GameServiceTest {

    private Game game;
    private Herd herd;
    private Pantry pantry;
    private PantryRepository pantryRepository;
    private HerdRepository herdRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        game = mock(Game.class);
        herd = mock(Herd.class);
        pantry = mock(Pantry.class);
        pantryRepository = mock(PantryRepository.class);
        herdRepository = mock(HerdRepository.class);
        gameService = new GameService(game, herd, pantry, pantryRepository, herdRepository);
    }

    @Test
    public void givenFoods_whenPlayTurn_thenGameIsPlayed() {
        gameService.playTurn();

        verify(game).playTurn();
    }

    @Test
    public void whenPlayTurn_thenPantryIsUpdated() {
        gameService.playTurn();

        verify(pantryRepository).save(pantry);
    }

    @Test
    public void whenPlayTurn_thenHerdIsSaved() {
        gameService.playTurn();

        verify(herdRepository).save(herd);
    }

    @Test
    public void whenReset_thenGameIsReset() {
        gameService.reset();

        verify(game).reset();
    }

    @Test
    public void whenReset_thenHerdIsDeleted() {
        gameService.reset();

        verify(herdRepository).delete();
    }

    @Test
    public void whenReset_thenPantryIsIsDeleted() {
        gameService.reset();

        verify(pantryRepository).delete();
    }
}
