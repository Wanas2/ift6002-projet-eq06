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
    private final static String ANOTHER_DINOSAUR_NAME = "Toto";
    private final static String RESULT_DRAW = "tie";

    private Dinosaur dinosaur_1;
    private Dinosaur dinosaur_2;
    private Dinosaur dinosaur_3;
    private Dinosaur dinosaur_4;
    private Dinosaur dinosaur_5;
    private Dinosaur dinosaur_6;
    private SumoFightOrganizer sumoFightOrganizer;

    @BeforeEach
    public void setUp() {
        dinosaur_1 = mock(Dinosaur.class);
        dinosaur_2 = mock(Dinosaur.class);
        dinosaur_3 = mock(Dinosaur.class);
        dinosaur_4 = mock(Dinosaur.class);
        dinosaur_5 = mock(Dinosaur.class);
        dinosaur_6 = mock(Dinosaur.class);

        sumoFightOrganizer = new SumoFightOrganizer();
    }

    @Test
    public void givenTwoDinosaursWithSameStrength_whenSumoFight_thenFightShouldBeTie() {
        when(dinosaur_1.compareTo(dinosaur_2)).thenReturn(0);

        String result = sumoFightOrganizer.sumoFight(dinosaur_1, dinosaur_2);

        assertEquals(result, RESULT_DRAW);
    }

    @Test
    public void givenTwoDinosaursWithDifferentStrength_whenSumoFight_thenShouldReturnTheNameOfWinner() {
        when(dinosaur_1.compareTo(dinosaur_2)).thenReturn(1);
        when(dinosaur_1.getName()).thenReturn(DINOSAUR_NAME);

        String result = sumoFightOrganizer.sumoFight(dinosaur_1, dinosaur_2);

        assertEquals(result, DINOSAUR_NAME);
    }

    @Test
    public void givenADinosaurSecondTime_whenSumoFight_thenShouldThrowDinosaurAlreadyParticipatingException() {
        when(dinosaur_1.compareTo(dinosaur_2)).thenReturn(0);
        when(dinosaur_1.compareTo(dinosaur_3)).thenReturn(1);

        String result = sumoFightOrganizer.sumoFight(dinosaur_1, dinosaur_2);

        assertEquals(result, RESULT_DRAW);
        assertThrows(DinosaurAlreadyParticipatingException.class,
                () -> sumoFightOrganizer.sumoFight(dinosaur_1, dinosaur_3));
    }

    @Test
    public void whenSumoFightMoreThanTwoTimes_thenShouldThrowMaxCombatsReachedException() {
        when(dinosaur_1.compareTo(dinosaur_2)).thenReturn(0);
        when(dinosaur_3.compareTo(dinosaur_4)).thenReturn(1);
        when(dinosaur_3.getName()).thenReturn(ANOTHER_DINOSAUR_NAME);

        String result = sumoFightOrganizer.sumoFight(dinosaur_1, dinosaur_2);
        String result_1 = sumoFightOrganizer.sumoFight(dinosaur_3, dinosaur_4);

        assertEquals(result, RESULT_DRAW);
        assertEquals(result_1, ANOTHER_DINOSAUR_NAME);
        assertThrows(MaxCombatsReachedException.class,
                () -> sumoFightOrganizer.sumoFight(dinosaur_5, dinosaur_6));
    }
}
