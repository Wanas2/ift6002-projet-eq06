package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumption;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.HerbivorousFoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.HerbivorousFoodStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HerbivorousFoodConsumptionStrategyTest {

    private final static int OTHER_AGE = 3;
    private final static int WEIGHT = 81;
    private final static int EXPECTED_NORMAL_SALADS = 1;
    private final static int EXPECTED_NORMAL_WATER = 49;
    private final static int EXPECTED_DOUBLE_SALADS = 1;
    private final static int EXPECTED_DOUBLE_WATER = 98;

    private HerbivorousFoodConsumptionStrategy strategy;
    private HerbivorousFoodStorage foodStorage;

    @BeforeEach
    public void setup() {
        foodStorage = mock(HerbivorousFoodStorage.class);
        strategy = new HerbivorousFoodConsumptionStrategy(foodStorage);
    }

    @Test
    public void whenGetFoodNeeds_thenFoodNeedsShouldBeOnlyOneCarnivorousNeed() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, OTHER_AGE);

        assertEquals(1,foodNeeds.size());
        assertEquals(FoodConsumption.HERBIVOROUS,foodNeeds.get(0).getFoodConsumption());
    }

    @Test
    public void givenAgeIsNot0_whenGetFoodNeeds_thenFoodNeedsShouldTakeTheRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, OTHER_AGE);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(foodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_NORMAL_SALADS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesiredToHerbivorous(EXPECTED_NORMAL_WATER);
    }

    @Test
    public void givenAgeIs0_whenGetFoodNeeds_thenFoodNeedsShouldTakeTheDoubleOfRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, 0);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(foodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_DOUBLE_SALADS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesiredToHerbivorous(EXPECTED_DOUBLE_WATER);
    }
}
