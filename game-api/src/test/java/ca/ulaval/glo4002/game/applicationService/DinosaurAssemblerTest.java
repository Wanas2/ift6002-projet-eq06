package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.Dinosaur.DinosaurAssembler;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Gender;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class DinosaurAssemblerTest {

    @Test
    public void givenADinosaur_whenToDTO_thenShouldBeCorrectlyMapped(){
        FoodConsumptionStrategy aStrategy = mock(FoodConsumptionStrategy.class);
        Species aSpecies = Species.TyrannosaurusRex;
        String expectedSpecies = "Tyrannosaurus Rex";
        int aWeight = 17;
        String aName = "Joe";
        Gender aGender = Gender.F;
        String expectedGender = "f";
        Dinosaur aDinosaur = new Dinosaur(aSpecies,aWeight,aName,aGender,aStrategy);
        DinosaurAssembler dinosaurAssembler = new DinosaurAssembler();

        DinosaurDTO dinosaurDTO = dinosaurAssembler.toDTO(aDinosaur);

        assertEquals(aName,dinosaurDTO.name);
        assertEquals(aWeight,dinosaurDTO.weight);
        assertEquals(expectedGender,dinosaurDTO.gender);
        assertEquals(expectedSpecies,dinosaurDTO.species);

    }
}
