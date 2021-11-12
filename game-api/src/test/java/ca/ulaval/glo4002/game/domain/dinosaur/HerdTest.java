package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumption;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


public class HerdTest {

    private final static String CARNIVOROUS_NAME = "Bob";
    private final static String STRONGER_CARNIVOROUS_NAME = "Alfred";
    private final static String HERBIVOROUS_NAME = "Bobi";
    private final static String STRONGER_HERBIVOROUS_NAME = "Alyce";
    private final static String NAME_DINOSAUR = "Cedric";
    private final static int CARNIVOROUS_WEIGHT = 80;
    private final static int STRONGER_CARNIVOROUS_WEIGHT = 100;
    private final static int HERBIVOROUS_WEIGHT = 80;
    private final static int STRONGER_HERBIVOROUS_WEIGHT= 100;

    private FoodConsumptionStrategy carnivorousStrategy;
    private FoodConsumptionStrategy strongerDinosaurCarnivorousStrategy;
    private FoodConsumptionStrategy herbivorousStrategy;
    private FoodConsumptionStrategy strongerDinosaurHerbivorousStrategy;
    private Dinosaur aCarnivorousDinosaur;
    private Dinosaur aStrongerCarnivorousDinosaur;
    private Dinosaur anHerbivorousDinosaur;
    private Dinosaur aStrongerHerbivorousDinosaur;
    private final List<Dinosaur> dinosaurs = new ArrayList<>();
    private Herd herd;

    @BeforeEach
    void setUp() {
        carnivorousStrategy = mock(FoodConsumptionStrategy.class);
        herbivorousStrategy = mock(FoodConsumptionStrategy.class);
        strongerDinosaurHerbivorousStrategy = mock(FoodConsumptionStrategy.class);
        strongerDinosaurCarnivorousStrategy = mock(FoodConsumptionStrategy.class);

        aCarnivorousDinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, CARNIVOROUS_NAME,
                Gender.M, carnivorousStrategy);
        aStrongerCarnivorousDinosaur = new Dinosaur(Species.Allosaurus, STRONGER_CARNIVOROUS_WEIGHT,
                STRONGER_CARNIVOROUS_NAME, Gender.M, strongerDinosaurCarnivorousStrategy);
        anHerbivorousDinosaur = new Dinosaur(Species.Ankylosaurus, HERBIVOROUS_WEIGHT, HERBIVOROUS_NAME,
                Gender.F, herbivorousStrategy);
        aStrongerHerbivorousDinosaur = new Dinosaur(Species.Diplodocus, STRONGER_HERBIVOROUS_WEIGHT,
                STRONGER_HERBIVOROUS_NAME, Gender.F, strongerDinosaurHerbivorousStrategy);

