package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class CarnivorousFoodConsumptionStrategyTest {

    private final static int WEIGHT = 81;
    private final static int EXPECTED_NORMAL_BURGERS = 1;
    private final static int EXPECTED_NORMAL_WATER = 49;
    private final static int EXPECTED_DOUBLE_BURGERS = 1;
    private final static int EXPECTED_DOUBLE_WATER = 98;

    private CarnivorousFoodConsumptionStrategy strategy;
    private CarnivorousFoodStorage carnivorousFoodStorage;

    @BeforeEach
    public void setup() {
        carnivorousFoodStorage = mock(CarnivorousFoodStorage.class);
        strategy = new CarnivorousFoodConsumptionStrategy(carnivorousFoodStorage);
    }

    @Test
    public void whenGetNormalFoodNeeds_thenFoodNeedsShouldBeOnlyOneCarnivorousNeed() {
        List<FoodNeed> foodNeeds = strategy.getNormalFoodNeeds(WEIGHT);

        assertEquals(1,foodNeeds.size());
        assertEquals(FoodConsumption.CARNIVOROUS,foodNeeds.get(0).getFoodConsumption());
    }

    @Test
    public void whenGetStarvingFoodNeeds_thenFoodNeedsShouldBeOnlyOneCarnivorousNeed() {
        List<FoodNeed> foodNeeds = strategy.getStarvingFoodNeeds(WEIGHT);

        assertEquals(1,foodNeeds.size());
        assertEquals(FoodConsumption.CARNIVOROUS,foodNeeds.get(0).getFoodConsumption());
    }

    @Test
    public void whenGetNormalFoodNeeds_thenFoodNeedsShouldTakeTheRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getNormalFoodNeeds(WEIGHT);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_NORMAL_BURGERS);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleWaterDesiredToCarnivorous(EXPECTED_NORMAL_WATER);
    }

    @Test
    public void whenGetStarvingFoodNeeds_thenFoodNeedsShouldTakeTheDoubleOfRightAmount() {
        List<FoodNeed> foodNeeds = strategy.getStarvingFoodNeeds(WEIGHT);

        foodNeeds.forEach(FoodNeed::satisfy);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleBurgerDesired(EXPECTED_DOUBLE_BURGERS);
        verify(carnivorousFoodStorage).giveExactOrMostPossibleWaterDesiredToCarnivorous(EXPECTED_DOUBLE_WATER);
    }

    @Test
    public void givenFoodNeedsAreSatisfied_whenAreFoodNeedsSatisfied_thenShouldBeSatisfied() {
        when(carnivorousFoodStorage.giveExactOrMostPossibleBurgerDesired(EXPECTED_NORMAL_BURGERS))
                .thenReturn(EXPECTED_NORMAL_BURGERS);
        when(carnivorousFoodStorage.giveExactOrMostPossibleWaterDesiredToCarnivorous(EXPECTED_NORMAL_WATER))
                .thenReturn(EXPECTED_NORMAL_WATER);
        List<FoodNeed> foodNeeds = strategy.getNormalFoodNeeds(WEIGHT);
        foodNeeds.forEach(FoodNeed::satisfy);

        boolean areFoodNeedsSatisfied = strategy.areFoodNeedsSatisfied();

        assertTrue(areFoodNeedsSatisfied);
    }
}
