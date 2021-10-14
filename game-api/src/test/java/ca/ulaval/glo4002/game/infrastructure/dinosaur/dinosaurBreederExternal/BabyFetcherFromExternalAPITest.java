package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.WebTarget;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BabyFetcherFromExternalAPITest {

    private DinosaurBreederExternal dinosaurBreederExternal;
    private DinosaurFactory dinosaurFactory;
    private ParentsGenderValidator parentsGenderValidator;
    private BabyFetcherFromExternalAPI babyFetcherFromExternalAPI;

    @BeforeEach
    void setUp() {
        dinosaurBreederExternal = mock(DinosaurBreederExternal.class);
        dinosaurFactory = mock(DinosaurFactory.class);
        parentsGenderValidator = new ParentsGenderValidator();
        babyFetcherFromExternalAPI = new BabyFetcherFromExternalAPI(dinosaurBreederExternal, dinosaurFactory,
                parentsGenderValidator);
    }

    @Test
    public void givenAFatherDinoAndAMotherDino_whenFetch_thenABabyShouldBeCreated()
            throws SpeciesWillNotBreedException {


    }
}