package ca.ulaval.glo4002.game.infrastructure.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Breeder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class BreederFromExternalAPI implements Breeder {

    private final static String EXTERNAL_BREEDER_URI = "http://localhost:8080/breed";
    private final BabyDinoMapper babyDinoMapper;
    private final Client client = ClientBuilder.newClient();

    public BreederFromExternalAPI(BabyDinoMapper babyDinoMapper) {
        this.babyDinoMapper = babyDinoMapper;
    }

    @Override
    public BabyDinoResponseDTO breed(BreedingRequestExternalDTO breedingRequestExternalDTO) {
        return babyDinoMapper.mapData(client.target(EXTERNAL_BREEDER_URI).path("/"), breedingRequestExternalDTO);
    }
}
