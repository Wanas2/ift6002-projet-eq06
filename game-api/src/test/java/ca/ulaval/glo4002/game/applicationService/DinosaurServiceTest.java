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
    private final static int SOMME_WEIGHT = 134;
    private final static String A_NAME = "ehwr";
    private final static String ANOTHER_NAME = "ehwrwfgh";
    private final static Gender THE_MALE_GENDER = Gender.M;
    private final static Gender THE_FEMALE_GENDER = Gender.F;

    private BreedingRequestDTO aBreedingRequestDTO;
    private FoodConsumptionStrategy aFoodConsumptionStrategy;
    private DinosaurDTO aDinosaurDTO;
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
        initializeADinosaurDTO();
        initializeABreedingDTO();
        aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        aDinosaur = new Dinosaur(A_SPECIES, SOMME_WEIGHT, A_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy);
        anotherDinosaur =
                new Dinosaur(A_SPECIES, SOMME_WEIGHT, ANOTHER_NAME, THE_FEMALE_GENDER, aFoodConsumptionStrategy);
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
    public void givenADinosaureDTO_whenAddDinosaur_thenShouldVerifyIfDinosaureWithSameNameExists() {
        dinosaurService.addDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);

        verify(herd).hasDinosaurWithName(aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTOWithDuplicateName_whenAddDinosaur_thenShouldThrowException() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(true);

        assertThrows(DuplicateNameException.class,
                ()->dinosaurService.
                        addDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species));
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenShouldCreateAppropriateDinosaur() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(false);

        dinosaurService.addDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);

        verify(dinosaurFactory).create(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.species,
                aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenGameShouldAddTheDinosaur() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(false);
        when(dinosaurFactory.create(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.species,
                aDinosaurDTO.name)).thenReturn(aDinosaur);

        dinosaurService.addDinosaur(aDinosaurDTO.name, aDinosaurDTO.weight, aDinosaurDTO.gender, aDinosaurDTO.species);

        verify(game).addDinosaur(aDinosaur);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenDinosaurWithNameShouldBeGotten() {
        dinosaurService.showDinosaur(A_NAME);

        verify(herd).getDinosaurWithName(A_NAME);
    }

    @Test
    public void whenShowAllDinosaurs_thenHerdShouldGetAllDinosaures() {
        dinosaurService.showAllDinosaurs();

        verify(herd).getAllDinosaurs();
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDino_thenHerdShouldGetTheFatherDinosaurByItsName() {
        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(herd).getDinosaurWithName(aBreedingRequestDTO.fatherName);
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDino_thenHerdShouldGetTheMotherDinosaurByItsName() {
        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(herd).getDinosaurWithName(aBreedingRequestDTO.motherName);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaur_whenBreedDino_thenShouldFetchTheBabyDinosaur() {
        when(herd.getDinosaurWithName(aBreedingRequestDTO.fatherName)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(aBreedingRequestDTO.motherName)).thenReturn(anotherDinosaur);

        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(babyFetcher).fetch(aDinosaur, anotherDinosaur, aBreedingRequestDTO.name);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaur_whenBreedDino_thenGameShouldAddDinosaur() {
        when(herd.getDinosaurWithName(aBreedingRequestDTO.fatherName)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(aBreedingRequestDTO.motherName)).thenReturn(anotherDinosaur);
        when(babyFetcher.fetch(aDinosaur, anotherDinosaur, aBreedingRequestDTO.name))
                .thenReturn(Optional.of(aBabyDinosaur));

        dinosaurService.breedDinosaur(aBreedingRequestDTO.name, aBreedingRequestDTO.fatherName,
                aBreedingRequestDTO.motherName);

        verify(game).addDinosaur(aBabyDinosaur);
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
