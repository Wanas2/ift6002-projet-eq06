package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BabyDinosaurTest {

    private Dinosaur fatherDinosaur;
    private Dinosaur motherDinosaur;
    private BabyDinosaur aBabyDinosaur;
    private FoodConsumptionStrategy babyDinosaurConsumptionStrategy;

    @BeforeEach
    public void setup() {
        babyDinosaurConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        String name = "Baby";
        fatherDinosaur = mock(Dinosaur.class);
        motherDinosaur = mock(Dinosaur.class);
        aBabyDinosaur = new BabyDinosaur(Species.Ankylosaurus, name, Gender.F, babyDinosaurConsumptionStrategy,
                fatherDinosaur, motherDinosaur);
    }

    @Test
    public void givenABabyDinosaurWithBothParentsDead_whenIsAlive_thenItShouldNotBeAlive() {
        when(fatherDinosaur.isAlive()).thenReturn(false);
        when(motherDinosaur.isAlive()).thenReturn(false);

        boolean isBabyAlive = aBabyDinosaur.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWithFoodNeedsNotSatisfied_whenIsAlive_thenItShouldNotBeALive() {
        when(babyDinosaurConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(false);

        boolean isBabyAlive = aBabyDinosaur.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWithFoodNeedsSatisfiedAndWithAtLeastOneParentAlive_whenIsAlive_thenItShouldAlive() {
        when(fatherDinosaur.isAlive()).thenReturn(true);
        when(motherDinosaur.isAlive()).thenReturn(false);
        when(babyDinosaurConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(true);

        boolean isBabyAlive = aBabyDinosaur.isAlive();

        assertTrue(isBabyAlive);
    }
}
