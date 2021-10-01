package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurRepository;
import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;
import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DinosaurResourceTests {
    String NON_EXISTENT_NAME = "Bob";
    String EXISTENT_NAME = "Bobi";
    int WEIGHT = 17;
    String GENDER = "f";
    String SPECIES = "Ankylosaurus";

    DinosaurResource dinosaurResource;
    DinosaurRepository dinosaurRepository;

    @BeforeEach
    public void setup(){
        dinosaurRepository = mock(DinosaurRepository.class);
        when(dinosaurRepository.existsByName(NON_EXISTENT_NAME)).thenReturn(false);
        when(dinosaurRepository.existsByName(EXISTENT_NAME)).thenReturn(true);

        dinosaurResource = new DinosaurResource(new DinosaurRequestsValidator(dinosaurRepository));
    }

    @Test
    public void givenRequestGenderIsNeitherMNorF_whenAddingDinosaur_thenShouldThrowExceptionWithInvalidGender(){
        String invalidGender = "A";
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,invalidGender,SPECIES);

        try{
            dinosaurResource.addDino(request);
            fail("Should have thrown GeneralBadRequestException");
        } catch (GeneralBadRequestException exception){
            assertEquals("INVALID_GENDER", exception.getError());
        }
    }

    @Test
    public void givenACorrectRequest_whenAddingDinosaur_thenResponseShouldBeStatus200(){
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,GENDER,SPECIES);

        Response response = dinosaurResource.addDino(request);

        assertEquals(200,response.getStatus());
    }

    @Test
    public void givenRequestWeightIsNotStrictlyPositive_whenAddingDinosaur_thenShouldThrowExceptionWithInvalidWeight(){
        int invalidWeight = -5;
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,invalidWeight,GENDER,SPECIES);

        try{
            dinosaurResource.addDino(request);
            fail("Should have thrown GeneralBadRequestException");
        } catch (GeneralBadRequestException exception){
            assertEquals("INVALID_WEIGHT", exception.getError());
        }
    }

    @Test
    public void givenRequestSpeciesIsNotSupported_whenAddingDinosaur_thenShouldThrowExceptionWithInvalidSpecies(){
        String invalidSpecies = "Labrador";
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,GENDER,invalidSpecies);

        try{
            dinosaurResource.addDino(request);
            fail("Should have thrown GeneralBadRequestException");
        } catch (GeneralBadRequestException exception){
            assertEquals("INVALID_SPECIES", exception.getError());
        }
    }

    @Test
    public void givenRequestNameAlreadyExists_whenAddingDinosaur_thenShouldThrowExceptionWithDuplicateName(){
        DinosaurDTO request = new DinosaurDTO(EXISTENT_NAME,WEIGHT,GENDER,SPECIES);

        try{
            dinosaurResource.addDino(request);
            fail("Should have thrown GeneralBadRequestException");
        } catch (GeneralBadRequestException exception){
            assertEquals("DUPLICATE_NAME", exception.getError());
        }
    }

    @Test
    public void givenRequestNameMatchingAliveDino_whenShowingDinosaur_thenShouldBeStatus200(){
        Response response = dinosaurResource.showDino(EXISTENT_NAME);

        assertEquals(200,response.getStatus());
    }

    @Test
    public void givenRequestNameMatchingNoDino_whenShowingDinosaur_thenShouldThrowExceptionWithNonExistentName(){
        try{
            dinosaurResource.showDino(NON_EXISTENT_NAME);
            fail("Should have thrown GeneralNotFoundException");
        } catch (GeneralNotFoundException exception){
            assertEquals("NON_EXISTENT_NAME", exception.getError());
        }
    }

    @Test
    public void whenShowingAllDinosaurs_thenShouldBeStatus200(){
        Response response = dinosaurResource.showAllDino();
        assertEquals(200,response.getStatus());
    }

    //TODO : les tests pour vérifier que la resource appelle bien les services
}
