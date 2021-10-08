package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.HerbivorousFoodStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class DinosaurFactoryTests {
    String A_NAME = "Bobi";
    int A_WEIGHT = 17;
    String A_GENDER = "f";
    String A_SPECIES = "Ankylosaurus";

    DinosaurFactory dinosaurFactory;

    @BeforeEach
    public void setup(){
        CarnivorousFoodStorage carnivorousFoodStorage = mock(CarnivorousFoodStorage.class);
        HerbivorousFoodStorage herbivorousFoodStorage = mock(HerbivorousFoodStorage.class);
        dinosaurFactory = new DinosaurFactory(carnivorousFoodStorage,herbivorousFoodStorage);
    }

    @Test
    public void givenGenderIsNeitherMNorF_whenCreatingDinosaur_thenShouldThrowInvalidGenderException(){
        String invalidGender = "X";

        assertThrows(InvalidGenderException.class,
                () -> dinosaurFactory.create(invalidGender,A_WEIGHT,A_SPECIES,A_NAME));
    }

    @Test
    public void givenACorrect_whenCreatingDinosaur_thenShouldNotThrow(){
        assertDoesNotThrow(() -> dinosaurFactory.create(A_GENDER,A_WEIGHT,A_SPECIES,A_NAME));
    }

    @Test
    public void givenWeightIsNotStrictlyPositive_whenCreatingDinosaur_thenShouldThrowInvalidWeightException(){
        int invalidWeight = -5;

        assertThrows(InvalidWeightException.class,
                () -> dinosaurFactory.create(A_GENDER,invalidWeight,A_SPECIES,A_NAME));
    }

    @Test
    public void givenSpeciesIsNotSupported_whenCreatingDinosaur_thenShouldThrowInvalidSpeciesException(){
        String invalidSpecies = "Labrador";

        assertThrows(InvalidSpeciesException.class,
                () -> dinosaurFactory.create(A_GENDER,A_WEIGHT,invalidSpecies,A_NAME));
    }
}
