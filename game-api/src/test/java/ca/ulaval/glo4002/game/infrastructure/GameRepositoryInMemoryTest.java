package ca.ulaval.glo4002.game.infrastructure;

import ca.ulaval.glo4002.game.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class GameRepositoryInMemoryTest {
    private final Game A_GAME = mock(Game.class);
    private GameRepositoryInMemory gameRepository;

    @BeforeEach
    public void setUp() {
        gameRepository = new GameRepositoryInMemory();
    }

    @Test
    public void shouldBeInitiallyEmpty(){
        assertTrue(gameRepository.find().isEmpty());
    }

    @Test
    public void whenSave_thenShouldNotBeEmpty() {
        gameRepository.save(A_GAME);

        assertFalse(gameRepository.find().isEmpty());
    }

    @Test
    public void givenASavedGame_whenFind_thenTheGameShouldBeReturned() {
        gameRepository.save(A_GAME);

        Optional<Game> gameReturned = gameRepository.find();

        Game gameExpected = A_GAME;
        assertEquals(gameExpected, gameReturned.get());
    }

    @Test
    public void givenASavedGame_whenDelete_thenThenShouldBeEmpty() {
        gameRepository.save(A_GAME);

        gameRepository.delete();

        assertTrue(gameRepository.find().isEmpty());
    }

}
