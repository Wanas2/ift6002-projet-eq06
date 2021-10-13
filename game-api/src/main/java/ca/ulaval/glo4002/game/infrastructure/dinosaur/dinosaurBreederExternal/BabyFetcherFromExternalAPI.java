package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.babyMaking.BabyFetcher;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class BabyFetcherFromExternalAPI implements BabyFetcher {

    private final static String EXTERNAL_BREEDER_URI = "http://localhost:8080/breed";
    private final DinoBreeder dinoBreeder;
    private final Client client = ClientBuilder.newClient();

    public BabyFetcherFromExternalAPI(DinoBreeder dinoBreeder) {
        this.dinoBreeder = dinoBreeder;
    }

    @Override
    public BabyDinoResponseDTO fetch(Dinosaur fatherDinosaur, Dinosaur motherDinosaur)
            throws SpeciesWillNotBreedException {
        BreedingAssembler breedingAssembler = new BreedingAssembler();
        BreedingRequestExternalDTO breedingRequestExternalDTO = breedingAssembler.toDTO(fatherDinosaur, motherDinosaur);

        BabyDinoResponseDTO babyDinoResponseDTO
                = dinoBreeder.breed(client.target(EXTERNAL_BREEDER_URI).path("/"), breedingRequestExternalDTO);

        // Todo Créer le bébé dinosaure ici???

        return dinoBreeder.breed(client.target(EXTERNAL_BREEDER_URI).path("/"), breedingRequestExternalDTO);
    }
}
