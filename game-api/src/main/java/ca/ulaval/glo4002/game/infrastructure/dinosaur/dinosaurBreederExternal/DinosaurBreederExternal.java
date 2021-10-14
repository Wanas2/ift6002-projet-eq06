package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DinosaurBreederExternal {

    public BabyDinoResponseDTO breed(WebTarget externalService, BreedingRequestExternalDTO breedingRequestExternalDTO)
            throws SpeciesWillNotBreedException {
        Invocation.Builder invocationBuilder = externalService.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity
                .entity(breedingRequestExternalDTO, MediaType.APPLICATION_JSON));

        if(response.getStatus() == 400)
            throw new SpeciesWillNotBreedException("Impossibles to breed these species");

        return response.readEntity(BabyDinoResponseDTO.class);
    }
}
