package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AdultDinosaurTest {

    private final static int A_POSITIVE_WEIGHT_VARIATION = 27;

    private AdultDinosaur aDinosaur;

    @BeforeEach
    public void setup() {
        FoodConsumptionStrategy aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        int dinosaurWeight = 150;
        String dinosaurName = "Bobi";
        aDinosaur = new AdultDinosaur(Species.Ankylosaurus, dinosaurWeight, dinosaurName, Gender.F,
                aFoodConsumptionStrategy);
    }

    @Test
    void givenAPositiveWeightVariation_whenModifyWeight_thenTheWeightVariationShouldBeAddedToTheDinosaurWeight(){
        int dinosaurWeightAfterWeightIncrease = aDinosaur.getWeight() + A_POSITIVE_WEIGHT_VARIATION;

        aDinosaur.modifyWeight(A_POSITIVE_WEIGHT_VARIATION);
        int newDinosaurWeight = aDinosaur.getWeight();

        assertEquals(dinosaurWeightAfterWeightIncrease, newDinosaurWeight);
    }

    @Test void givenANegativeWeightVariation_whenModifyWeight_thenTheWeightVariationShouldBeSubtractedToTheDinosaurWeight(){

    }
}
