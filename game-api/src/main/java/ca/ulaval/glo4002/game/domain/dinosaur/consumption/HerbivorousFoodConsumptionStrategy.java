package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

import java.util.ArrayList;
import java.util.List;

public class HerbivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {

    private final int STARVING_FACTOR = 2;
    private final double WATER_FACTOR = 0.6;
    private final double FOOD_FACTOR = 0.0025;

    private final HerbivorousFoodStorage herbivorousFoodStorage;
    private HerbivorousFoodNeed herbivorousFoodNeed;

    public HerbivorousFoodConsumptionStrategy(HerbivorousFoodStorage herbivorousFoodStorage) {
        this.herbivorousFoodStorage = herbivorousFoodStorage;
    }

    @Override
    public List<FoodNeed> getFoodNeeds(int weight, int age) {
        int starvingFactor = age == 0 ? STARVING_FACTOR : 1;
        int waterNeeded = (int)Math.ceil(starvingFactor*weight*WATER_FACTOR);
        int saladNeeded = (int)Math.ceil(starvingFactor*weight*FOOD_FACTOR);
        herbivorousFoodNeed = new HerbivorousFoodNeed(herbivorousFoodStorage,saladNeeded,waterNeeded);

        List<FoodNeed> needs = new ArrayList<>();
        needs.add(herbivorousFoodNeed);
        return needs;
    }

    @Override
    public boolean areFoodNeedsSatisfied() {
        return herbivorousFoodNeed == null || herbivorousFoodNeed.isSatisfied();
    }
}
