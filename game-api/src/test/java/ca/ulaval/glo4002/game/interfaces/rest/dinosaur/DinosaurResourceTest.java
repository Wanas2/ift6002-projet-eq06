package ca.ulaval.glo4002.game.interfaces.rest.dinosaur;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurAssembler;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DinosaurResourceTest {

    private final static int STATUS_200_OK = 200;
    private final static String A_DINOSAUR_NAME = "Bobi";
    private final static String ANOTHER_DINOSAUR_NAME = "Bob";
    private final static int WEIGHT = 17;
    private final static String GENDER = "f";
    private final static String SPECIES = "Ankylosaurus";

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
        aDinosaurDTO = new DinosaurDTO(ANOTHER_DINOSAUR_NAME, WEIGHT, GENDER, SPECIES);
        aDinosaur = mock(Dinosaur.class);
        anotherDinosaur = mock(Dinosaur.class);
        dinosaurs = new ArrayList<>();
        dinosaurService = mock(DinosaurService.class);
        dinosaurAssembler = mock(DinosaurAssembler.class);
        dinosaurResource = new DinosaurResource(dinosaurService, dinosaurAssembler);
    }

    @Test
    public void givenADinosaurDTOWithValidData_whenAddDinosaur_thenShouldAddTheDinosaur() {
        dinosaurResource.addDinosaur(aDinosaurDTO);

        verify(dinosaurService).
                addDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);
    }

    @Test
    public void givenADinosaurDTOWithValidData_whenAddDinosaur_thenResponseStatusShouldBe200() {
        Response response = dinosaurResource.addDinosaur(aDinosaurDTO);

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void givenADinosaurDTOWithWeightNotStrictlyPositive_whenAddDinosaur_thenShouldThrowInvalidWeightException() {
        int anInvalidWeight = -5;
        aDinosaurDTO = new DinosaurDTO(A_DINOSAUR_NAME, anInvalidWeight, GENDER, SPECIES);

        assertThrows(InvalidWeightException.class,
                ()->dinosaurResource.addDinosaur(aDinosaurDTO));
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDinosaur_thenDinosaurShouldBeBred() {
        dinosaurResource.breedDinosaur(aBreedingRequestDTO);

        verify(dinosaurService).
                breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                        aBreedingRequestDTO.motherName);
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDinosaur_thenResponseStatusShouldBe200() {
        Response response = dinosaurResource.breedDinosaur(aBreedingRequestDTO);

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenShouldShowDinosaur() {
        dinosaurResource.showDinosaur(A_DINOSAUR_NAME);

        verify(dinosaurService).showDinosaur(A_DINOSAUR_NAME);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenTheDinosaurDTOShouldBeCreated() {
        when(dinosaurService.showDinosaur(A_DINOSAUR_NAME)).thenReturn(aDinosaur);

        dinosaurResource.showDinosaur(A_DINOSAUR_NAME);

        verify(dinosaurAssembler).toDTO(aDinosaur);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenResponseStatusShouldBe200() {
        Response response = dinosaurResource.showDinosaur(A_DINOSAUR_NAME);

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void whenShowAllDinosaurs_thenAllDinosaursShouldBeShown() {
        dinosaurResource.showAllDinosaurs();

        verify(dinosaurService).showAllDinosaurs();
    }

    @Test
    public void givenADinosaur_whenShowAllDinosaurs_thenTheDinosaurDTOShouldBeCreated() {
        dinosaurs.add(aDinosaur);
        when(dinosaurService.showAllDinosaurs()).thenReturn(dinosaurs);

        dinosaurResource.showAllDinosaurs();

        verify(dinosaurAssembler).toDTO((aDinosaur));
    }

    @Test
    public void givenMultipleDinosaurs_whenShowAllDinosaurs_thenTheDinosaursDTOShouldBeCreated() {
        dinosaurs.add(aDinosaur);
        dinosaurs.add(anotherDinosaur);
        when(dinosaurService.showAllDinosaurs()).thenReturn(dinosaurs);

        dinosaurResource.showAllDinosaurs();

        verify(dinosaurAssembler).toDTO((aDinosaur));
        verify(dinosaurAssembler).toDTO((anotherDinosaur));
    }

    @Test
    public void whenShowAllDinosaurs_thenResponseStatusShouldBe200() {
        Response response = dinosaurResource.showAllDinosaurs();

        assertEquals(STATUS_200_OK, response.getStatus());
    }

    @Test
    public void whenShowAllDinosaurs_thenTheServiceShouldBeCalled() {
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
