package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.babyMaking.BabyFetcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class BabyFetcherFromExternalAPITest {

    private DinosaurBreederExternal dinosaurBreederExternal;
    private BabyFetcher babyFetcher;

    @BeforeEach
    void setUp() {
        dinosaurBreederExternal
                = mock(DinosaurBreederExternal.class);
        babyFetcher = new BabyFetcherFromExternalAPI(dinosaurBreederExternal);
    }

    @Test
    public void testBreeder() {
//        BabyDinoResponseDTO expectedBabyDinoResponseDTO = new BabyDinoResponseDTO();
//        expectedBabyDinoResponseDTO.gender = "m";
//        expectedBabyDinoResponseDTO.offspring = "Triceratops";
//
//        when(babyDinoJsonDataMapperFromWebTarget.mapData(any(), any())).thenReturn(expectedBabyDinoResponseDTO);
//
//        BabyDinoResponseDTO babyDinoResponseDTO = breeder.breed(any());
//
//        System.out.println(babyDinoResponseDTO.gender);
//        System.out.println(babyDinoResponseDTO.offspring);
    }
}