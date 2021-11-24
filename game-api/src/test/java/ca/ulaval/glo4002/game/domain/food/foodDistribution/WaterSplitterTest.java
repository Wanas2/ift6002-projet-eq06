package ca.ulaval.glo4002.game.domain.food.foodDistribution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterSplitterTest {

    private WaterSplitter waterSplitter;

    @BeforeEach
    void setUp() {
        waterSplitter = new WaterSplitter();
    }

    @Test
    public void initiallyBothWaterContainersAreEmpty() {
        assertTrue(waterSplitter.getWaterForCarnivorous().isEmpty());
        assertTrue(waterSplitter.getWaterForHerbivorous().isEmpty());
    }

    @Test
    public void givenFreshWater_whenSplitWater_thenBothWaterContainersNowContainsWater() {


    }
}
