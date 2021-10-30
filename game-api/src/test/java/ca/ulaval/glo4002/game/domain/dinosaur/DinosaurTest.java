package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DinosaurTest {

    private final int AGE = 0;
    private final int WEIGHT = 81;
    private final String CARNIVOROUS_NAME = "Bob";
    private final String HERBIVOROUS_NAME = "Bobi";

    private Dinosaur aCarnivorousDinosaur;
    private FoodConsumptionStrategy carnivorousStrategy;
    private Dinosaur aHerbivorousDinosaur;
    private FoodConsumptionStrategy herbivorousStrategy;

    @BeforeEach
    public void setup() {
        carnivorousStrategy = mock(FoodConsumptionStrategy.class);
        aCarnivorousDinosaur = new Dinosaur(Species.Spinosaurus, WEIGHT, CARNIVOROUS_NAME, Gender.M,
                carnivorousStrategy);
        herbivorousStrategy = mock(FoodConsumptionStrategy.class);
        aHerbivorousDinosaur = new Dinosaur(Species.Ankylosaurus, WEIGHT, HERBIVOROUS_NAME, Gender.F,
                herbivorousStrategy);
    }

    @Test
    public void givenANewDinosaur_thenItShouldBeAlive() {
        assertTrue(aCarnivorousDinosaur.isAlive());
    }

    @Test
    public void givenADinosaur_whenItCanNotEatEnough_thenItShouldDie() {
        when(herbivorousStrategy.consumeFood(WEIGHT, AGE)).thenReturn(false);

        aHerbivorousDinosaur.eat();

        assertFalse(aHerbivorousDinosaur.isAlive());
    }

    @Test
    public void givenAnDinosaur_whenItCanEatEnough_thenItShouldStayAlive() {
        when(herbivorousStrategy.consumeFood(WEIGHT, AGE)).thenReturn(true);

        aHerbivorousDinosaur.eat();

        assertTrue(aHerbivorousDinosaur.isAlive());
    }

    @Test
    public void givenADinosaur_whenCalculateStrength_thenStrengthShouldBeCalculated() {
        int expectedStrength = 122;

        int strength = aCarnivorousDinosaur.calculateStrength();

        assertEquals(expectedStrength, strength);
    }
}
