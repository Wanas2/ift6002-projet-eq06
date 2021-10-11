package ca.ulaval.glo4002.game.infrastructure.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Breeder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BreederFromExternalAPITest {

    private BabyDinoJsonDataMapperFromWebTarget babyDinoJsonDataMapperFromWebTarget;
    private Breeder breeder;

    @BeforeEach
    void setUp() {
        babyDinoJsonDataMapperFromWebTarget
                = mock(BabyDinoJsonDataMapperFromWebTarget.class);
        breeder = new BreederFromExternalAPI(babyDinoJsonDataMapperFromWebTarget);
    }

    @Test
    public void testBreeder() {
        BabyDinoReponseDTO expectedBabyDinoReponseDTO = new BabyDinoReponseDTO();
        expectedBabyDinoReponseDTO.gender = "m";
        expectedBabyDinoReponseDTO.offspring = "Triceratops";

        when(babyDinoJsonDataMapperFromWebTarget.mapData(any())).thenReturn(expectedBabyDinoReponseDTO);

        BabyDinoReponseDTO babyDinoReponseDTO = breeder.breed("", "");

        System.out.println(babyDinoReponseDTO.gender);
        System.out.println(babyDinoReponseDTO.offspring);
    }
}