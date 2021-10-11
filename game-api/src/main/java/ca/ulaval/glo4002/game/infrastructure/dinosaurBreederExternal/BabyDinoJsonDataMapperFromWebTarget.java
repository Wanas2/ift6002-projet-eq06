package ca.ulaval.glo4002.game.infrastructure.dinosaurBreederExternal;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BabyDinoJsonDataMapperFromWebTarget implements BabyDinoMapper {

    private GenericType<BabyDinoReponseDTO> babyDinosaure = new GenericType<BabyDinoReponseDTO>() {};
    private BabyDinoRequestDTO breedingRequest = new BabyDinoRequestDTO() {};

    @Override
    public BabyDinoReponseDTO mapData(WebTarget data) {
        breedingRequest.fatherSpecies = "Diplodocus"; // Todo Utiliser un Assembler
        breedingRequest.motherSpecies = "Stegosaurus";

        Invocation.Builder invocationBuilder =  data.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.entity(breedingRequest, MediaType.APPLICATION_JSON));

        return response.readEntity(BabyDinoReponseDTO.class);
    }
}
