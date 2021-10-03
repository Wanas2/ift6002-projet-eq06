package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DinosaurTests {
    Dinosaur A_CARNIVOROUS_DINOSAUR;
    FoodConsumptionStrategy CARNIVOROUS_STRATEGY;
    Dinosaur AN_HERBIVOROUS_DINOSAUR;
    FoodConsumptionStrategy HERBIVOROUS_STRATEGY;
    int AGE = 0;
    int WEIGHT = 81;
    String CARNIVOROUS_NAME = "Bob";
    String HERBIVOROUS_NAME = "Bobi";
    private final static float MALE_FACTOR = 1.0f;
    private final static float CARNIVOROUS_FACTOR = 1.5f;

    @BeforeEach
    public void setup(){
        CARNIVOROUS_STRATEGY = mock(FoodConsumptionStrategy.class);
        A_CARNIVOROUS_DINOSAUR = new Dinosaur(Species.Spinosaurus,WEIGHT,CARNIVOROUS_NAME,Gender.M,
                CARNIVOROUS_STRATEGY);
        HERBIVOROUS_STRATEGY = mock(FoodConsumptionStrategy.class);
        AN_HERBIVOROUS_DINOSAUR = new Dinosaur(Species.Ankylosaurus,WEIGHT,HERBIVOROUS_NAME,Gender.F,
                HERBIVOROUS_STRATEGY);
    }

    @Test
    public void givenANewDinosaur_thenItShouldBeAlive(){
        assertTrue(A_CARNIVOROUS_DINOSAUR.isAlive());
    }

    @Test
    public void givenADinosaur_whenItCanNotEatEnough_thenItShouldDie(){
        when(HERBIVOROUS_STRATEGY.consumeFood(WEIGHT, AGE)).thenReturn(false);

        AN_HERBIVOROUS_DINOSAUR.eat();

        assertFalse(AN_HERBIVOROUS_DINOSAUR.isAlive());
    }

    @Test
    public void givenAnDinosaur_whenItCanEatEnough_thenItShouldStayAlive(){
        when(HERBIVOROUS_STRATEGY.consumeFood(WEIGHT, AGE)).thenReturn(true);

        AN_HERBIVOROUS_DINOSAUR.eat();

        assertTrue(AN_HERBIVOROUS_DINOSAUR.isAlive());
    }

    @Test
    public void givenADinosaur_whenCalculateStrength_thenStrengthShouldBeCalculated(){
        int expectedStrength = 122;

        int strength = A_CARNIVOROUS_DINOSAUR.calculateStrength();

        assertEquals(expectedStrength, strength);
    }
}
