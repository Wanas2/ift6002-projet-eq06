package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BabyDinosaurTest {

    private Dinosaur father_dinosaur;
    private Dinosaur mother_dinosaur;
    private BabyDinosaur a_baby_dinosaur;
    private final int BABY_WEIGHT = 1;
    private final int BABY_AGE = 0;
    private FoodConsumptionStrategy baby_dinosaur_consumption_strategy;

    @BeforeEach
    public void setup() {
        baby_dinosaur_consumption_strategy = mock(FoodConsumptionStrategy.class);
        String name = "Baby";
        father_dinosaur = mock(Dinosaur.class);
        mother_dinosaur = mock(Dinosaur.class);
        a_baby_dinosaur = new BabyDinosaur(Species.Ankylosaurus, name, Gender.F, baby_dinosaur_consumption_strategy,
                father_dinosaur, mother_dinosaur);
    }

    @Test
    public void givenABabyDinosaurWithBothParentsDead_whenVerifyingIfAlive_thenItShouldBeDead() {
        when(father_dinosaur.isAlive()).thenReturn(false);
        when(mother_dinosaur.isAlive()).thenReturn(false);

        boolean isBabyAlive = a_baby_dinosaur.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWhichDidNotEatEnough_whenVerifyingIfAlive_thenItShouldBeDead() {
        when(father_dinosaur.isAlive()).thenReturn(true);
        when(mother_dinosaur.isAlive()).thenReturn(true);
        when(baby_dinosaur_consumption_strategy.consumeFood(BABY_WEIGHT, BABY_AGE)).thenReturn(false);
        a_baby_dinosaur.eat();

        boolean isBabyAlive = a_baby_dinosaur.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWhichEatEnoughAndWithAtLeastOneParentAlive_whenVerifyingIfAlive_thenItShouldAlive() {
        when(father_dinosaur.isAlive()).thenReturn(true);
        when(mother_dinosaur.isAlive()).thenReturn(false);
        when(baby_dinosaur_consumption_strategy.consumeFood(BABY_WEIGHT, BABY_AGE)).thenReturn(true);
        a_baby_dinosaur.eat();

        boolean isBabyAlive = a_baby_dinosaur.isAlive();

        assertTrue(isBabyAlive);
    }
}
