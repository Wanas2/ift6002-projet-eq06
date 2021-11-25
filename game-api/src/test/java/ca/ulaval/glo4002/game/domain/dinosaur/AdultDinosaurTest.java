package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AdultDinosaurTest {

    private final static int WEIGHT_VARIATION = 27;

    private AdultDinosaur aDinosaur;

    @BeforeEach
    public void setup() {
        FoodConsumptionStrategy aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        int dinosaurWeight = 150;
        String dinosaurName = "Bobi";
        aDinosaur = new AdultDinosaur(Species.Ankylosaurus, dinosaurWeight, "dinosaurName", Gender.F,
                aFoodConsumptionStrategy);
    }

    @Test
    public void givenWeightVariation_whenModifyWeight_thenWeightShouldBeModified() {
        int expectedWeight = 177;

        aDinosaur.modifyWeight(WEIGHT_VARIATION);
        int modifiedWeight = aDinosaur.getWeight();

        assertEquals(expectedWeight, modifiedWeight);
    }
}
