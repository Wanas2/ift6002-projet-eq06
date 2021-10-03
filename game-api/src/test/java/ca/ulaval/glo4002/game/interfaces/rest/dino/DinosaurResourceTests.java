package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.applicationService.GameService;
import ca.ulaval.glo4002.game.domain.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DinosaurResourceTests {
    String NON_EXISTENT_NAME = "Bob";
    String EXISTENT_NAME = "Bobi";
    int WEIGHT = 17;
    String GENDER = "f";
    String SPECIES = "Ankylosaurus";
    DinosaurResource dinosaurResource;
    DinosaurRequestsValidator requestsValidator;
    GameService gameService;

    @BeforeEach
    public void setup(){
        requestsValidator = mock(DinosaurRequestsValidator.class);
        gameService = mock(GameService.class);
        dinosaurResource = new DinosaurResource(gameService, requestsValidator);
    }

    @Test
    public void whenAddingDinosaur_thenShouldAskForValidation(){
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,GENDER,SPECIES);

        dinosaurResource.addDino(request);

        verify(requestsValidator).validateAddRequest(request);
    }

    @Test
    public void whenShowingDinosaur_thenShouldAskForValidation(){
        dinosaurResource.showDino(EXISTENT_NAME);

        verify(requestsValidator).validateShowRequest(EXISTENT_NAME);
    }

    @Test
    public void givenCorrectRequest_whenAddingDinosaur_thenShouldBeStatus200(){
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,GENDER,SPECIES);

        Response response = dinosaurResource.addDino(request);

        assertEquals(200,response.getStatus());
    }

    @Test
    public void givenRequestNameMatchingAliveDino_whenShowingDinosaur_thenShouldBeStatus200(){
        Response response = dinosaurResource.showDino(EXISTENT_NAME);

        assertEquals(200,response.getStatus());
    }

    @Test
    public void whenShowingAllDinosaurs_thenShouldBeStatus200(){
        Response response = dinosaurResource.showAllDino();
        assertEquals(200,response.getStatus());
    }

    //TODO : les tests pour vérifier que la resource appelle bien les services
}
