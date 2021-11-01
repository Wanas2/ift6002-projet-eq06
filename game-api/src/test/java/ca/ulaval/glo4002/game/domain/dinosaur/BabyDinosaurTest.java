package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BabyDinosaurTest {

    private final static int BABY_WEIGHT = 1;
    private final static int BABY_AGE = 0;

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
    public void givenABabyDinosaurWithBothParentsDead_whenVerifyingIfAlive_thenItShouldBeDead() {
        when(fatherDinosaur.isAlive()).thenReturn(false);
        when(motherDinosaur.isAlive()).thenReturn(false);

        boolean isBabyAlive = aBabyDinosaur.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWhichDidNotEatEnough_whenVerifyingIfAlive_thenItShouldBeDead() {
        when(fatherDinosaur.isAlive()).thenReturn(true);
        when(motherDinosaur.isAlive()).thenReturn(true);
        when(babyDinosaurConsumptionStrategy.consumeFood(BABY_WEIGHT, BABY_AGE)).thenReturn(false);
        aBabyDinosaur.eat();

        boolean isBabyAlive = aBabyDinosaur.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWhichEatEnoughAndWithAtLeastOneParentAlive_whenVerifyingIfAlive_thenItShouldAlive() {
        when(fatherDinosaur.isAlive()).thenReturn(true);
        when(motherDinosaur.isAlive()).thenReturn(false);
        when(babyDinosaurConsumptionStrategy.consumeFood(BABY_WEIGHT, BABY_AGE)).thenReturn(true);
        aBabyDinosaur.eat();

        boolean isBabyAlive = aBabyDinosaur.isAlive();

        assertTrue(isBabyAlive);
    }
}
