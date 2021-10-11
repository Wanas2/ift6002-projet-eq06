package ca.ulaval.glo4002.game.infrastructure.dinosaurBreederExternal;

import javax.ws.rs.client.WebTarget;

public interface BabyDinoMapper {

    BabyDinoResponseDTO mapData(WebTarget data, BreedingRequestExternalDTO breedingRequestExternalDTO);
}
