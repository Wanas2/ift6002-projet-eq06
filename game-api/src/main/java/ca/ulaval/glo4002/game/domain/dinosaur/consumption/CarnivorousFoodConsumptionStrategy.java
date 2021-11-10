package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

import java.util.ArrayList;
import java.util.List;

public class CarnivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {

    private final int STARVING_FACTOR = 2;
    private final double WATER_FACTOR = 0.6;
    private final double FOOD_FACTOR = 0.001;

    private final CarnivorousFoodStorage carnivorousFoodStorage;
    private CarnivorousFoodNeed carnivorousFoodNeed;

    public CarnivorousFoodConsumptionStrategy(CarnivorousFoodStorage carnivorousFoodStorage) {
        this.carnivorousFoodStorage = carnivorousFoodStorage;
    }

    @Override
    public List<FoodNeed> getFoodNeeds(int weight, int age) {
        int starvingFactor = age == 0 ? STARVING_FACTOR : 1;
        int waterNeeded = (int)Math.ceil(starvingFactor*weight*WATER_FACTOR);
        int burgerNeeded = (int)Math.ceil(starvingFactor*weight*FOOD_FACTOR);
        carnivorousFoodNeed = new CarnivorousFoodNeed(carnivorousFoodStorage,burgerNeeded,waterNeeded);

        List<FoodNeed> needs = new ArrayList<>();
        needs.add(carnivorousFoodNeed);
        return needs;
    }

    @Override
    public boolean areFoodNeedsSatisfied() {
        return carnivorousFoodNeed == null || carnivorousFoodNeed.isSatisfied();
    }
}
