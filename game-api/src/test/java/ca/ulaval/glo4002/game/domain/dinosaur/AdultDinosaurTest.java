package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdultDinosaurTest {

    private final static int DINOSAUR_WEIGHT = 87;
    private final static int STRONGER_DINOSAUR_WEIGHT = 9999;
    private final static int WEAKER_THAN = -1;

    private AdultDinosaur adultDinosaur;
    private AdultDinosaur aStrongerDinosaur;
    private FoodConsumptionStrategy aFoodConsumptionStrategy;

    @BeforeEach
    public void setup() {
        aFoodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        adultDinosaur = new AdultDinosaur(Species.Ankylosaurus, DINOSAUR_WEIGHT, "Bobi", Gender.F,
                aFoodConsumptionStrategy);
        aStrongerDinosaur = new AdultDinosaur(Species.Ankylosaurus, STRONGER_DINOSAUR_WEIGHT, "Bob",
                Gender.F, aFoodConsumptionStrategy);
    }

    @Test
    public void givenADinosaurWithFoodNeedsNotSatisfied_whenIsAlive_thenDinosaurShouldNotBeALive() {
        when(aFoodConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(false);

        boolean isAlive = adultDinosaur.isAlive();

        assertFalse(isAlive);
    }

    @Test
    public void givenADinosaurWithFoodNeedsSatisfied_whenIsAlive_thenDinosaurShouldBeAlive() {
        when(aFoodConsumptionStrategy.areFoodNeedsSatisfied()).thenReturn(true);

        boolean isAlive = adultDinosaur.isAlive();

        assertTrue(isAlive);
    }

    @Test
    public void whenLoseFight_thenDinosaurShouldNotBeAlive() {
        adultDinosaur.loseFight();

        assertFalse(adultDinosaur.isAlive());
    }

    @Test
    public void whenWinFight_thenDinosaurShouldBeStarving() {
        adultDinosaur.winFight();

        adultDinosaur.askForFood();
        verify(aFoodConsumptionStrategy).getStarvingFoodNeeds(anyInt());
    }

    @Test
    public void givenDinosaurIsStarving_whenAskForFood_thenDinosaurShouldGetStarvingFoodNeed() {
        adultDinosaur.askForFood();

        verify(aFoodConsumptionStrategy).getStarvingFoodNeeds(DINOSAUR_WEIGHT);
    }

    @Test
    public void givenDinosaurIsNotStarving_whenAskForFood_thenDinosaurShouldGetNormalFoodNeed() {
        adultDinosaur.askForFood();

        adultDinosaur.askForFood();

        verify(aFoodConsumptionStrategy).getNonStarvingFoodNeeds(DINOSAUR_WEIGHT);
    }

    @Test
    public void givenAStrongerDinosaur_whenCompareStrength_thenDinosaurShouldBeWeakerThanTheStronger() {
        int strengthComparison = adultDinosaur.compareStrength(aStrongerDinosaur);

        assertEquals(WEAKER_THAN,strengthComparison);
    }
}
