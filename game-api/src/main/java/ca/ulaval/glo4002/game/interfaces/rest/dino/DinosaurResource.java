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
    public Response addDinosaur(DinosaurDTO dinosaurDTO) {
        dinosaurService.addDinosaur(dinosaurDTO);
        return Response.ok().build();
    }

    @POST
    @Path("/breed")
    public Response breedDinosaur(BreedingRequestDTO breedingRequestDTO) {
        dinosaurService.breedDinosaur(breedingRequestDTO);
        return Response.ok().build();
    }

    @GET
    @Path("/dinosaurs/{name}")
    public Response showDinosaur(@PathParam("name") String name) {
        DinosaurDTO dinosaurDTO = dinosaurService.showDinosaur(name);
        return Response.ok().entity(dinosaurDTO).build();
    }

    @GET
    @Path("/dinosaurs")
    public Response showAllDinosaur() {
        List<DinosaurDTO> dinosaurs = dinosaurService.showAllDinosaurs();
        return Response.ok().entity(dinosaurs).build();
    }
}
