package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.applicationService.DinosaurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DinosaurResourceTests {

    private final static String NON_EXISTENT_NAME = "Bob";
    private final static String EXISTENT_NAME = "Bobi";
    private final static int WEIGHT = 17;
    private final static String GENDER = "f";
    private final static String SPECIES = "Ankylosaurus";
    private final static int CORRECT_STATUS = 200;
    private DinosaurResource dinosaurResource;
    private DinosaurService dinosaurService;

    @BeforeEach
    public void setup(){
        dinosaurService = mock(DinosaurService.class);
        dinosaurResource = new DinosaurResource(dinosaurService);
    }

    @Test
    public void givenCorrectRequest_whenAddingDinosaur_thenShouldBeStatus200(){
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,GENDER,SPECIES);

        Response response = dinosaurResource.addDino(request);

        assertEquals(CORRECT_STATUS,response.getStatus());
    }

    @Test
    public void givenRequestNameMatchingAliveDino_whenShowingDinosaur_thenShouldBeStatus200(){
        Response response = dinosaurResource.showDino(EXISTENT_NAME);

        assertEquals(CORRECT_STATUS,response.getStatus());
    }

    @Test
    public void whenShowingAllDinosaurs_thenShouldBeStatus200(){
        Response response = dinosaurResource.showAllDino();
        assertEquals(CORRECT_STATUS,response.getStatus());
    }

    @Test
    public void whenShowingADino_thenTheServiceShouldBeCalled(){
        dinosaurResource.showDino(EXISTENT_NAME);

        verify(dinosaurService).showDinosaur(EXISTENT_NAME);
    }

    @Test
    public void whenShowingAllDino_thenTheServiceShouldBeCalled(){
        dinosaurResource.showAllDino();

        verify(dinosaurService).showAllDinosaurs();
    }
}
