package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DinosaurResourceTest {

    private final static String NON_EXISTENT_NAME = "Bob";
    private final static String EXISTENT_NAME = "Bobi";
    private final static int WEIGHT = 17;
    private final static String GENDER = "f";
    private final static String SPECIES = "Ankylosaurus";
    private final static int STATUS_200 = 200;
    private BreedingRequestDTO aBreedingRequestDTO;
    private DinosaurResource dinosaurResource;
    private DinosaurService dinosaurService;

    @BeforeEach
    public void setup() {
        initializeABreedingDTO();
        dinosaurService = mock(DinosaurService.class);
        dinosaurResource = new DinosaurResource(dinosaurService);
    }

    @Test
    public void givenCorrectRequest_whenAddingDinosaur_thenShouldBeStatus200() {
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME, WEIGHT, GENDER, SPECIES);

        Response response = dinosaurResource.addDino(request);

        assertEquals(STATUS_200, response.getStatus());
    }

    @Test
    public void givenRequestNameMatchingAliveDino_whenShowingDinosaur_thenShouldBeStatus200() {
        Response response = dinosaurResource.showDino(EXISTENT_NAME);

        assertEquals(STATUS_200, response.getStatus());
    }

    @Test
    public void whenShowingAllDinosaurs_thenShouldBeStatus200() {
        Response response = dinosaurResource.showAllDinosaurs();

        assertEquals(STATUS_200, response.getStatus());
    }

    @Test
    public void whenShowingADino_thenTheServiceShouldBeCalled() {
        dinosaurResource.showDino(EXISTENT_NAME);

        verify(dinosaurService).showDinosaur(EXISTENT_NAME);
    }

    @Test
    public void whenShowingAllDino_thenTheServiceShouldBeCalled() {
        dinosaurResource.showAllDinosaurs();

        verify(dinosaurService).showAllDinosaurs();
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDino_thenDinosaurShouldBeBred() {
        dinosaurResource.breedDino(aBreedingRequestDTO);

        verify(dinosaurService).breedDino(aBreedingRequestDTO);
    }

    @Test
    public void whenBreedDino_thenShouldReturnStatus200() {
        Response response = dinosaurResource.breedDino(aBreedingRequestDTO);

        assertEquals(STATUS_200, response.getStatus());
    }

    private void initializeABreedingDTO() {
        aBreedingRequestDTO = new BreedingRequestDTO();
        String babyName = "wrrwrww";
        String theFathersName = "wgrwr";
        String theMothersName = "mko";
        aBreedingRequestDTO.name = babyName;
        aBreedingRequestDTO.fatherName = theFathersName;
        aBreedingRequestDTO.motherName = theMothersName;
    }
}