        Collections.addAll(dinosaurs, aCarnivorousDinosaur, anHerbivorousDinosaur,
                aStrongerHerbivorousDinosaur,aStrongerCarnivorousDinosaur);
        herd = new Herd(dinosaurs);
        anHerbivorousDinosaur.age();
        aStrongerHerbivorousDinosaur.age();
        aStrongerHerbivorousDinosaur.age();

    }

    @Test
    public void givenADinosaur_whenAddingNotExistingDinosaur_thenDinosaurShouldBeAdded() {
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, NAME_DINOSAUR, Gender.F,
                carnivorousStrategy);

        herd.addDinosaur(dinosaur);

        assertTrue(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenADinosaur_whenAddingExistingDinosaur_thenDinosaurShouldNotBeAdded() {
        Dinosaur dinosaur = new Dinosaur(Species.Allosaurus, CARNIVOROUS_WEIGHT, CARNIVOROUS_NAME, Gender.M,
                carnivorousStrategy);

        herd.addDinosaur(dinosaur);

        assertFalse(dinosaurs.contains(dinosaur));
    }

    @Test
    public void givenHerd_whenFeedDinosaur_thenHerbivorousFoodNeedShouldBeSatisfiedFromWeakerToStrongerDinosaurs() {
        FoodNeed weakerDinosaurFoodNeed = mock(FoodNeed.class);
        when(weakerDinosaurFoodNeed.getFoodConsumption()).thenReturn(FoodConsumption.HERBIVOROUS);
        FoodNeed strongerDinosaurFoodNeed = mock(FoodNeed.class);
        when(strongerDinosaurFoodNeed.getFoodConsumption()).thenReturn(FoodConsumption.HERBIVOROUS);
        InOrder correctOrder = inOrder(weakerDinosaurFoodNeed,strongerDinosaurFoodNeed);
        when(herbivorousStrategy.getFoodNeeds(anyInt(),anyInt()))
                .thenReturn(List.of(weakerDinosaurFoodNeed));
        when(strongerDinosaurHerbivorousStrategy.getFoodNeeds(anyInt(),anyInt()))
                .thenReturn(List.of(strongerDinosaurFoodNeed));
        when(carnivorousStrategy.getFoodNeeds(anyInt(),anyInt())).thenReturn(new ArrayList<>());
        when(strongerDinosaurCarnivorousStrategy.getFoodNeeds(anyInt(),anyInt())).thenReturn(new ArrayList<>());

        herd.feedDinosaurs();

        correctOrder.verify(weakerDinosaurFoodNeed).satisfy();
        correctOrder.verify(strongerDinosaurFoodNeed).satisfy();
    }

    @Test
    public void givenHerd_whenFeedDinosaur_thenCarnivorousFoodNeedShouldBeSatisfiedFromStrongerToWeakerDinosaurs() {
        FoodNeed weakerDinosaurFoodNeed = mock(FoodNeed.class);
        when(weakerDinosaurFoodNeed.getFoodConsumption()).thenReturn(FoodConsumption.CARNIVOROUS);
        FoodNeed strongerDinosaurFoodNeed = mock(FoodNeed.class);
        when(strongerDinosaurFoodNeed.getFoodConsumption()).thenReturn(FoodConsumption.CARNIVOROUS);
        InOrder correctOrder = inOrder(strongerDinosaurFoodNeed,weakerDinosaurFoodNeed);
        when(carnivorousStrategy.getFoodNeeds(anyInt(),anyInt()))
                .thenReturn(List.of(weakerDinosaurFoodNeed));
        when(strongerDinosaurCarnivorousStrategy.getFoodNeeds(anyInt(),anyInt()))
                .thenReturn(List.of(strongerDinosaurFoodNeed));
        when(herbivorousStrategy.getFoodNeeds(anyInt(),anyInt())).thenReturn(new ArrayList<>());
        when(strongerDinosaurHerbivorousStrategy.getFoodNeeds(anyInt(),anyInt())).thenReturn(new ArrayList<>());

        herd.feedDinosaurs();

        correctOrder.verify(strongerDinosaurFoodNeed).satisfy();
        correctOrder.verify(weakerDinosaurFoodNeed).satisfy();
    }

    @Test
    public void givenHerdWithDinosaurs_whenFeedingSomeDinosaurs_thenFastingDinosaursShouldBeRemoved() {
        when(strongerDinosaurCarnivorousStrategy.areFoodNeedsSatisfied()).thenReturn(true);
        when(strongerDinosaurCarnivorousStrategy.getFoodNeeds(anyInt(),anyInt()))
                .thenReturn(new ArrayList<>());
        when(herbivorousStrategy.areFoodNeedsSatisfied()).thenReturn(true);
        when(herbivorousStrategy.getFoodNeeds(anyInt(),anyInt())).thenReturn(new ArrayList<>());
        when(strongerDinosaurHerbivorousStrategy.getFoodNeeds(anyInt(),anyInt())).thenReturn(new ArrayList<>());
        when(carnivorousStrategy.getFoodNeeds(anyInt(),anyInt())).thenReturn(new ArrayList<>());

        herd.feedDinosaurs();

        assertTrue(dinosaurs.contains(aStrongerCarnivorousDinosaur));
        assertTrue(dinosaurs.contains(anHerbivorousDinosaur));
        assertFalse(dinosaurs.contains(aStrongerHerbivorousDinosaur));
        assertFalse(dinosaurs.contains(aCarnivorousDinosaur));
    }
}
