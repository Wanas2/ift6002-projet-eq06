package ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Gender;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BreedingAssemblerTest {

    private final Species A_SPECIES = Species.Brachiosaurus;
    private final Species ANOTHER_SPECIES = Species.Diplodocus;
    private final static int SOMME_WEIGHT = 134;
    private final static String A_NAME = "ehwr";
    private final static String ANOTHER_NAME = "ehwrwfgh";
    private final Gender THE_MALE_GENDER = Gender.M;
    private final Gender THE_FEMALE_GENDER = Gender.F;

    private BreedingRequestExternalDTO breedingRequestExternalDTO;
    private Dinosaur aMaleDinosaur;
    private Dinosaur aFemaleDinosaur;
    private FoodConsumptionStrategy aFoodConsumptionStrategy;
    private BreedingAssembler breedingAssembler;

    @BeforeEach
    void setUp() {
        breedingRequestExternalDTO = new BreedingRequestExternalDTO();
        breedingRequestExternalDTO.fatherSpecies = Species.Brachiosaurus.toString();
        breedingRequestExternalDTO.motherSpecies = Species.Diplodocus.toString();

        aMaleDinosaur = new Dinosaur(A_SPECIES, SOMME_WEIGHT, A_NAME, THE_MALE_GENDER, aFoodConsumptionStrategy);
        aFemaleDinosaur =
                new Dinosaur(ANOTHER_SPECIES, SOMME_WEIGHT, ANOTHER_NAME, THE_FEMALE_GENDER, aFoodConsumptionStrategy);
        breedingAssembler = new BreedingAssembler();
    }

    @Test
    public void givenAMaleDinosaurAndAFemaleDinosaur_whenToDTO_thenShouldCreateAppropriateDTO() {
        breedingAssembler.toDTO(aMaleDinosaur, aFemaleDinosaur);

        assertEquals(breedingRequestExternalDTO.fatherSpecies, aMaleDinosaur.getSpecies().toString());
        assertEquals(breedingRequestExternalDTO.motherSpecies, aFemaleDinosaur.getSpecies().toString());
    }
}
