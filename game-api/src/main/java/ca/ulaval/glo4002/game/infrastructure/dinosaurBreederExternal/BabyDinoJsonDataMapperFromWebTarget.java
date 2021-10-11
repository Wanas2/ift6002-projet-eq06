package ca.ulaval.glo4002.game.infrastructure.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.interfaces.rest.dino.BreedingRequestDTO;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BabyDinoJsonDataMapperFromWebTarget implements BabyDinoMapper {

    private GenericType<BabyDinoResponseDTO> babyDinosaure = new GenericType<BabyDinoResponseDTO>() {};

    @Override
    public BabyDinoResponseDTO mapData(WebTarget data, BreedingRequestExternalDTO breedingRequestExternalDTO) {
        Invocation.Builder invocationBuilder =  data.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity
                .entity(breedingRequestExternalDTO, MediaType.APPLICATION_JSON));
        return response.readEntity(BabyDinoResponseDTO.class);
    }
}
