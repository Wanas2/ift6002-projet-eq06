package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DinosaurResource {

    private final DinosaurService dinosaureService;

    public DinosaurResource(DinosaurService dinosaureService) {
        this.dinosaureService = dinosaureService;
    }

    @POST
    @Path("/dinosaurs")
    public Response addDino(DinosaurDTO dinosaurDTO) {
        dinosaureService.addDinosaur(dinosaurDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/breed")
    public Response breedDino(BreedingRequestDTO breedingRequestDTO) {
        dinosaureService.breedDino(breedingRequestDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/dinosaurs/{name}")
    public Response showDino(@PathParam("name") String name) {
        DinosaurDTO dinosaurDTO = dinosaureService.showDinosaur(name);
        return Response.ok().entity(dinosaurDTO).build();
    }

    @GET
    @Path("/dinosaurs")
    public Response showAllDinosaurs() {
        List<DinosaurDTO> dinosaurs = dinosaureService.showAllDinosaurs();
        return Response.ok().entity(dinosaurs).build();
    }
}
