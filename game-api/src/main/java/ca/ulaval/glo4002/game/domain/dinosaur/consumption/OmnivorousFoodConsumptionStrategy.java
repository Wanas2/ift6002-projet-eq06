package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

import ca.ulaval.glo4002.game.domain.food.FoodStorage;

public class OmnivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {

    private final int STARVING_FACTOR = 2;
    private final double WATER_FACTOR = 0.6;
    private final double SALAD_FACTOR = 0.0025;
    private final double BURGER_FACTOR = 0.001;

    private final FoodStorage storage;

    public OmnivorousFoodConsumptionStrategy(FoodStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean consumeFood(int weight, int age) {
        int starvingFactor = age == 0 ? STARVING_FACTOR : 1;

        int waterNeeded = (int)Math.ceil(starvingFactor*weight*WATER_FACTOR);
        int burgersNeeded = (int)Math.ceil(starvingFactor*weight*BURGER_FACTOR/2);
        int saladsNeeded = (int)Math.ceil(starvingFactor*weight*SALAD_FACTOR/2);

        int burgersConsumed = storage.giveExactOrMostPossibleBurgerDesired(burgersNeeded);
        int saladsConsumed = storage.giveExactOrMostPossibleSaladDesired(saladsNeeded);
        int waterConsumed = storage.giveExactOrMostPossibleWaterDesired(waterNeeded);

        return burgersConsumed == burgersNeeded && saladsConsumed == saladsNeeded
                && waterConsumed == waterNeeded;
    }
}
