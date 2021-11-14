package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.CarnivorousFoodStorage;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumption;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CarnivorousFoodConsumptionStrategyTest {

    private final static int OTHER_AGE = 4;
    private final static int WEIGHT = 81;
    private final static int EXPECTED_NORMAL_BURGERS = 1;
    private final static int EXPECTED_NORMAL_WATER = 49;
    private final static int EXPECTED_DOUBLE_BURGERS = 1;
    private final static int EXPECTED_DOUBLE_WATER = 98;

    private CarnivorousFoodConsumptionStrategy strategy;
    private CarnivorousFoodStorage foodStorage;

    @BeforeEach
    public void setup() {
        foodStorage = mock(CarnivorousFoodStorage.class);
        strategy = new CarnivorousFoodConsumptionStrategy(foodStorage);
    }

    @Test
    public void whenGetFoodNeeds_thenFoodNeedsShouldBeOnlyOneCarnivorousNeed() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, OTHER_AGE);

        assertEquals(1,foodNeeds.size());
        assertEquals(FoodConsumption.CARNIVOROUS,foodNeeds.get(0).getFoodConsumption());
    }

    @Test
    public void givenAgeIsNot0_whenGetFoodNeeds_thenFoodNeedsShouldTakeTheRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, OTHER_AGE);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_NORMAL_BURGERS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesiredToCarnivorous(EXPECTED_NORMAL_WATER);
    }

    @Test
    public void givenAgeIs0_whenGetFoodNeeds_thenFoodNeedsShouldTakeTheDoubleOfRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getFoodNeeds(WEIGHT, 0);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(foodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_DOUBLE_BURGERS);
        verify(foodStorage).giveExactOrMostPossibleWaterDesiredToCarnivorous(EXPECTED_DOUBLE_WATER);
    }
}
