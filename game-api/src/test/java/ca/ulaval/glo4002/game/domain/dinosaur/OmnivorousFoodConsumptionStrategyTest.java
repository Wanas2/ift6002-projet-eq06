package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.OmnivorousFoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.food.FoodStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class OmnivorousFoodConsumptionStrategyTest {

    private final static int OTHER_AGE = 3;
    private final static int WEIGHT = 81;
    private final static int EXPECTED_NORMAL_SALADS = 1;
    private final static int EXPECTED_NORMAL_BURGERS = 1;
    private final static int EXPECTED_NORMAL_WATER = 49;
    private final static int EXPECTED_DOUBLE_SALADS = 1;
    private final static int EXPECTED_DOUBLE_BURGERS = 1;
    private final static int EXPECTED_DOUBLE_WATER = 98;

    private OmnivorousFoodConsumptionStrategy strategy;
    private FoodStorage foodStorage;

    @BeforeEach
    public void setup() {
        foodStorage = mock(FoodStorage.class);
        strategy = new OmnivorousFoodConsumptionStrategy(foodStorage);
    }

    @Test
    public void givenAgeIsNot0_whenConsumingFood_thenItShouldTakeTheRightAmount() {
        strategy.consumeFood(WEIGHT, OTHER_AGE);

        verify(foodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_NORMAL_SALADS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_NORMAL_WATER);
        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_NORMAL_BURGERS);
    }

    @Test
    public void givenAgeIs0_whenConsumingFood_thenItShouldTakeTheDoubleOfRightAmount() {
        strategy.consumeFood(WEIGHT, 0);

        verify(foodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_DOUBLE_SALADS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_DOUBLE_WATER);
        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_DOUBLE_BURGERS);
    }
}
