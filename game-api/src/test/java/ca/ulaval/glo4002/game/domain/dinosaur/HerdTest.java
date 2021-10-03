package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class HerdTest {
    private FoodConsumptionStrategy CARNIVOROUS_STRATEGY;
    private FoodConsumptionStrategy HERBIVOROUS_STRATEGY_1;
    private FoodConsumptionStrategy HERBIVOROUS_STRATEGY_2;
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
    void setUp(){
        CARNIVOROUS_STRATEGY = mock(FoodConsumptionStrategy.class);
        HERBIVOROUS_STRATEGY_1 = mock(FoodConsumptionStrategy.class);
        HERBIVOROUS_STRATEGY_2 = mock(FoodConsumptionStrategy.class);
        carnivorous_dinosaur_1 = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, CARNIVOROUS_NAME, Gender.M, CARNIVOROUS_STRATEGY);
        herbivorous_dinosaur_1 = new Dinosaur(Species.Ankylosaurus, HERBIVOROUS_WEIGHT_1, HERBIVOROUS_NAME_1, Gender.F, HERBIVOROUS_STRATEGY_1);
        herbivorous_dinosaur_2 = new Dinosaur(Species.Diplodocus, HERBIVOROUS_WEIGHT_2, HERBIVOROUS_NAME_2, Gender.F, HERBIVOROUS_STRATEGY_2);
        Collections.addAll(dinosaurs, carnivorous_dinosaur_1, herbivorous_dinosaur_1, herbivorous_dinosaur_2);
        DinosaurRepository dinosaurRepository = mock(DinosaurRepository.class);
        when(dinosaurRepository.findAll()).thenReturn(dinosaurs);
        herd = new Herd(dinosaurRepository);
        herbivorous_dinosaur_1.age();
        herbivorous_dinosaur_2.age();
        herbivorous_dinosaur_2.age();

    }

    @Test
    public void givenADinosaur_whenAddingNotExistingDinosaur_thenDinosaurShouldBeAdded(){
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, NAME_DINOSAUR, Gender.F, CARNIVOROUS_STRATEGY);

        herd.add(dinosaur);

        assertTrue(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenADinosaur_whenAddingExistingDinosaur_thenDinosaurShouldNotBeAdded(){
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, CARNIVOROUS_NAME, Gender.M, CARNIVOROUS_STRATEGY);

        herd.add(dinosaur);

        assertFalse(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenHerd_whenFeedingAllDinosaurs_thenNoDinosaurShouldBeRemoved(){
        when(CARNIVOROUS_STRATEGY.consumeFood(CARNIVOROUS_WEIGHT, CARNIVOROUS_AGE)).thenReturn(true);
        when(HERBIVOROUS_STRATEGY_1.consumeFood(HERBIVOROUS_WEIGHT_1, HERBIVOROUS_AGE_1)).thenReturn(true);
        when(HERBIVOROUS_STRATEGY_2.consumeFood(HERBIVOROUS_WEIGHT_2, HERBIVOROUS_AGE_2)).thenReturn(true);

        herd.feed();

        assertTrue(dinosaurs.contains(carnivorous_dinosaur_1));
        assertTrue(dinosaurs.contains(herbivorous_dinosaur_1));
        assertTrue(dinosaurs.contains(herbivorous_dinosaur_2));
    }

    @Test
    public void givenHerd_whenFeedingSomeDinosaurs_thenFastingDinosaursShouldBeRemoved(){
        when(CARNIVOROUS_STRATEGY.consumeFood(CARNIVOROUS_WEIGHT, CARNIVOROUS_AGE)).thenReturn(true);
        when(HERBIVOROUS_STRATEGY_1.consumeFood(HERBIVOROUS_WEIGHT_1, HERBIVOROUS_AGE_1)).thenReturn(false);
        when(HERBIVOROUS_STRATEGY_2.consumeFood(HERBIVOROUS_WEIGHT_2, HERBIVOROUS_AGE_2)).thenReturn(true);

        herd.feed();

        assertTrue(dinosaurs.contains(carnivorous_dinosaur_1));
        assertFalse(dinosaurs.contains(herbivorous_dinosaur_1));
        assertTrue(dinosaurs.contains(herbivorous_dinosaur_2));
    }
}
