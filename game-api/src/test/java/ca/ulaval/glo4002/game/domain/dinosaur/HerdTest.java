package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class HerdTest {

    private final static String CARNIVOROUS_DINOSAUR_NAME = "Bob";
    private final static String HERBIVOROUS_DINOSAUR_NAME_1 = "Bobi";
    private final static String HERBIVOROUS_DINOSAUR_NAME_2 = "Alyce";
    private final static String DINOSAUR_NAME = "Cedric";
    private final static int CARNIVOROUS_DINOSAUR_WEIGHT = 90;
    private final static int HERBIVOROUS_DINOSAUR_WEIGHT_1 = 80;
    private final static int HERBIVOROUS_DINOSAUR_WEIGHT_2 = 80;
    private final static int CARNIVOROUS_DINOSAUR_AGE = 0;
    private final static int HERBIVOROUS_DINOSAUR_AGE_1 = 1;
    private final static int HERBIVOROUS_DINOSAUR_AGE_2 = 2;

    private FoodConsumptionStrategy carnivorousStrategy;
    private FoodConsumptionStrategy herbivorousStrategy1;
    private FoodConsumptionStrategy herbivorousStrategy2;
    private Dinosaur aDinosaur;
    private Dinosaur carnivorousDinosaur1;
    private Dinosaur herbivorousDinosaur1;
    private Dinosaur herbivorousDinosaur2;
    private final List<Dinosaur> dinosaurs = new ArrayList<>();
    private Herd herd;

    @BeforeEach
    void setUp() {
        carnivorousStrategy = mock(FoodConsumptionStrategy.class);
        herbivorousStrategy1 = mock(FoodConsumptionStrategy.class);
        herbivorousStrategy2 = mock(FoodConsumptionStrategy.class);
        aDinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_DINOSAUR_WEIGHT, DINOSAUR_NAME, Gender.M,
                carnivorousStrategy);
        carnivorousDinosaur1 = new Dinosaur(Species.Allosaurus, CARNIVOROUS_DINOSAUR_WEIGHT, CARNIVOROUS_DINOSAUR_NAME, Gender.M,
                carnivorousStrategy);
        herbivorousDinosaur1 = new Dinosaur(Species.Ankylosaurus, HERBIVOROUS_DINOSAUR_WEIGHT_1, HERBIVOROUS_DINOSAUR_NAME_1, Gender.F,
                herbivorousStrategy1);
        herbivorousDinosaur2 = new Dinosaur(Species.Diplodocus, HERBIVOROUS_DINOSAUR_WEIGHT_2, HERBIVOROUS_DINOSAUR_NAME_2, Gender.F,
                herbivorousStrategy2);
        Collections.addAll(dinosaurs, carnivorousDinosaur1, herbivorousDinosaur1, herbivorousDinosaur2);
        herd = new Herd(dinosaurs);
        herbivorousDinosaur1.age();
        herbivorousDinosaur2.age();
        herbivorousDinosaur2.age();

    }

    @Test
    public void givenANameOfANonExistingDinosaur_whenHasDinosaurWithName_thenNameShouldNotExist() {
        boolean dinosaurNameExists = herd.hasDinosaurWithName(DINOSAUR_NAME);

        assertFalse(dinosaurNameExists);
    }

    @Test
    public void givenANameOfAnExistingDinosaur_whenHasDinosaurWithName_thenNameShouldExist() {
        herd.addDinosaur(aDinosaur);
        boolean dinosaurNameExists = herd.hasDinosaurWithName(DINOSAUR_NAME);

        assertTrue(dinosaurNameExists);
    }

    @Test
    public void givenANonExistingDinosaur_whenAddDinosaur_thenDinosaurShouldBeAdded() {
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_DINOSAUR_WEIGHT, DINOSAUR_NAME, Gender.F,
                carnivorousStrategy);

        herd.addDinosaur(dinosaur);

        assertTrue(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenADinosaur_whenAddingExistingDinosaur_thenDinosaurShouldNotBeAdded() {
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_DINOSAUR_WEIGHT, CARNIVOROUS_DINOSAUR_NAME, Gender.M,
                carnivorousStrategy);

        herd.addDinosaur(dinosaur);

        assertFalse(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenHerd_whenFeedingAllDinosaurs_thenNoDinosaurShouldBeRemoved() {
        when(carnivorousStrategy.consumeFood(CARNIVOROUS_DINOSAUR_WEIGHT, CARNIVOROUS_DINOSAUR_AGE)).thenReturn(true);
        when(herbivorousStrategy1.consumeFood(HERBIVOROUS_DINOSAUR_WEIGHT_1, HERBIVOROUS_DINOSAUR_AGE_1)).thenReturn(true);
        when(herbivorousStrategy2.consumeFood(HERBIVOROUS_DINOSAUR_WEIGHT_2, HERBIVOROUS_DINOSAUR_AGE_2)).thenReturn(true);

        herd.feedDinosaurs();

        assertTrue(dinosaurs.contains(carnivorousDinosaur1));
        assertTrue(dinosaurs.contains(herbivorousDinosaur1));
        assertTrue(dinosaurs.contains(herbivorousDinosaur2));
    }

    @Test
    public void givenHerd_whenFeedingSomeDinosaurs_thenFastingDinosaursShouldBeRemoved() {
        when(carnivorousStrategy.consumeFood(CARNIVOROUS_DINOSAUR_WEIGHT, CARNIVOROUS_DINOSAUR_AGE)).thenReturn(true);
        when(herbivorousStrategy1.consumeFood(HERBIVOROUS_DINOSAUR_WEIGHT_1, HERBIVOROUS_DINOSAUR_AGE_1)).thenReturn(false);
        when(herbivorousStrategy2.consumeFood(HERBIVOROUS_DINOSAUR_WEIGHT_2, HERBIVOROUS_DINOSAUR_AGE_2)).thenReturn(true);

        herd.feedDinosaurs();

        assertTrue(dinosaurs.contains(carnivorousDinosaur1));
        assertFalse(dinosaurs.contains(herbivorousDinosaur1));
        assertTrue(dinosaurs.contains(herbivorousDinosaur2));
    }

    @Test
    public void givenHerd_whenReset_thenHerdShouldBeEmpty() {
        dinosaurs.add(aDinosaur);
        herd = new Herd(dinosaurs);

        herd.reset();

        assertEquals(0,dinosaurs.size());
    }
}
