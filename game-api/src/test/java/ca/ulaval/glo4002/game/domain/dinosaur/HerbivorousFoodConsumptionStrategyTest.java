package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.HerbivorousFoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.HerbivorousFoodStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class HerbivorousFoodConsumptionStrategyTest {
    HerbivorousFoodConsumptionStrategy strategy;
    HerbivorousFoodStorage foodStorage;
    int OTHER_AGE = 3;
    int WEIGHT = 81;
    int EXPECTED_NORMAL_SALADS = 1;
    int EXPECTED_NORMAL_WATER = 49;
    int EXPECTED_DOUBLE_SALADS = 1;
    int EXPECTED_DOUBLE_WATER = 98;

    @BeforeEach
    public void setup(){
        foodStorage = mock(HerbivorousFoodStorage.class);
        strategy = new HerbivorousFoodConsumptionStrategy(foodStorage);
    }

    @Test
    public void givenAgeIsNot0_whenConsumingFood_thenItShouldTakeTheRightAmount(){
        strategy.consumeFood(WEIGHT,OTHER_AGE);

        verify(foodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_NORMAL_SALADS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_NORMAL_WATER);
    }

    @Test
    public void givenAgeIs0_whenConsumingFood_thenItShouldTakeTheDoubleOfRightAmount(){
        strategy.consumeFood(WEIGHT,0);

        verify(foodStorage).giveExactOrMostPossibleSaladDesired(EXPECTED_DOUBLE_SALADS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_DOUBLE_WATER);
    }
}
