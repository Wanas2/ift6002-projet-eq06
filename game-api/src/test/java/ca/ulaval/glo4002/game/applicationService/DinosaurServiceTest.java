package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.herd.Herd;
import ca.ulaval.glo4002.game.interfaces.rest.dinosaur.BreedingRequestDTO;
import ca.ulaval.glo4002.game.interfaces.rest.dinosaur.DinosaurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DinosaurServiceTest {

    private final static Species A_SPECIES = Species.Diplodocus;
    private final static int SOME_WEIGHT = 134;
    private final static String A_NAME = "ehwr";
    private final static String ANOTHER_NAME = "ehwrwfgh";
    private final static String A_TYRANNOSAURUS_NAME = "rwfgh";
    private final static Gender THE_MALE_GENDER = Gender.M;
    private final static Gender THE_FEMALE_GENDER = Gender.F;

    private BreedingRequestDTO aBreedingRequestDTO;
    private DinosaurDTO aDinosaurDTO;
    private AdultDinosaur adultDinosaur;
    private AdultDinosaur anotherAdultDinosaur;
    private BabyDinosaur aBabyDinosaur;
    private DinosaurFactory dinosaurFactory;
    private Herd herd;
    private Game game;
    private BabyFetcher babyFetcher;
    private DinosaurService dinosaurService;

    @BeforeEach
    void setUp() {
        initializeADinosaurDTO();
        initializeABreedingDTO();
        FoodConsumptionStrategy aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        adultDinosaur = new AdultDinosaur(A_SPECIES, SOME_WEIGHT, A_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy);
        anotherAdultDinosaur =
                new AdultDinosaur(A_SPECIES, SOME_WEIGHT, ANOTHER_NAME, THE_FEMALE_GENDER, aFoodConsumptionStrategy);
        aBabyDinosaur =
                new BabyDinosaur(A_SPECIES, A_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy, adultDinosaur,
                        anotherAdultDinosaur);
        dinosaurFactory = mock(DinosaurFactory.class);
        herd = mock(Herd.class);
        game = mock(Game.class);
        babyFetcher = mock(BabyFetcher.class);
        dinosaurService = new DinosaurService(dinosaurFactory, herd, game, babyFetcher);
    }

    @Test
    public void givenADinosaurDTO_whenAddDinosaur_thenShouldVerifyIfDinosaurWithSameNameExists() {
        dinosaurService.addAdultDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);

        verify(herd).hasDinosaurWithName(aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaurDTOWithDuplicateName_whenAddDinosaur_thenShouldThrowDuplicateNameException() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(true);

        assertThrows(DuplicateNameException.class,
                ()->dinosaurService.
                        addAdultDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species));
    }

    @Test
    public void givenADinosaurDTO_whenAddDinosaur_thenShouldCreateAppropriateDinosaur() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(false);

        dinosaurService.addAdultDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);

        verify(dinosaurFactory).createAdultDinosaur(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.species,
                aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaurDTO_whenAddDinosaur_thenGameShouldAddTheDinosaur() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(false);
        when(dinosaurFactory.createAdultDinosaur(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.species,
                aDinosaurDTO.name)).thenReturn(adultDinosaur);

        dinosaurService.addAdultDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);

        verify(game).addAdultDinosaur(adultDinosaur);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenDinosaurWithNameShouldBeReceived() {
        dinosaurService.showDinosaur(A_NAME);

        verify(herd).getDinosaurWithName(A_NAME);
    }

    @Test
    public void whenShowAllDinosaurs_thenHerdShouldGetAllDinosaurs() {
        dinosaurService.showAllDinosaurs();

        verify(herd).getAllDinosaurs();
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDinosaur_thenHerdShouldGetTheFatherDinosaurByItsName() {
        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(herd).getDinosaurWithName(aBreedingRequestDTO.fatherName);
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDinosaur_thenHerdShouldGetTheMotherDinosaurByItsName() {
        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(herd).getDinosaurWithName(aBreedingRequestDTO.motherName);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaur_whenBreedDinosaur_thenShouldFetchTheBabyDinosaur() {
        when(herd.getDinosaurWithName(aBreedingRequestDTO.fatherName)).thenReturn(adultDinosaur);
        when(herd.getDinosaurWithName(aBreedingRequestDTO.motherName)).thenReturn(anotherAdultDinosaur);

        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(babyFetcher).fetch(adultDinosaur, anotherAdultDinosaur, aBreedingRequestDTO.name);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaur_whenBreedDinosaur_thenGameShouldAddDinosaur() {
        when(herd.getDinosaurWithName(aBreedingRequestDTO.fatherName)).thenReturn(adultDinosaur);
        when(herd.getDinosaurWithName(aBreedingRequestDTO.motherName)).thenReturn(anotherAdultDinosaur);
        when(babyFetcher.fetch(adultDinosaur, anotherAdultDinosaur, aBreedingRequestDTO.name))
                .thenReturn(Optional.of(aBabyDinosaur));

        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(game).addBabyDinosaur(aBabyDinosaur);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaurThatCannotHaveABaby_whenBreedDinosaur_thenGameShouldNotAddDinosaur() {
        when(herd.getDinosaurWithName(aBreedingRequestDTO.fatherName)).thenReturn(adultDinosaur);
        when(herd.getDinosaurWithName(aBreedingRequestDTO.motherName)).thenReturn(anotherAdultDinosaur);
        when(babyFetcher.fetch(adultDinosaur, anotherAdultDinosaur, aBreedingRequestDTO.name))
                .thenReturn(Optional.empty());

        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(game, never()).addBabyDinosaur(aBabyDinosaur);
    }

    @Test
    public void givenTwoDinosaursNames_whenPrepareSumoFight_thenDinosaurServiceShouldPrepareTheRightDinosaurs(){
        when(herd.getDinosaurWithName(A_NAME)).thenReturn(adultDinosaur);
        when(herd.getDinosaurWithName(ANOTHER_NAME)).thenReturn(anotherAdultDinosaur);

        dinosaurService.prepareSumoFight(A_NAME, ANOTHER_NAME);

        verify(herd).getDinosaurWithName(A_NAME);
        verify(herd).getDinosaurWithName(ANOTHER_NAME);
    }

    @Test
    public void givenTwoDinosaursThatAreNotATyrannosaurusRex_whenPrepareSumoFight_thenHerdShouldOrganizeAFight(){
        when(herd.getDinosaurWithName(A_NAME)).thenReturn(adultDinosaur);
        when(herd.getDinosaurWithName(ANOTHER_NAME)).thenReturn(anotherAdultDinosaur);

        dinosaurService.prepareSumoFight(A_NAME, ANOTHER_NAME);

        verify(herd).predictWinnerSumoFight(adultDinosaur, anotherAdultDinosaur);
    }

    private void initializeADinosaurDTO() {
        String aName = "igiyv";
        int someWeight = 134;
        String aGender = "m";
        String aSpecies = "Diplodocus";

        aDinosaurDTO = new DinosaurDTO(aName, someWeight, aGender, aSpecies);
    }

    private void initializeABreedingDTO() {
        aBreedingRequestDTO = new BreedingRequestDTO();
        String babyName = "wrrwrww";
        String theFathersName = "wgrwr";
        String theMothersName = "mko";
        aBreedingRequestDTO.name = babyName;
        aBreedingRequestDTO.fatherName = theFathersName;
        aBreedingRequestDTO.motherName = theMothersName;
    }
}
