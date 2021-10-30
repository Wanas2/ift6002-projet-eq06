package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurAssembler;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.interfaces.rest.dinosaur.BreedingRequestDTO;
import ca.ulaval.glo4002.game.interfaces.rest.dinosaur.DinosaurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DinosaurServiceTest {

    private final Species A_SPECIES = Species.Diplodocus;
    private final int SOMME_WEIGHT = 134;
    private final String A_NAME = "ehwr";
    private final String ANOTHER_NAME = "ehwrwfgh";
    private final Gender THE_MALE_GENDER = Gender.M;
    private final Gender THE_FEMALE_GENDER = Gender.F;

    private BreedingRequestDTO aBreedingRequestDTO;
    private FoodConsumptionStrategy aFoodConsumptionStrategy;
    private DinosaurDTO aDinosaurDTO;
    private Dinosaur aDinosaur;
    private Dinosaur anotherDinosaur;
    private BabyDinosaur aBabyDinosaur;
    private DinosaurAssembler dinosaurAssembler;
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
                new BabyDinosaur(A_SPECIES, A_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy, aDinosaur, anotherDinosaur);
        dinosaurAssembler = mock(DinosaurAssembler.class);
        dinosaurFactory = mock(DinosaurFactory.class);
        herd = mock(Herd.class);
        game = mock(Game.class);
        babyFetcher = mock(BabyFetcher.class);
        dinosaurService = new DinosaurService(dinosaurAssembler, dinosaurFactory, herd, game,
                babyFetcher);
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaur_thenShouldVerifyIfDinosaureWithSameNameExists() {
        dinosaurService.addDinosaur(aDinosaurDTO);

        verify(herd).hasDinosaurWithName(aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTOWithDuplicateName_whenAddDinosaur_thenShouldThrowException() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(true);

        assertThrows(DuplicateNameException.class, ()->dinosaurService.addDinosaur(aDinosaurDTO));
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenShouldCreateAppropriateDinosaur() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(false);

        dinosaurService.addDinosaur(aDinosaurDTO);

        verify(dinosaurFactory).create(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.species,
                aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenGameShouldAddTheDinosaur() {
        when(herd.hasDinosaurWithName(aDinosaurDTO.name)).thenReturn(false);
        when(dinosaurFactory.create(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.species,
                aDinosaurDTO.name)).thenReturn(aDinosaur);

        dinosaurService.addDinosaur(aDinosaurDTO);

        verify(game).addDinosaur(aDinosaur);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenDinosaurWithNameShouldBeGotten() {
        dinosaurService.showDinosaur(A_NAME);

        verify(herd).getDinosaurWithName(A_NAME);
    }

    @Test
    public void givenADinosaurName_whenShowDinosaur_thenTheDinosaurDTOShouldBeCreated() {
        when(herd.getDinosaurWithName(A_NAME)).thenReturn(aDinosaur);

        dinosaurService.showDinosaur(A_NAME);

        verify(dinosaurAssembler).toDTO(aDinosaur);
    }

    @Test
    public void whenShowAllDinosaurs_thenHerdShouldGetAllDinosaures() {
        dinosaurService.showAllDinosaurs();

        verify(herd).getAllDinosaurs();
    }

    @Test
    public void givenADinosaur_whenShowAllDinosaurs_thenTheDinosaureDTOShouldBeCreated() {
        List<Dinosaur> dinosaurs = new ArrayList<>();
        dinosaurs.add(aDinosaur);
        when(herd.getAllDinosaurs()).thenReturn(dinosaurs);

        dinosaurService.showAllDinosaurs();

        verify(dinosaurAssembler).toDTO((aDinosaur));
    }

    @Test
    public void givenMultipleDinosaurs_whenShowAllDinosaurs_thenTheDinosauresDTOShouldBeCreated() {
        List<Dinosaur> dinosaurs = new ArrayList<>();
        dinosaurs.add(aDinosaur);
        dinosaurs.add(anotherDinosaur);
        when(herd.getAllDinosaurs()).thenReturn(dinosaurs);

        dinosaurService.showAllDinosaurs();

        verify(dinosaurAssembler).toDTO((aDinosaur));
        verify(dinosaurAssembler).toDTO((anotherDinosaur));
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDino_thenHerdShouldGetTheFatherDinosaurByItsName() {
        dinosaurService.breedDinosaur(aBreedingRequestDTO);

        verify(herd).getDinosaurWithName(aBreedingRequestDTO.fatherName);
    }

    @Test
    public void givenABreedingRequestDTO_whenBreedDino_thenHerdShouldGetTheMotherDinosaurByItsName() {
        dinosaurService.breedDinosaur(aBreedingRequestDTO);

        verify(herd).getDinosaurWithName(aBreedingRequestDTO.motherName);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaur_whenBreedDino_thenShouldFetchTheBabyDinosaur() {
        when(herd.getDinosaurWithName(aBreedingRequestDTO.fatherName)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(aBreedingRequestDTO.motherName)).thenReturn(anotherDinosaur);

        dinosaurService.breedDinosaur(aBreedingRequestDTO);

        verify(babyFetcher).fetch(aDinosaur, anotherDinosaur, aBreedingRequestDTO.name);
    }

    @Test
    public void givenAMaleAndAFemaleDinosaur_whenBreedDino_thenGameShouldAddDinosaur() {
        when(herd.getDinosaurWithName(aBreedingRequestDTO.fatherName)).thenReturn(aDinosaur);
        when(herd.getDinosaurWithName(aBreedingRequestDTO.motherName)).thenReturn(anotherDinosaur);
        when(babyFetcher.fetch(aDinosaur, anotherDinosaur, aBreedingRequestDTO.name)).thenReturn(Optional.of(aBabyDinosaur));

        dinosaurService.breedDinosaur(aBreedingRequestDTO);

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
