package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DinosaurResource {

    private DinosaurService dinosaurService;

    public DinosaurResource(DinosaurService dinosaurService) {
        this.dinosaurService = dinosaurService;
    }

    @POST
    @Path("/dinosaurs")
    public Response addDino(DinosaurDTO dinosaurDTO) {
        dinosaurService.addDinosaur(dinosaurDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/breed")
    public Response breedDino(BreedingRequestDTO breedingRequestDTO) {
        dinosaurService.breedDino(breedingRequestDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/dinosaurs/{name}")
    public Response showDino(@PathParam("name") String name) {
        DinosaurDTO dinosaurDTO = dinosaurService.showDinosaur(name);
        return Response.ok().entity(dinosaurDTO).build();
    }

    @GET
    @Path("/dinosaurs")
    public Response showAllDino() {
        List<DinosaurDTO> dinos = dinosaurService.showAllDinosaurs();
        return Response.ok().entity(dinos).build();
    }
}
