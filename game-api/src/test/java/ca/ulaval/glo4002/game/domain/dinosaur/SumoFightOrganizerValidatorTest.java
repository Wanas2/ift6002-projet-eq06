package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.sumoFight.exceptions.ArmsTooShortException;
import ca.ulaval.glo4002.game.domain.dinosaur.sumoFight.exceptions.DinosaurAlreadyParticipatingException;
import ca.ulaval.glo4002.game.domain.dinosaur.sumoFight.exceptions.MaxCombatsReachedException;
import ca.ulaval.glo4002.game.domain.dinosaur.sumoFight.SumoFightOrganizerValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class SumoFightOrganizerValidatorTest {

    private final static int MAX_NUMBER_OF_FIGHTS_PER_TURN = 2;
    private final static int NUMBER_OF_FIGHTS_DONE = 1;

    private final List<Dinosaur> dinosaursAlreadyFought = new ArrayList<>();
    private Dinosaur aDinosaur;
    private Dinosaur anotherDinosaur;
    private Dinosaur aTyrannosaurusRex;
    private SumoFightOrganizerValidator sumoFightOrganizerValidator;

    @BeforeEach
    public void setUp() {
        FoodConsumptionStrategy foodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        aDinosaur = new AdultDinosaur(Species.Allosaurus,476,"David",Gender.M,foodConsumptionStrategy);
        anotherDinosaur = new AdultDinosaur(Species.Allosaurus,276,"Bob",Gender.M,foodConsumptionStrategy);
        aTyrannosaurusRex = new AdultDinosaur(Species.TyrannosaurusRex,152,"Jean",Gender.F,
                foodConsumptionStrategy);

        sumoFightOrganizerValidator = new SumoFightOrganizerValidator();
    }

    @Test
    public void givenDinosaursHaveNotFought_whenValidateFighters_thenShouldNotThrowDinosaurAlreadyParticipatingException() {
        assertDoesNotThrow(() -> sumoFightOrganizerValidator.validateSumoFighters(dinosaursAlreadyFought,
                aDinosaur, anotherDinosaur));
    }

    @Test
    public void givenDinosaurAlreadyFighting_whenValidateFighters_thenShouldThrowDinosaurAlreadyParticipatingException() {
        dinosaursAlreadyFought.add(aDinosaur);

        assertThrows(DinosaurAlreadyParticipatingException.class,
                () -> sumoFightOrganizerValidator.validateSumoFighters(dinosaursAlreadyFought,
                        aDinosaur, anotherDinosaur));
    }

    @Test
    public void givenADinosaurIsTyrannosaurusRex_whenValidateFighters_thenShouldThrowArmsTooShortException() {
        assertThrows(ArmsTooShortException.class,
                () -> sumoFightOrganizerValidator.validateSumoFighters(dinosaursAlreadyFought,
                        aDinosaur, aTyrannosaurusRex));
    }

    @Test
    public void givenNumberOfFightsDoneLessThanMaxNumberFights_whenValidateSumoFight_thenShouldNotThrowMaxCombatsReachedException() {
        assertDoesNotThrow(() -> sumoFightOrganizerValidator.validateSumoFight(NUMBER_OF_FIGHTS_DONE,
                MAX_NUMBER_OF_FIGHTS_PER_TURN));
    }

    @Test
    public void givenNumberOfFightsDoneEqualToMaxNumberFights_whenValidateSumoFight_thenShouldThrowMaxCombatsReachedException() {
        assertThrows(MaxCombatsReachedException.class,
                ()-> sumoFightOrganizerValidator.validateSumoFight(MAX_NUMBER_OF_FIGHTS_PER_TURN,
                        MAX_NUMBER_OF_FIGHTS_PER_TURN));
    }
}
