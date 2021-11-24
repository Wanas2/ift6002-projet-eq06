package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.herd.Herd;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DinosaurServiceTest {

    private final static Species A_SPECIES = Species.Diplodocus;
    private final static Species ANOTHER_SPECIES = Species.Allosaurus;
    private final static int A_WEIGHT = 134;
    private final static String A_NAME = "name";
    private final static String ANOTHER_NAME = "another_name";
    private final static Gender THE_MALE_GENDER = Gender.M;
    private final static Gender THE_FEMALE_GENDER = Gender.F;
    private final static String A_GENDER = "Gender";
    private final static String A_BABY_NAME = "baby";
    private final static String A_FATHER_NAME = "father";
    private final static String A_MOTHER_NAME = "mother";

    private Dinosaur aDinosaur;
    private Dinosaur anotherDinosaur;
    private BabyDinosaur aBabyDinosaur;
    private DinosaurFactory dinosaurFactory;
    private Herd herd;
    private Game game;
    private BabyFetcher babyFetcher;
    private DinosaurService dinosaurService;

    @BeforeEach
    void setUp() {
        FoodConsumptionStrategy aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        aDinosaur = new Dinosaur(A_SPECIES, A_WEIGHT, A_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy);
        anotherDinosaur =
                new Dinosaur(A_SPECIES, A_WEIGHT, ANOTHER_NAME, THE_FEMALE_GENDER, aFoodConsumptionStrategy);
        aBabyDinosaur =
                new BabyDinosaur(A_SPECIES, A_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy, aDinosaur,
                        anotherDinosaur);
        dinosaurFactory = mock(DinosaurFactory.class);
        herd = mock(Herd.class);
        game = mock(Game.class);
        babyFetcher = mock(BabyFetcher.class);
        dinosaurService = new DinosaurService(dinosaurFactory, herd, game, babyFetcher);
    }

    @Test
    public void givenADinosaurNameNotInHerd_whenAddDinosaur_thenShouldCreateAppropriateDinosaur() {
        when(herd.hasDinosaurWithName(A_NAME)).thenReturn(false);

        dinosaurService.addDinosaur(A_NAME, A_WEIGHT, A_GENDER, ANOTHER_SPECIES.toString());

        verify(dinosaurFactory).create(A_GENDER, A_WEIGHT, ANOTHER_SPECIES.toString(), A_NAME);
    }

    @Test
    public void givenADinosaurNameNotInHerd_whenAddDinosaur_thenGameShouldAddTheDinosaur() {
        when(herd.hasDinosaurWithName(A_NAME)).thenReturn(false);
        when(dinosaurFactory.create(A_GENDER, A_WEIGHT, ANOTHER_SPECIES.toString(), A_NAME)).thenReturn(aDinosaur);

        dinosaurService.addDinosaur(A_NAME, A_WEIGHT, A_GENDER, ANOTHER_SPECIES.toString());

        verify(game).addDinosaur(aDinosaur);
    }

    @Test
    public void givenADinosaurNameInHerd_whenAddDinosaur_thenShouldThrowDuplicateNameException() {
        when(herd.hasDinosaurWithName(A_NAME)).thenReturn(true);

        assertThrows(
                DuplicateNameException.class,
                () -> dinosaurService.addDinosaur(A_NAME, A_WEIGHT, A_GENDER, ANOTHER_SPECIES.toString())
        );
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenDinosaurWithNameShouldBeReceived() {
        dinosaurService.showDinosaur(A_NAME);

        verify(herd).getDinosaurWithName(A_NAME);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenTheDinosaurShouldBeReturned() {
        when(herd.getDinosaurWithName(A_NAME)).thenReturn(aDinosaur);

        Dinosaur dinosaurReturned = dinosaurService.showDinosaur(A_NAME);

        assertEquals(aDinosaur, dinosaurReturned);
    }

    @Test
    public void whenShowAllDinosaurs_thenAllTheDinosaursShouldBeReturned() {
        List<Dinosaur> allDinosaursExpected = Arrays.asList(aDinosaur, anotherDinosaur);
        when(herd.getAllDinosaurs()).thenReturn(allDinosaursExpected);

        List<Dinosaur> DinosaursReturned = dinosaurService.showAllDinosaurs();

        assertEquals(allDinosaursExpected, DinosaursReturned);
    }

    @Test
    public void whenShowAllDinosaurs_thenHerdShouldGetAllDinosaurs() {
        dinosaurService.showAllDinosaurs();

        verify(herd).getAllDinosaurs();
    }

    @Test
    public void givenCompatibleMaleAndFemaleDinosaursInHerd_whenBreedDinosaur_thenGameShouldAddDinosaur() {
        when(herd.getDinosaurWithName(A_FATHER_NAME)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(A_MOTHER_NAME)).thenReturn(anotherDinosaur);
        when(babyFetcher.fetch(aDinosaur, anotherDinosaur, A_BABY_NAME))
                .thenReturn(Optional.of(aBabyDinosaur));

        dinosaurService.breedDinosaur(A_BABY_NAME, A_FATHER_NAME, A_MOTHER_NAME);

        verify(game).addDinosaur(aBabyDinosaur);
    }

    @Test
    public void givenIncompatibleMaleAndFemaleDinosaursInHerd_whenBreedDinosaur_thenGameShouldNotAddDinosaur() {
        when(herd.getDinosaurWithName(A_FATHER_NAME)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(A_MOTHER_NAME)).thenReturn(anotherDinosaur);
        when(babyFetcher.fetch(aDinosaur, anotherDinosaur, A_BABY_NAME))
                .thenReturn(Optional.empty());

        dinosaurService.breedDinosaur(A_BABY_NAME, A_FATHER_NAME, A_MOTHER_NAME);

        verify(game, never()).addDinosaur(aBabyDinosaur);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaur_whenBreedDino_thenShouldFetchTheBabyDinosaur() {
        when(herd.getDinosaurWithName(A_FATHER_NAME)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(A_MOTHER_NAME)).thenReturn(anotherDinosaur);

        dinosaurService.breedDinosaur(A_BABY_NAME, A_FATHER_NAME, A_MOTHER_NAME);

        verify(babyFetcher).fetch(aDinosaur, anotherDinosaur, A_BABY_NAME);
    }

    @Test
    public void givenTwoDinosaurs_whenPrepareSumoFight_thenThePredictedWinnerNameShouldBeReturned() {
        String expectedWinnerName = "winner";
        when(herd.getDinosaurWithName(A_NAME)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(ANOTHER_NAME)).thenReturn(anotherDinosaur);
        when(herd.predictWinnerSumoFight(aDinosaur, anotherDinosaur)).thenReturn(expectedWinnerName);

        String predictedNameReturned = dinosaurService.prepareSumoFight(A_NAME, ANOTHER_NAME);

        assertEquals(expectedWinnerName, predictedNameReturned);
    }
}
