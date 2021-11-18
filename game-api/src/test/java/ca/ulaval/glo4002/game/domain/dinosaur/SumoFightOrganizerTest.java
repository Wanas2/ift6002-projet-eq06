package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.DinosaurAlreadyParticipatingException;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.MaxCombatsReachedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SumoFightOrganizerTest {

    private final static String DINOSAUR_NAME = "Bob";
    private final static String RESULT_DRAW = "tie";
    private final static int POSITIVE_DIFFERENCE = 1;
    private final static int NULL_DIFFERENCE = 0;

    private Dinosaur aStrongerDinosaur;
    private Dinosaur aDinosaur;
    private Dinosaur aDinosaurWithSameStrength;
    private SumoFightOrganizer sumoFightOrganizer;

    @BeforeEach
    public void setUp() {
        aStrongerDinosaur = mock(Dinosaur.class);
        aDinosaur = mock(Dinosaur.class);
        aDinosaurWithSameStrength = mock(Dinosaur.class);

        SumoFightOrganizerValidator sumoFightOrganizerValidator = new SumoFightOrganizerValidator();
        sumoFightOrganizer = new SumoFightOrganizer(sumoFightOrganizerValidator);
    }

    @Test
    public void givenTwoDinosaursWithSameStrength_whenSumoFight_thenFightShouldBeTie() {
        when(aDinosaurWithSameStrength.compareStrength(aDinosaurWithSameStrength)).thenReturn(NULL_DIFFERENCE);

        String result = sumoFightOrganizer.sumoFight(aDinosaurWithSameStrength, aDinosaurWithSameStrength);

        assertEquals(RESULT_DRAW, result);
    }

    @Test
    public void givenTwoDinosaursWithDifferentStrength_whenSumoFight_thenShouldReturnTheNameOfStrongestDinosaur() {
        when(aStrongerDinosaur.compareStrength(aDinosaur)).thenReturn(POSITIVE_DIFFERENCE);
        when(aStrongerDinosaur.getName()).thenReturn(DINOSAUR_NAME);

        String result = sumoFightOrganizer.sumoFight(aStrongerDinosaur, aDinosaur);

        assertEquals(DINOSAUR_NAME, result);
    }

    @Test
    public void givenDinosaurAlreadyFighting_whenSumoFight_thenShouldThrowDinosaurAlreadyParticipatingException() {
        when(aStrongerDinosaur.compareStrength(aDinosaur)).thenReturn(POSITIVE_DIFFERENCE);

        sumoFightOrganizer.sumoFight(aStrongerDinosaur, aDinosaur);

        assertThrows(DinosaurAlreadyParticipatingException.class,
                () -> sumoFightOrganizer.sumoFight(aStrongerDinosaur, aDinosaurWithSameStrength));
    }

    @Test
    public void givenASumoFightOrganizerWithTwoSumoFights_whenSumoFight_thenShouldThrowMaxCombatsReachedException() {
        when(aStrongerDinosaur.compareStrength(aStrongerDinosaur)).thenReturn(NULL_DIFFERENCE);
        when(aDinosaurWithSameStrength.compareStrength(aDinosaurWithSameStrength)).thenReturn(POSITIVE_DIFFERENCE);

        sumoFightOrganizer.sumoFight(aStrongerDinosaur, aStrongerDinosaur);
        sumoFightOrganizer.sumoFight(aDinosaurWithSameStrength, aDinosaurWithSameStrength);

        assertThrows(MaxCombatsReachedException.class, () -> sumoFightOrganizer.sumoFight(aDinosaur, aDinosaur));
    }
}
