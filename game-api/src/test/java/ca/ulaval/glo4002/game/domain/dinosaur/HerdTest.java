package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class HerdTest {

    private FoodConsumptionStrategy carnivorousStrategy;
    private FoodConsumptionStrategy herbivorousStrategy1;
    private FoodConsumptionStrategy herbivorousStrategy2;
    private final static String CARNIVOROUS_NAME = "Bob";
    private final static String HERBIVOROUS_NAME_1 = "Bobi";
    private final static String HERBIVOROUS_NAME_2 = "Alyce";
    private final static String NAME_DINOSAUR = "Cedric";
    private final static int CARNIVOROUS_WEIGHT = 90;
    private final static int HERBIVOROUS_WEIGHT_1 = 80;
    private final static int HERBIVOROUS_WEIGHT_2 = 80;
    private final static int CARNIVOROUS_AGE = 0;
    private final static int HERBIVOROUS_AGE_1 = 1;
    private final static int HERBIVOROUS_AGE_2 = 2;
    private Dinosaur carnivorous_dinosaur_1;
    private Dinosaur herbivorous_dinosaur_1;
    private Dinosaur herbivorous_dinosaur_2;
    private final List<Dinosaur> dinosaurs = new ArrayList<>();
    private Herd herd;

    @BeforeEach
    void setUp() {
        carnivorousStrategy = mock(FoodConsumptionStrategy.class);
        herbivorousStrategy1 = mock(FoodConsumptionStrategy.class);
        herbivorousStrategy2 = mock(FoodConsumptionStrategy.class);
        carnivorous_dinosaur_1 = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, CARNIVOROUS_NAME, Gender.M, carnivorousStrategy);
        herbivorous_dinosaur_1 = new Dinosaur(Species.Ankylosaurus, HERBIVOROUS_WEIGHT_1, HERBIVOROUS_NAME_1, Gender.F, herbivorousStrategy1);
        herbivorous_dinosaur_2 = new Dinosaur(Species.Diplodocus, HERBIVOROUS_WEIGHT_2, HERBIVOROUS_NAME_2, Gender.F, herbivorousStrategy2);
        Collections.addAll(dinosaurs, carnivorous_dinosaur_1, herbivorous_dinosaur_1, herbivorous_dinosaur_2);
        herd = new Herd(dinosaurs);
        herbivorous_dinosaur_1.age();
        herbivorous_dinosaur_2.age();
        herbivorous_dinosaur_2.age();

    }

    @Test
    public void givenADinosaur_whenAddingNotExistingDinosaur_thenDinosaurShouldBeAdded() {
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, NAME_DINOSAUR, Gender.F, carnivorousStrategy);

        herd.addDinosaur(dinosaur);

        assertTrue(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenADinosaur_whenAddingExistingDinosaur_thenDinosaurShouldNotBeAdded() {
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, CARNIVOROUS_NAME, Gender.M, carnivorousStrategy);

        herd.addDinosaur(dinosaur);

        assertFalse(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenHerd_whenFeedingAllDinosaurs_thenNoDinosaurShouldBeRemoved() {
        when(carnivorousStrategy.consumeFood(CARNIVOROUS_WEIGHT, CARNIVOROUS_AGE)).thenReturn(true);
        when(herbivorousStrategy1.consumeFood(HERBIVOROUS_WEIGHT_1, HERBIVOROUS_AGE_1)).thenReturn(true);
        when(herbivorousStrategy2.consumeFood(HERBIVOROUS_WEIGHT_2, HERBIVOROUS_AGE_2)).thenReturn(true);

        herd.feedDinosaurs();

        assertTrue(dinosaurs.contains(carnivorous_dinosaur_1));
        assertTrue(dinosaurs.contains(herbivorous_dinosaur_1));
        assertTrue(dinosaurs.contains(herbivorous_dinosaur_2));
    }

    @Test
    public void givenHerd_whenFeedingSomeDinosaurs_thenFastingDinosaursShouldBeRemoved() {
        when(carnivorousStrategy.consumeFood(CARNIVOROUS_WEIGHT, CARNIVOROUS_AGE)).thenReturn(true);
        when(herbivorousStrategy1.consumeFood(HERBIVOROUS_WEIGHT_1, HERBIVOROUS_AGE_1)).thenReturn(false);
        when(herbivorousStrategy2.consumeFood(HERBIVOROUS_WEIGHT_2, HERBIVOROUS_AGE_2)).thenReturn(true);

        herd.feedDinosaurs();

        assertTrue(dinosaurs.contains(carnivorous_dinosaur_1));
        assertFalse(dinosaurs.contains(herbivorous_dinosaur_1));
        assertTrue(dinosaurs.contains(herbivorous_dinosaur_2));
    }
}
