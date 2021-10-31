package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurAssembler;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DinosaurResourceTest {

    private final static String NON_EXISTENT_NAME = "Bob";
    private final static String AN_EXISTING_NAME = "Bobi";
    private final static int WEIGHT = 17;
    private final static String GENDER = "f";
    private final static String SPECIES = "Ankylosaurus";
    private final static int STATUS_200 = 200;

    private BreedingRequestDTO aBreedingRequestDTO;
    private DinosaurDTO aDinosaurDTO;
    private Dinosaur aDinosaur;
    private Dinosaur anotherDinosaur;
    private List<Dinosaur> dinosaurs;
    private DinosaurService dinosaurService;
    private DinosaurAssembler dinosaurAssembler;
    private DinosaurResource dinosaurResource;

    @BeforeEach
    public void setup() {
        initializeABreedingDTO();
        aDinosaurDTO = new DinosaurDTO(NON_EXISTENT_NAME, WEIGHT, GENDER, SPECIES);
        aDinosaur = mock(Dinosaur.class);
        anotherDinosaur = mock(Dinosaur.class);
        dinosaurs = new ArrayList<>();
        dinosaurService = mock(DinosaurService.class);
        dinosaurAssembler = mock(DinosaurAssembler.class);
        dinosaurResource = new DinosaurResource(dinosaurService, dinosaurAssembler);
    }

    @Test
    public void givenADinosaurDTOWithValidData_whenAddDino_thenShouldAddTheDinosaur() {
        dinosaurResource.addDino(aDinosaurDTO);

        verify(dinosaurService).
                addDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);
    }

    @Test
    public void givenADinosaurDTOWithValidData_whenAddingDinosaur_thenShouldReturnStatus200() {
        Response response = dinosaurResource.addDino(aDinosaurDTO);

        assertEquals(STATUS_200, response.getStatus());
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDino_thenDinosaurShouldBeBred() {
        dinosaurResource.breedDino(aBreedingRequestDTO);

        verify(dinosaurService).
                breedDino(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                        aBreedingRequestDTO.motherName);
    }

    @Test
    public void whenBreedDino_thenShouldReturnStatus200() {
        Response response = dinosaurResource.breedDino(aBreedingRequestDTO);

        assertEquals(STATUS_200, response.getStatus());
    }

    @Test
    public void givenTheNameOfADinosaur_whenShowDinosaur_thenShouldShowDinosaur() {
        dinosaurResource.showDinosaur(AN_EXISTING_NAME);

        verify(dinosaurService).showDinosaur(AN_EXISTING_NAME);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenTheDinosaurDTOShouldBeCreated() {
        when(dinosaurService.showDinosaur(AN_EXISTING_NAME)).thenReturn(aDinosaur);

        dinosaurResource.showDinosaur(AN_EXISTING_NAME);

        verify(dinosaurAssembler).toDTO(aDinosaur);
    }

    @Test
    public void givenTheNameOfAnAliveDinosaur_whenShowingDinosaur_thenShouldReturnStatus200() {
        Response response = dinosaurResource.showDinosaur(AN_EXISTING_NAME);

        assertEquals(STATUS_200, response.getStatus());
    }

    @Test
    public void whenShowAllDinosaurs_thenAllDinosaursShouldBeShown() {
        dinosaurResource.showAllDinosaurs();

        verify(dinosaurService).showAllDinosaurs();
    }

    @Test
    public void givenADinosaur_whenShowAllDinosaurs_thenTheDinosaureDTOShouldBeCreated() {
        dinosaurs.add(aDinosaur);
        when(dinosaurService.showAllDinosaurs()).thenReturn(dinosaurs);

        dinosaurResource.showAllDinosaurs();

        verify(dinosaurAssembler).toDTO((aDinosaur));
    }

    @Test
    public void givenMultipleDinosaurs_whenShowAllDinosaurs_thenTheDinosauresDTOShouldBeCreated() {
        dinosaurs.add(aDinosaur);
        dinosaurs.add(anotherDinosaur);
        when(dinosaurService.showAllDinosaurs()).thenReturn(dinosaurs);

        dinosaurResource.showAllDinosaurs();

        verify(dinosaurAssembler).toDTO((aDinosaur));
        verify(dinosaurAssembler).toDTO((anotherDinosaur));
    }

    @Test
    public void whenShowingAllDinosaurs_thenShouldBeStatus200() {
        Response response = dinosaurResource.showAllDinosaurs();

        assertEquals(STATUS_200, response.getStatus());
    }

    @Test
    public void whenShowingADino_thenTheServiceShouldBeCalled() {
        dinosaurResource.showDinosaur(AN_EXISTING_NAME);

        verify(dinosaurService).showDinosaur(AN_EXISTING_NAME);
    }

    @Test
    public void whenShowingAllDino_thenTheServiceShouldBeCalled() {
        dinosaurResource.showAllDinosaurs();

        verify(dinosaurService).showAllDinosaurs();
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
