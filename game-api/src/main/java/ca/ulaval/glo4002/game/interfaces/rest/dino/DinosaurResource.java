package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.applicationService.GameService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DinosaurResource {

    private DinosaurRequestsValidator requestValidator;
    private GameService gameService;

    public  DinosaurResource(GameService gameService, DinosaurRequestsValidator requestValidator){
        this.requestValidator = requestValidator;
        this.gameService = gameService;
    }

    @POST
    @Path("/dinosaurs")
    public Response addDino(DinosaurDTO dinosaurDTO) {
        //TODO : validation dans le domaine
        requestValidator.validateAddRequest(dinosaurDTO);
        gameService.addDinosaur(dinosaurDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/dinosaurs/{name}")
    public Response showDino(@PathParam("name") String name) {
        requestValidator.validateShowRequest(name);
        DinosaurDTO dinosaurDTO = gameService.showDinosaur(name);
        return Response.ok().entity(dinosaurDTO).build();
    }

    @GET
    @Path("/dinosaurs")
    public Response showAllDino() {
        List<DinosaurDTO> dinos = gameService.showAllDinosaurs();
        return Response.ok().entity(dinos).build();
    }
}
