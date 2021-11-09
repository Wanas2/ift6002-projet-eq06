package ca.ulaval.glo4002.game.interfaces.rest.game;

import ca.ulaval.glo4002.game.applicationService.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameResourceTest {

    private final static int STATUS_200_OK = 200;

    private GameService gameService;
    private GameResource gameResource;

    @BeforeEach
    public void setUp() {
        gameService = mock(GameService.class);
        gameResource = new GameResource(gameService);
    }

    @Test
    public void whenPlayTurn_thenShouldCallGameService() {
        gameResource.playTurn();

        verify(gameService).playTurn();
    }

    @Test
    public void whenPlayTurn_thenResponseStatusShouldBe200() {
        Response response = gameResource.playTurn();

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void whenReset_thenShouldCallGameService() {
        gameResource.reset();

        verify(gameService).reset();
    }

    @Test
    public void whenReset_thenResponseStatusShouldBe200() {
        Response response = gameResource.reset();

        assertEquals(STATUS_200_OK, response.getStatus());
    }
}
