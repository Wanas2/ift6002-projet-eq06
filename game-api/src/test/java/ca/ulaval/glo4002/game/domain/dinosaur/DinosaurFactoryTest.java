package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.HerbivorousFoodStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DinosaurFactoryTest {
    private String A_NAME = "Bobi";
    private int A_WEIGHT = 17;
    private String A_GENDER = "f";
    private String A_SPECIES = "Ankylosaurus";

    DinosaurFactory dinosaurFactory;

    @BeforeEach
    public void setup(){
        CarnivorousFoodStorage carnivorousFoodStorage = mock(CarnivorousFoodStorage.class);
        HerbivorousFoodStorage herbivorousFoodStorage = mock(HerbivorousFoodStorage.class);
        dinosaurFactory = new DinosaurFactory(carnivorousFoodStorage,herbivorousFoodStorage);
    }

    @Test
    public void givenGenderIsNeitherMNorF_whenCreatingDinosaur_thenShouldThrowInvalidGenderException(){
        String anInvalidGender = "X";

        assertThrows(InvalidGenderException.class,
                () -> dinosaurFactory.create(anInvalidGender,A_WEIGHT,A_SPECIES,A_NAME));
    }

    @Test
    public void givenCorrectParameters_whenCreatingDinosaur_thenShouldNotThrow(){
        assertDoesNotThrow(() -> dinosaurFactory.create(A_GENDER,A_WEIGHT,A_SPECIES,A_NAME));
    }

    @Test
    public void givenWeightIsNotStrictlyPositive_whenCreatingDinosaur_thenShouldThrowInvalidWeightException(){
        int anInvalidWeight = -5;

        assertThrows(InvalidWeightException.class,
                () -> dinosaurFactory.create(A_GENDER,anInvalidWeight,A_SPECIES,A_NAME));
    }

    @Test
    public void givenSpeciesIsNotSupported_whenCreatingDinosaur_thenShouldThrowInvalidSpeciesException(){
        String anInvalidSpecies = "Labrador";

        assertThrows(InvalidSpeciesException.class,
                () -> dinosaurFactory.create(A_GENDER,A_WEIGHT,anInvalidSpecies,A_NAME));
    }
}
