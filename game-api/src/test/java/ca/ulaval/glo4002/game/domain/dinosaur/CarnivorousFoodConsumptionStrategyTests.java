package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CarnivorousFoodConsumptionStrategyTests {
    CarnivorousFoodConsumptionStrategy strategy;
    CarnivorousFoodStorage foodStorage;
    int ENTRY_TURN = 1;
    int OTHER_TURN = 3;
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
    public void givenCurrentTurnIsNotEntryTurn_whenConsumingFood_thenItShouldTakeTheRightAmount(){
        strategy.consumeFood(WEIGHT,ENTRY_TURN,OTHER_TURN);

        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_NORMAL_BURGERS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_NORMAL_WATER);
    }

    @Test
    public void givenCurrentTurnIsEntryTurn_whenConsumingFood_thenItShouldTakeTheDoubleOfRightAmount(){
        strategy.consumeFood(WEIGHT,ENTRY_TURN,ENTRY_TURN);

        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_DOUBLE_BURGERS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(EXPECTED_DOUBLE_WATER);
    }
}
