package ca.ulaval.glo4002.game.interfaces.rest.game;

import ca.ulaval.glo4002.game.applicationService.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GameResourceTest {

    private GameService gameService;
    private GameResource gameResource;

    @BeforeEach
    public void setUp(){
        gameService = mock(GameService.class);
        gameResource = new GameResource(gameService);
    }

    @Test
    public void whenPlaying_thenShouldCallGameService(){
        gameResource.playTurn();

        verify(gameService).playTurn();
    }

    @Test
    public void whenPlaying_thenResponseReturnShouldBeValid(){
        Response response = gameResource.playTurn();

        assertEquals(Response.ok().build().getStatus(), response.getStatus());
    }

    @Test
    public void whenResetting_thenShouldCallGameService(){
        gameResource.reset();

        verify(gameService).reset();
    }

    @Test
    public void whenResetting_thenResponseReturnShouldBeValid(){
        Response response = gameResource.reset();

        assertEquals(Response.ok().build().getStatus(), response.getStatus());
    }
}
