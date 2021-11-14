package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OmnivorousFoodConsumptionStrategyTest {

    private final static int OTHER_AGE = 3;
    private final static int WEIGHT = 81;
    private final static int EXPECTED_NORMAL_SALADS = 1;
    private final static int EXPECTED_NORMAL_BURGERS = 1;
    private final static int EXPECTED_NORMAL_WATER = 24;
    private final static int EXPECTED_DOUBLE_SALADS = 1;
    private final static int EXPECTED_DOUBLE_BURGERS = 1;
    private final static int EXPECTED_DOUBLE_WATER = 48;

    private OmnivorousFoodConsumptionStrategy strategy;
    private HerbivorousFoodStorage herbivorousFoodStorage;
    private CarnivorousFoodStorage carnivorousFoodStorage;

    @BeforeEach
    public void setup() {
        carnivorousFoodStorage = mock(CarnivorousFoodStorage.class);
        herbivorousFoodStorage = mock(HerbivorousFoodStorage.class);
        strategy = new OmnivorousFoodConsumptionStrategy(carnivorousFoodStorage, herbivorousFoodStorage);
    }

    @Test
    public void whenGetFoodNeeds_thenFoodNeedsShouldBeOneCarnivorousAndOneHerbivorousNeed() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, OTHER_AGE);

        assertEquals(2,foodNeeds.size());
        assertTrue(foodNeeds.stream()
                .anyMatch((foodNeed -> foodNeed.getFoodConsumption() == FoodConsumption.CARNIVOROUS)));
        assertTrue(foodNeeds.stream()
                .anyMatch((foodNeed -> foodNeed.getFoodConsumption() == FoodConsumption.HERBIVOROUS)));
    }

    @Test
    public void givenAgeIsNot0_whenGetFoodNeeds_thenFoodNeedsShouldTakeTheRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, OTHER_AGE);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_NORMAL_BURGERS);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleWaterDesiredToCarnivorous(EXPECTED_NORMAL_WATER);
        verify(herbivorousFoodStorage).giveExactOrMostPossibleWaterDesiredToHerbivorous(EXPECTED_NORMAL_WATER);
        verify(herbivorousFoodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_NORMAL_SALADS);
    }

    @Test
    public void givenAgeIs0_whenGetFoodNeeds_thenFoodNeedsShouldTakeTheDoubleOfRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT,0);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_DOUBLE_BURGERS);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleWaterDesiredToCarnivorous(EXPECTED_DOUBLE_WATER);
        verify(herbivorousFoodStorage).giveExactOrMostPossibleWaterDesiredToHerbivorous(EXPECTED_DOUBLE_WATER);
        verify(herbivorousFoodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_DOUBLE_SALADS);
    }
}
