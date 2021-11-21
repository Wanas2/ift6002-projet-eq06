package ca.ulaval.glo4002.game.interfaces.rest.dinosaur;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurAssembler;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DinosaurResource {

    private final DinosaurService dinosaurService;
    private final DinosaurAssembler dinosaurAssembler;

    public DinosaurResource(DinosaurService dinosaurService, DinosaurAssembler dinosaurAssembler) {
        this.dinosaurService = dinosaurService;
        this.dinosaurAssembler = dinosaurAssembler;
    }

    @POST
    @Path("/dinosaurs")
    public Response addDinosaur(DinosaurDTO dinosaurDTO) {
        if(dinosaurDTO.weight < 100) {
            throw new InvalidWeightException();
        }
        dinosaurService.addDinosaur(dinosaurDTO.name, dinosaurDTO.weight, dinosaurDTO.gender, dinosaurDTO.species);
        return Response.ok().build();
    }

    @POST
    @Path("/breed")
    public Response breedDinosaur(BreedingRequestDTO breedingRequestDTO) {
        dinosaurService.breedDinosaur(breedingRequestDTO.name, breedingRequestDTO.fatherName,
                breedingRequestDTO.motherName);
        return Response.ok().build();
    }

    @GET
    @Path("/dinosaurs/{name}")
    public Response showDinosaur(@PathParam("name") String name) {
        Dinosaur dinosaur = dinosaurService.showDinosaur(name);
        DinosaurDTO dinosaurDTO = dinosaurAssembler.toDTO(dinosaur);
        return Response.ok().entity(dinosaurDTO).build();
    }

    @GET
    @Path("/dinosaurs")
    public Response showAllDinosaurs() {
        List<Dinosaur> dinosaurs = dinosaurService.showAllDinosaurs();
        List<DinosaurDTO> dinosaurDTOs = dinosaurs.stream()
                .map(dinosaurAssembler::toDTO)
                .collect(Collectors.toList());

        return Response.ok().entity(dinosaurDTOs).build();
    }

    @PATCH
    @Path("/dinosaurs/{name}")
    public Response patchDinosaur(@PathParam("name") String name, GrowDTO growDTO) {
        dinosaurService.patchDinosaurWeight(name, growDTO.weight);
        return Response.ok().build();
    }
}
