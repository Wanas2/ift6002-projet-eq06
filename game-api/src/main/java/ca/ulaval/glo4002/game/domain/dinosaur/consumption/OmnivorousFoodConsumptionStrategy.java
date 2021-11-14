package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

import java.util.ArrayList;
import java.util.List;

public class OmnivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {

    private final int STARVING_FACTOR = 2;
    private final double WATER_FACTOR = 0.6;
    private final double SALAD_FACTOR = 0.0025;
    private final double BURGER_FACTOR = 0.001;

    private final CarnivorousFoodStorage carnivorousFoodStorage;
    private final HerbivorousFoodStorage herbivorousFoodStorage;
    private HerbivorousFoodNeed herbivorousFoodNeed;
    private CarnivorousFoodNeed carnivorousFoodNeed;

    public OmnivorousFoodConsumptionStrategy(CarnivorousFoodStorage carnivorousFoodStorage,
                                             HerbivorousFoodStorage herbivorousFoodStorage) {
        this.carnivorousFoodStorage = carnivorousFoodStorage;
        this.herbivorousFoodStorage = herbivorousFoodStorage;
    }

    @Override
    public List<FoodNeed> getFoodNeeds(int weight, int age) {
        int starvingFactor = age == 0 ? STARVING_FACTOR : 1;
        int waterNeeded = (int)Math.floor(starvingFactor*weight*WATER_FACTOR/2);

        int saladNeeded = (int)Math.ceil(starvingFactor*weight*SALAD_FACTOR);
        herbivorousFoodNeed = new HerbivorousFoodNeed(herbivorousFoodStorage,saladNeeded,waterNeeded);

        int burgerNeeded = (int)Math.ceil(starvingFactor*weight*BURGER_FACTOR);
        carnivorousFoodNeed = new CarnivorousFoodNeed(carnivorousFoodStorage,burgerNeeded,waterNeeded);

        List<FoodNeed> needs = new ArrayList<>();
        needs.add(carnivorousFoodNeed);
        needs.add(herbivorousFoodNeed);
        return needs;
    }

    @Override
    public boolean areFoodNeedsSatisfied() {
        return (carnivorousFoodNeed == null || carnivorousFoodNeed.isSatisfied()) &&
                (herbivorousFoodNeed == null || herbivorousFoodNeed.isSatisfied());
    }
}
