package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.exceptions.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DinosaurServiceTest {

    private Species A_SPECIES = Species.Diplodocus;
    private int SOMME_WEIGHT = 134;
    private String A_NAME = "ehwr";
    private String ANOTHER_NAME = "ehwrwfgh";
    private Gender A_GENDER = Gender.M;

    private FoodConsumptionStrategy aFoodConsumptionStrategy;
    private DinosaurDTO aDinosaurDTO;
    private Dinosaur aDinosaur;
    private Dinosaur anotherDinosaur;
    private DinosaurAssembler dinosaurAssembler;
    private DinosaurFactory dinosaurFactory;
    private Herd herd;
    private Game game;
    private DinosaurService dinosaurService;

    @BeforeEach
    void setUp() {
        initializeADinosaurDTO();
        aDinosaur = new Dinosaur(A_SPECIES, SOMME_WEIGHT, A_NAME, A_GENDER, aFoodConsumptionStrategy);
        anotherDinosaur = new Dinosaur(A_SPECIES, SOMME_WEIGHT, ANOTHER_NAME, A_GENDER, aFoodConsumptionStrategy);
        aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        dinosaurAssembler = mock(DinosaurAssembler.class);
        dinosaurFactory = mock(DinosaurFactory.class);
        herd = mock(Herd.class);
        game = mock(Game.class);
        dinosaurService = new DinosaurService(dinosaurAssembler, dinosaurFactory, herd, game);
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaur_thenShouldVerifyIfDinosaureWithSameNameExists() {
        dinosaurService.addDinosaur(aDinosaurDTO);

        verify(herd).hasDinoosaurWithName(aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTOWithDuplicateName_whenAddDinosaur_thenShouldThrowException () {
        when(herd.hasDinoosaurWithName(aDinosaurDTO.name)).thenReturn(true);

        assertThrows(DuplicateNameException.class, () -> dinosaurService.addDinosaur(aDinosaurDTO));
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenShouldCreateAppropriateDinosaur() {
        when(herd.hasDinoosaurWithName(aDinosaurDTO.name)).thenReturn(false);

        dinosaurService.addDinosaur(aDinosaurDTO);

        verify(dinosaurFactory).create(aDinosaurDTO.gender, aDinosaurDTO.weight, aDinosaurDTO.species,
                aDinosaurDTO.name);
    }

    @Test
    public void givenADinosaureDTO_whenAddDinosaure_thenGameShouldAddTheDinosaur() {
        when(herd.hasDinoosaurWithName(aDinosaurDTO.name)).thenReturn(false);
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

    private void initializeADinosaurDTO() {
        String aName = "igiyv";
        int someWeight = 134;
        String aGender = "m";
        String aSpecies = "Diplodocus";

        aDinosaurDTO = new DinosaurDTO(aName, someWeight, aGender, aSpecies);
    }
}
