package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BabyDinoJsonDataMapperFromWebTarget implements BabyDinoMapper {

    @Override
    public BabyDinoResponseDTO mapData(WebTarget data, BreedingRequestExternalDTO breedingRequestExternalDTO) {
        Invocation.Builder invocationBuilder =  data.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity
                .entity(breedingRequestExternalDTO, MediaType.APPLICATION_JSON));

        if(response.getStatus() == 200)
            try {
                throw new SpeciesWillNotBreedException("Impossibles to breed these species");
            } catch (SpeciesWillNotBreedException exception) {
                exception.printStackTrace();
            }
        return response.readEntity(BabyDinoResponseDTO.class);
    }
}
