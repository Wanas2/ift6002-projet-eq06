package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DinosaurTest {

    private final static int DINOSAUR_WEIGHT = 87;
    private final static int STRONGER_DINOSAUR_WEIGHT = 9999;
    private final static int WEAKER_THAN = -1;

    private FoodNeed foodNeed;
    private DinosaurImpl aDinosaurImpl;
    private DinosaurImpl aStrongerDinosaurImpl;
    private FoodConsumptionStrategy aFoodConsumptionStrategy;

    @BeforeEach
    public void setup() {
        foodNeed = mock(FoodNeed.class);
        aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        aDinosaurImpl = new DinosaurImpl(Species.Ankylosaurus, DINOSAUR_WEIGHT, "Bobi", Gender.F,
                aFoodConsumptionStrategy);
        aStrongerDinosaurImpl = new DinosaurImpl(Species.Ankylosaurus, STRONGER_DINOSAUR_WEIGHT, "Bob",
                Gender.F, aFoodConsumptionStrategy);
    }

    @Test
    public void givenADinosaurWithFoodNeedsNotSatisfied_whenIsAlive_thenDinosaurShouldNotBeALive() {
        when(aFoodConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(false);

        boolean isAlive = aDinosaurImpl.isAlive();

        assertFalse(isAlive);
    }

    @Test
    public void givenADinosaurWithFoodNeedsSatisfied_whenIsAlive_thenDinosaurShouldBeAlive() {
        when(aFoodConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(true);

        boolean isAlive = aDinosaurImpl.isAlive();

        assertTrue(isAlive);
    }

    @Test
    public void whenLoseFight_thenDinosaurShouldNotBeAlive() {
        aDinosaurImpl.loseFight();

        assertFalse(aDinosaurImpl.isAlive());
    }

    @Test
    public void whenWinFight_thenDinosaurShouldBeStarving() {
        aDinosaurImpl.winFight();

        aDinosaurImpl.askForFood();
        verify(aFoodConsumptionStrategy).getStarvingFoodNeeds(anyInt());
    }

    @Test
    public void givenDinosaurIsStarving_whenAskForFood_thenDinosaurShouldGetStarvingFoodNeed() {
        aDinosaurImpl.askForFood();

        verify(aFoodConsumptionStrategy).getStarvingFoodNeeds(DINOSAUR_WEIGHT);
    }

    @Test
    public void givenDinosaurIsNotStarving_whenAskForFood_thenDinosaurShouldGetNormalFoodNeed() {
        aDinosaurImpl.askForFood();

        aDinosaurImpl.askForFood();

        verify(aFoodConsumptionStrategy).getNonStarvingFoodNeeds(DINOSAUR_WEIGHT);
    }

    @Test
    public void whenAskForFood_thenShouldReturnFoodNeedList() {
        List<FoodNeed> foodNeeds = new ArrayList<>(Collections.singleton(foodNeed));
        when(aFoodConsumptionStrategy.getStarvingFoodNeeds(DINOSAUR_WEIGHT)).thenReturn(foodNeeds);

        List<FoodNeed> foodNeedsReturned = aDinosaurImpl.askForFood();

        assertEquals(foodNeeds, foodNeedsReturned);
    }

    @Test
    public void givenAStrongerDinosaur_whenCompareStrength_thenDinosaurShouldBeWeakerThanTheStronger() {
        int strengthComparison = aDinosaurImpl.compareStrength(aStrongerDinosaurImpl);

        assertEquals(WEAKER_THAN,strengthComparison);
    }

    static class DinosaurImpl {
        private Species species;
        protected int weight;
        private String name;
        private Gender gender;
        protected final FoodConsumptionStrategy foodConsumptionStrategy;
        private boolean isAlive = true;
        protected boolean isStarving = true;

        public DinosaurImpl(Species species, int weight, String name, Gender gender,
                        FoodConsumptionStrategy foodConsumptionStrategy) {
            this.species = species;
            this.weight = weight;
            this.name = name;
            this.gender = gender;
            this.foodConsumptionStrategy = foodConsumptionStrategy;
        }

        public boolean isAlive() {
            return isAlive && foodConsumptionStrategy.areFoodNeedsSatisfied();
        }

        public List<FoodNeed> askForFood() {
            List<FoodNeed> foodNeeds = isStarving ? foodConsumptionStrategy.getStarvingFoodNeeds(weight) :
                    foodConsumptionStrategy.getNonStarvingFoodNeeds(weight);
            isStarving = false;
            return foodNeeds;
        }

        public void loseFight() {
            isAlive = false;
        }

        public void winFight() {
            isStarving = true;
        }

        public int compareStrength(DinosaurImpl dinosaur) {
            return Integer.compare(this.calculateStrength(), dinosaur.calculateStrength());
        }

        private int calculateStrength() {
            return (int)Math.ceil(weight*gender.getGenderFactor()*species.getConsumptionStrength());
        }
    }
}
