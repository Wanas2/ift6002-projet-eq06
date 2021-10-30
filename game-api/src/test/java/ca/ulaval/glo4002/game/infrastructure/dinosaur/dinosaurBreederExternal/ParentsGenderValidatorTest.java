package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Gender;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidFatherException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidMotherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ParentsGenderValidatorTest {

    private final Species A_SPECIES = Species.Diplodocus;
    private final int SOMME_WEIGHT = 134;
    private final String A_NAME = "ehwr";
    private final String ANOTHER_NAME = "ehwrwfgh";
    private final Gender THE_MALE_GENDER = Gender.M;
    private final Gender THE_FEMALE_GENDER = Gender.F;

    private FoodConsumptionStrategy aFoodConsumptionStrategy;
    private Dinosaur aMaleDinosaur;
    private Dinosaur aFemaleDinosaur;
    private ParentsGenderValidator parentsGenderValidator;

    @BeforeEach
    void setUp() {
        aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        aFemaleDinosaur = new Dinosaur(A_SPECIES, SOMME_WEIGHT, A_NAME, THE_FEMALE_GENDER, aFoodConsumptionStrategy);
        aMaleDinosaur = new Dinosaur(A_SPECIES, SOMME_WEIGHT, ANOTHER_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy);
        parentsGenderValidator = new ParentsGenderValidator();
    }

    @Test
    public void givenAFatherDinoOfWrongGender_whenValidateParentGender_thenInvalidFatherException() {
        Gender wrongGender = Gender.F;
        Dinosaur aFatherDinosaurOfTheWrongGender =
                new Dinosaur(A_SPECIES, SOMME_WEIGHT, A_NAME, wrongGender, aFoodConsumptionStrategy);

        assertThrows(InvalidFatherException.class,
                ()->parentsGenderValidator.validateParentGender(aFatherDinosaurOfTheWrongGender, aFemaleDinosaur));
    }

    @Test
    public void givenAMotherDinoOfWrongGender_whenValidateParentGender_thenInvalidMotherException() {
        Gender wrongGender = Gender.M;
        Dinosaur aMotherDinosaurOfTheWrongGender =
                new Dinosaur(A_SPECIES, SOMME_WEIGHT, A_NAME, wrongGender, aFoodConsumptionStrategy);

        assertThrows(InvalidMotherException.class,
                ()->parentsGenderValidator.validateParentGender(aMaleDinosaur, aMotherDinosaurOfTheWrongGender));
    }

    @Test
    public void givenAFatherDinoAndAMotherDinoAllOfCorrectGender_whenValidateParentGender_thenNoExceptionShouldBeThrown() {
        assertDoesNotThrow(()->parentsGenderValidator.validateParentGender(aMaleDinosaur, aFemaleDinosaur));
    }
}
