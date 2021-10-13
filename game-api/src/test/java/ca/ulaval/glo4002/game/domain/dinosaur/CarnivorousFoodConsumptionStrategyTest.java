package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CarnivorousFoodConsumptionStrategyTest {
    CarnivorousFoodConsumptionStrategy strategy;
    CarnivorousFoodStorage foodStorage;
    int OTHER_AGE = 4;
    int WEIGHT = 81;
    int EXPECTED_NORMAL_BURGERS = 1;
    int EXPECTED_NORMAL_WATER = 49;
    int EXPECTED_DOUBLE_BURGERS = 1;
    int EXPECTED_DOUBLE_WATER = 98;

    @BeforeEach
    public void setup(){
        foodStorage = mock(CarnivorousFoodStorage.class);
        strategy = new CarnivorousFoodConsumptionStrategy(foodStorage);
    }

    @Test
    public void givenAgeIsNot0_whenConsumingFood_thenItShouldTakeTheRightAmount(){
        strategy.consumeFood(WEIGHT,OTHER_AGE);

        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_NORMAL_BURGERS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_NORMAL_WATER);
    }

    @Test
    public void givenAgeIs0_whenConsumingFood_thenItShouldTakeTheDoubleOfRightAmount(){
        strategy.consumeFood(WEIGHT,0);

        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_DOUBLE_BURGERS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_DOUBLE_WATER);
    }
}
