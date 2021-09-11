package ca.ulaval.glo4002.game.interfaces.rest.game;

import ca.ulaval.glo4002.game.applicationService.GameService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class GameResource {

    private GameService gameService;

    public GameResource(GameService gameService) {
        this.gameService = gameService;
    }

    @POST
    @Path("/turn")
    public Response playTurn() {
        TurnNumberDTO turnDTO = gameService.playTurn();

        return Response.ok().entity(turnDTO).build();
    }

    @POST
    @Path("/reset")
    public Response reset() {
        gameService.reset();

        return Response.ok().build();
    }
}
