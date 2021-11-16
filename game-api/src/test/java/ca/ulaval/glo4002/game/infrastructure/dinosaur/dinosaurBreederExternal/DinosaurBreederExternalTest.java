package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

public class DinosaurBreederExternalTest {

    private final static int STATUS_200_OK = 200;
    private final static int STATUS_400_BAD_REQUEST = 400;

    private DinosaurBreederExternal dinosaurBreederExternal;
    private BreedingRequestExternalDTO breedingRequestExternalDTO;
    private WebTarget externalService;
    private Response responseOfExternalService;
    private Invocation.Builder invocationBuilder;

    @BeforeEach
    void setUp() {
        externalService = mock(WebTarget.class);
        responseOfExternalService = mock(Response.class);
        invocationBuilder = mock(Invocation.Builder.class);
        when(externalService.request(MediaType.APPLICATION_JSON)).thenReturn(invocationBuilder);
        when(invocationBuilder.post(any(Entity.class))).thenReturn(responseOfExternalService);

        dinosaurBreederExternal = new DinosaurBreederExternal();
        breedingRequestExternalDTO = new BreedingRequestExternalDTO();
        breedingRequestExternalDTO.fatherSpecies = "aSpecies";
        breedingRequestExternalDTO.motherSpecies = "anotherSpecies";
    }

    @Test
    public void givenExternalServiceAnswersWithOk_whenBreed_thenTheBabyResponseDTOFromTheResponseShouldBeReturned() throws SpeciesWillNotBreedException {
        BabyDinosaurResponseDTO expectedBabyDinosaurResponseDTO = new BabyDinosaurResponseDTO();
        expectedBabyDinosaurResponseDTO.gender = "aGender";
        expectedBabyDinosaurResponseDTO.offspring = "anOffspring";
        when(responseOfExternalService.getStatus()).thenReturn(STATUS_200_OK);
        when(responseOfExternalService.readEntity(BabyDinosaurResponseDTO.class))
                .thenReturn(expectedBabyDinosaurResponseDTO);

        BabyDinosaurResponseDTO fetchedBabyDinosaurResponseDTO
                = dinosaurBreederExternal.breed(externalService,breedingRequestExternalDTO);

        assertEquals(expectedBabyDinosaurResponseDTO,fetchedBabyDinosaurResponseDTO);
    }

    @Test
    public void givenExternalServiceAnswersWithBadRequest_whenBreed_thenSpeciesWillNotBreedExceptionShouldBeThrown() {
        when(responseOfExternalService.getStatus()).thenReturn(STATUS_400_BAD_REQUEST);

        assertThrows(SpeciesWillNotBreedException.class,
                () -> dinosaurBreederExternal.breed(externalService,breedingRequestExternalDTO));
    }

    @Test
    public void whenBreed_thenInvocationBuilderShouldPostTheEntity() throws SpeciesWillNotBreedException {
        dinosaurBreederExternal.breed(externalService,breedingRequestExternalDTO);

        ArgumentMatcher<Entity<BreedingRequestExternalDTO>> isTheEntityWithJsonMediaTypeAndContainingTheDTO =
                entity -> entity.getEntity().equals(breedingRequestExternalDTO) &&
                        entity.getMediaType().toString().equals(MediaType.APPLICATION_JSON);
        verify(invocationBuilder).post(argThat(isTheEntityWithJsonMediaTypeAndContainingTheDTO));
    }
}
