package ca.ulaval.glo4002.game.interfaces.rest.dino;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DinosaurResource {

    private DinosaurRequestsValidator requestValidator;

    public  DinosaurResource(DinosaurRequestsValidator requestValidator){
        this.requestValidator = requestValidator;
    }

    @POST
    @Path("/dinosaurs")
    public Response addDino(DinosaurDTO dinosaurDTO) {
        requestValidator.validateAddRequest(dinosaurDTO);
        //TODO : creer et ajouter l'action au Turn (service)
        return Response.ok().build();
    }

    @GET
    @Path("/dinosaurs/{name}")
    public Response showDino(@PathParam("name") String name) {
        requestValidator.validateShowRequest(name);
        //TODO : recuperer le dinosaure et le renvoyer (service)
        return Response.ok().build();
    }

    @GET
    @Path("/dinosaurs")
    public Response showAllDino() {
        //TODO : recuperer tous les dinosaures et les renvoyer (service)
        return Response.ok().build();
    }
}
