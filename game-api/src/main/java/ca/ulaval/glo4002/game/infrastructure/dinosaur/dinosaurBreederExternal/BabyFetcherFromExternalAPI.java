package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.BabyDinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.BabyFetcher;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.Optional;

public class BabyFetcherFromExternalAPI implements BabyFetcher {

    private final static String EXTERNAL_BREEDER_URI = "http://localhost:8080/breed";
    private final DinosaurBreederExternal dinoBreeder;
    private final DinosaurFactory dinosaurFactory;
    private final ParentsGenderValidator parentsGenderValidator;
    private final Client client = ClientBuilder.newClient();

    public BabyFetcherFromExternalAPI(DinosaurBreederExternal dinoBreeder, DinosaurFactory dinosaurFactory,
                                      ParentsGenderValidator parentsGenderValidator) {
        this.dinoBreeder = dinoBreeder;
        this.dinosaurFactory = dinosaurFactory;
        this.parentsGenderValidator = parentsGenderValidator;
    }

    @Override
    public Optional<BabyDinosaur> fetch(Dinosaur fatherDinosaur, Dinosaur motherDinosaur, String name) {
        parentsGenderValidator.validateParentGender(fatherDinosaur, motherDinosaur);

        BreedingAssembler breedingAssembler = new BreedingAssembler();
        BreedingRequestExternalDTO breedingRequestExternalDTO = breedingAssembler.toDTO(fatherDinosaur, motherDinosaur);

        BabyDinoResponseDTO babyDinoResponseDTO;

        try {
            babyDinoResponseDTO
                    = dinoBreeder.breed(client.target(EXTERNAL_BREEDER_URI).path("/"), breedingRequestExternalDTO);
        } catch(SpeciesWillNotBreedException e) {
            return Optional.empty();
        }

        String genderName = babyDinoResponseDTO.gender;
        String speciesName = babyDinoResponseDTO.offspring;

        BabyDinosaur babyDinosaure
                = dinosaurFactory.createBaby(genderName, speciesName, name, fatherDinosaur, motherDinosaur);
        return Optional.of(babyDinosaure);
    }
}
