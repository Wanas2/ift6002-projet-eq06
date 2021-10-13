package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.BabyFetcher;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

public class BabyFetcherFromExternalAPI implements BabyFetcher {

    private final static String EXTERNAL_BREEDER_URI = "http://localhost:8080/breed";
    private final DinosaurBreederExternal dinoBreeder;
    private final DinosaurFactory dinosaurFactory;
    private final Client client = ClientBuilder.newClient();

    public BabyFetcherFromExternalAPI(DinosaurBreederExternal dinoBreeder, DinosaurFactory dinosaurFactory) {
        this.dinoBreeder = dinoBreeder;
        this.dinosaurFactory = dinosaurFactory;
    }

    @Override
    public Dinosaur fetch(Dinosaur fatherDinosaur, Dinosaur motherDinosaur, String name) {
        BreedingAssembler breedingAssembler = new BreedingAssembler();
        BreedingRequestExternalDTO breedingRequestExternalDTO = breedingAssembler.toDTO(fatherDinosaur, motherDinosaur);

        BabyDinoResponseDTO babyDinoResponseDTO = new BabyDinoResponseDTO();
        try {
            babyDinoResponseDTO
                    = dinoBreeder.breed(client.target(EXTERNAL_BREEDER_URI).path("/"), breedingRequestExternalDTO);
        } catch (SpeciesWillNotBreedException ignored) {
        }
        String genderName = babyDinoResponseDTO.gender;
        String speciesName = babyDinoResponseDTO.offspring;

        return dinosaurFactory.createBaby(genderName, speciesName, name, fatherDinosaur, motherDinosaur);
    }
}
