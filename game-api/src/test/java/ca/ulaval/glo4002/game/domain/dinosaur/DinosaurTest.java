package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DinosaurTest {

    private final static int DINOSAUR_WEIGHT = 87;
    private final static int STRONGER_DINOSAUR_WEIGHT = 9999;
    private final static int WEAKER_THAN = -1;

    private Dinosaur aDinosaur;
    private Dinosaur aStrongerDinosaur;
    private FoodConsumptionStrategy aFoodConsumptionStrategy;

    @BeforeEach
    public void setup() {
        aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        aDinosaur = new Dinosaur(Species.Ankylosaurus, DINOSAUR_WEIGHT, "Bobi", Gender.F,
                aFoodConsumptionStrategy);
        aStrongerDinosaur = new Dinosaur(Species.Ankylosaurus, STRONGER_DINOSAUR_WEIGHT, "Bob",
                Gender.F, aFoodConsumptionStrategy);
    }

    @Test
    public void givenADinosaurWithFoodNeedsNotSatisfied_whenIsAlive_thenDinosaurShouldNotBeALive() {
        when(aFoodConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(false);

        boolean isAlive = aDinosaur.isAlive();

        assertFalse(isAlive);
    }

    @Test
    public void givenADinosaurWithFoodNeedsSatisfied_whenIsAlive_thenDinosaurShouldBeAlive() {
        when(aFoodConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(true);

        boolean isAlive = aDinosaur.isAlive();

        assertTrue(isAlive);
    }

    @Test
    public void whenLoseFight_thenDinosaurShouldNotBeAlive() {
        aDinosaur.loseFight();

        assertFalse(aDinosaur.isAlive());
    }

    @Test
    public void whenWinFight_thenDinosaurShouldBeStarving() {
        aDinosaur.winFight();

        aDinosaur.askForFood();
        verify(aFoodConsumptionStrategy).getStarvingFoodNeeds(anyInt());
    }

    @Test
    public void givenDinosaurIsStarving_whenAskForFood_thenDinosaurShouldGetStarvingFoodNeed() {
        aDinosaur.askForFood();

        verify(aFoodConsumptionStrategy).getStarvingFoodNeeds(DINOSAUR_WEIGHT);
    }

    @Test
    public void givenDinosaurIsNotStarving_whenAskForFood_thenDinosaurShouldGetNormalFoodNeed() {
        aDinosaur.age();

        aDinosaur.askForFood();

        verify(aFoodConsumptionStrategy).getNormalFoodNeeds(DINOSAUR_WEIGHT);
    }

    @Test
    public void givenAStrongerDinosaur_whenCompareStrength_thenDinosaurShouldBeWeakerThanTheStronger() {
        int strengthComparison = aDinosaur.compareStrength(aStrongerDinosaur);

        assertEquals(WEAKER_THAN,strengthComparison);
    }
}
