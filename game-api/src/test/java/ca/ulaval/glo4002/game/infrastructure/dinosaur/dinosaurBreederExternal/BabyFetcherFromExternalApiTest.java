package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.WebTarget;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BabyFetcherFromExternalApiTest {

    private BabyFetcherFromExternalAPI fetcher;
    private DinosaurBreederExternal breeder;
    private DinosaurFactory factory;
    private Dinosaur A_MALE_DINOSAUR;
    private Dinosaur A_FEMALE_DINOSAUR;
    private BabyDinosaur A_BABY_DINOSAUR;
    private static final String BABY_NAME = "Marie";

    @BeforeEach
    public void setup(){
        breeder = mock(DinosaurBreederExternal.class);
        factory = mock(DinosaurFactory.class);
        ParentsGenderValidator validator = mock(ParentsGenderValidator.class);

        String maleName = "Bob";
        String femaleName = "Helene";
        int weight = 17;

        fetcher = new BabyFetcherFromExternalAPI(breeder,factory, validator);
        A_MALE_DINOSAUR = new Dinosaur(Species.Spinosaurus,weight,maleName, Gender.M,
                mock(FoodConsumptionStrategy.class));
        A_FEMALE_DINOSAUR = new Dinosaur(Species.Spinosaurus,weight,femaleName, Gender.F,
                mock(FoodConsumptionStrategy.class));
        A_BABY_DINOSAUR = new BabyDinosaur(Species.Spinosaurus,BABY_NAME,Gender.M,
                mock(FoodConsumptionStrategy.class),A_MALE_DINOSAUR,A_FEMALE_DINOSAUR);
    }

    @Test
    public void givenAFatherAndAMotherDinosaur_whenFetch_thenShouldReturnABaby()
            throws SpeciesWillNotBreedException {
        BabyDinoResponseDTO responseDTO = new BabyDinoResponseDTO();
        responseDTO.gender = "F";
        responseDTO.offspring = "Spinosaurus";
        when(breeder.breed(any(WebTarget.class),any(BreedingRequestExternalDTO.class)))
                .thenReturn(responseDTO);
        when(factory.createBaby(responseDTO.gender,responseDTO.offspring,BABY_NAME,
                A_MALE_DINOSAUR,A_FEMALE_DINOSAUR)).thenReturn(A_BABY_DINOSAUR);

        Optional<BabyDinosaur> babyDinosaur = fetcher.fetch(A_MALE_DINOSAUR,A_FEMALE_DINOSAUR,BABY_NAME);

        assertTrue(babyDinosaur.isPresent());
    }

    @Test
    public void givenAFatherAndAMotherDinosaurThatWontBreed_whenFetch_thenShouldNotReturnABaby()
            throws SpeciesWillNotBreedException {
        when(breeder.breed(any(WebTarget.class),any(BreedingRequestExternalDTO.class)))
                .thenThrow(new SpeciesWillNotBreedException(""));

        Optional<BabyDinosaur> babyDinosaur = fetcher.fetch(A_MALE_DINOSAUR,A_FEMALE_DINOSAUR,BABY_NAME);

        assertTrue(babyDinosaur.isEmpty());
    }
}
