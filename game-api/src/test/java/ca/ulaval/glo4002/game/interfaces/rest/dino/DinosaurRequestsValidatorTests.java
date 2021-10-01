package ca.ulaval.glo4002.game.interfaces.rest.dino;

import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurRepository;
import ca.ulaval.glo4002.game.interfaces.rest.dino.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DinosaurRequestsValidatorTests {
    String NON_EXISTENT_NAME = "Bob";
    String EXISTENT_NAME = "Bobi";
    int WEIGHT = 17;
    String GENDER = "f";
    String SPECIES = "Ankylosaurus";

    DinosaurRequestsValidator requestsValidator;
    DinosaurRepository dinosaurRepository;

    @BeforeEach
    public void setup(){
        dinosaurRepository = mock(DinosaurRepository.class);
        when(dinosaurRepository.existsByName(NON_EXISTENT_NAME)).thenReturn(false);
        when(dinosaurRepository.existsByName(EXISTENT_NAME)).thenReturn(true);

        requestsValidator = new DinosaurRequestsValidator(dinosaurRepository);
    }

    @Test
    public void givenRequestGenderIsNeitherMNorF_whenAddingDinosaur_thenShouldThrowInvalidGenderException(){
        String invalidGender = "A";
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,invalidGender,SPECIES);

        assertThrows(InvalidGenderException.class, () -> requestsValidator.validateAddRequest(request));
    }

    @Test
    public void givenACorrectRequest_whenAddingDinosaur_thenShouldNotThrow(){
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,GENDER,SPECIES);

        assertDoesNotThrow(() -> requestsValidator.validateAddRequest(request));
    }

    @Test
    public void givenRequestWeightIsNotStrictlyPositive_whenAddingDinosaur_thenShouldThrowInvalidWeightException(){
        int invalidWeight = -5;
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,invalidWeight,GENDER,SPECIES);

        assertThrows(InvalidWeightException.class,() -> requestsValidator.validateAddRequest(request));
    }

    @Test
    public void givenRequestSpeciesIsNotSupported_whenAddingDinosaur_thenShouldThrowInvalidSpeciesException(){
        String invalidSpecies = "Labrador";
        DinosaurDTO request = new DinosaurDTO(NON_EXISTENT_NAME,WEIGHT,GENDER,invalidSpecies);

        assertThrows(InvalidSpeciesException.class,() -> requestsValidator.validateAddRequest(request));
    }

    @Test
    public void givenRequestNameAlreadyExists_whenAddingDinosaur_thenShouldThrowDuplicateNameException(){
        DinosaurDTO request = new DinosaurDTO(EXISTENT_NAME,WEIGHT,GENDER,SPECIES);

        assertThrows(DuplicateNameException.class,() -> requestsValidator.validateAddRequest(request));
    }

    @Test
    public void givenRequestNameMatchingAliveDino_whenShowingDinosaur_thenShouldNotThrow(){
        assertDoesNotThrow(() -> requestsValidator.validateShowRequest(EXISTENT_NAME));
    }

    @Test
    public void givenRequestNameMatchingNoDino_whenShowingDinosaur_thenShouldThrowNonExistentNameException(){
        assertThrows(NonExistentNameException.class,() -> requestsValidator.validateShowRequest(NON_EXISTENT_NAME));
    }
}
