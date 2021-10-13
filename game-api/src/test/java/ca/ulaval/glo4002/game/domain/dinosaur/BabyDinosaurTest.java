package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BabyDinosaurTest {
    private Dinosaur FATHER_DINOSAUR;
    private Dinosaur MOTHER_DINOSAUR;
    private BabyDinosaur A_BABY_DINOSAUR;
    private final int BABY_WEIGHT = 1;
    private final int BABY_AGE = 0;
    private FoodConsumptionStrategy BABY_DINOSAUR_CONSUMPTION_STRATEGY;

    @BeforeEach
    public void setup(){
        BABY_DINOSAUR_CONSUMPTION_STRATEGY = mock(FoodConsumptionStrategy.class);
        String name = "Baby";
        FATHER_DINOSAUR = mock(Dinosaur.class);
        MOTHER_DINOSAUR = mock(Dinosaur.class);
        A_BABY_DINOSAUR = new BabyDinosaur(Species.Ankylosaurus,name,Gender.F,BABY_DINOSAUR_CONSUMPTION_STRATEGY,
                FATHER_DINOSAUR,MOTHER_DINOSAUR);
    }

    @Test
    public void givenABabyDinosaurWithBothParentsDead_whenVerifyingIfAlive_thenItShouldBeDead(){
        when(FATHER_DINOSAUR.isAlive()).thenReturn(false);
        when(MOTHER_DINOSAUR.isAlive()).thenReturn(false);

        boolean isBabyAlive = A_BABY_DINOSAUR.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWhichDidNotEatEnough_whenVerifyingIfAlive_thenItShouldBeDead(){
        when(FATHER_DINOSAUR.isAlive()).thenReturn(true);
        when(MOTHER_DINOSAUR.isAlive()).thenReturn(true);
        when(BABY_DINOSAUR_CONSUMPTION_STRATEGY.consumeFood(BABY_WEIGHT,BABY_AGE)).thenReturn(false);
        A_BABY_DINOSAUR.eat();

        boolean isBabyAlive = A_BABY_DINOSAUR.isAlive();

        assertFalse(isBabyAlive);
    }

    @Test
    public void givenABabyDinosaurWhichEatEnoughAndWithAtLeastOneParentAlive_whenVerifyingIfAlive_thenItShouldAlive(){
        when(FATHER_DINOSAUR.isAlive()).thenReturn(true);
        when(MOTHER_DINOSAUR.isAlive()).thenReturn(false);
        when(BABY_DINOSAUR_CONSUMPTION_STRATEGY.consumeFood(BABY_WEIGHT,BABY_AGE)).thenReturn(true);
        A_BABY_DINOSAUR.eat();

        boolean isBabyAlive = A_BABY_DINOSAUR.isAlive();

        assertTrue(isBabyAlive);
    }
}
