package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.DinosaurAlreadyParticipatingException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.MaxCombatsReachedException;
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
    private Dinosaur firstFighter;
    private Dinosaur secondFighter;
    private SumoFightOrganizerValidator sumoFightOrganizerValidator;

    @BeforeEach
    public void setUp() {
        firstFighter = mock(Dinosaur.class);
        secondFighter = mock(Dinosaur.class);

        sumoFightOrganizerValidator = new SumoFightOrganizerValidator();
    }

    @Test
    public void givenDinosaursHaveNotFought_whenValidateFighter_thenShouldNotThrowDinosaurAlreadyParticipatingException() {
        assertDoesNotThrow(() -> sumoFightOrganizerValidator.validateSumoFighter(dinosaursAlreadyFought, firstFighter, secondFighter));
    }

    @Test
    public void givenDinosaurAlreadyFighting_whenValidateFighter_thenShouldThrowDinosaurAlreadyParticipatingException() {
        dinosaursAlreadyFought.add(firstFighter);

        assertThrows(DinosaurAlreadyParticipatingException.class,
                () -> sumoFightOrganizerValidator.validateSumoFighter(dinosaursAlreadyFought, firstFighter, secondFighter));
    }

    @Test
    public void givenNumberOfFightsDoneLessThanMaxNumberFights_whenValidateSumoFight_thenShouldNotThrowMaxCombatsReachedException() {
        assertDoesNotThrow(() -> sumoFightOrganizerValidator.validateSumoFight(NUMBER_OF_FIGHTS_DONE, MAX_NUMBER_OF_FIGHTS_PER_TURN));
    }

    @Test
    public void givenNumberOfFightsDoneEqualToMaxNumberFights_whenValidateSumoFight_thenShouldThrowMaxCombatsReachedException() {
        assertThrows(MaxCombatsReachedException.class,
                ()-> sumoFightOrganizerValidator.validateSumoFight(MAX_NUMBER_OF_FIGHTS_PER_TURN, MAX_NUMBER_OF_FIGHTS_PER_TURN));
    }
}
