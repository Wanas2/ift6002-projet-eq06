package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.BurgerWaterStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CarnivorousFoodConsumptionStrategyTests {
    CarnivorousFoodConsumptionStrategy strategy;
    BurgerWaterStorage foodStorage;
    int ENTRY_TURN = 1;
    int OTHER_TURN = 3;
    int WEIGHT = 81;

    @BeforeEach
    public void setup(){
        foodStorage = mock(BurgerWaterStorage.class);
        strategy = new CarnivorousFoodConsumptionStrategy(foodStorage);
    }

    @Test
    public void givenCurrentTurnIsNotEntryTurn_whenConsumingFood_thenItShouldTakeTheRightAmount(){
        int expectedBurgers = 1;
        int expectedWater = 49;

        strategy.consumeFood(WEIGHT,ENTRY_TURN,OTHER_TURN);

        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(expectedBurgers);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(expectedWater);
    }

    @Test
    public void givenCurrentTurnIsEntryTurn_whenConsumingFood_thenItShouldTakeTheDoubleOfRightAmount(){
        int expectedBurgers = 1;
        int expectedWater = 98;

        strategy.consumeFood(WEIGHT,ENTRY_TURN,ENTRY_TURN);

        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(expectedBurgers);
        verify(foodStorage).giveExactOrMostPossibleWaterDesired(expectedWater);
    }
}
