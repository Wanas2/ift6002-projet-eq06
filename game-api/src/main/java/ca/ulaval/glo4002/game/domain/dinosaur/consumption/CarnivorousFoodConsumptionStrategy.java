package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

public class CarnivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {

    private final int STARVING_FACTOR = 2;
    private final double WATER_FACTOR = 0.6;
    private final double FOOD_FACTOR = 0.001;

    private final CarnivorousFoodStorage storage;

    public CarnivorousFoodConsumptionStrategy(CarnivorousFoodStorage storage) {
        this.storage = storage;
    }

    @Override
    public boolean consumeFood(int weight, int age) {
        int starvingFactor = age == 0 ? STARVING_FACTOR : 1;
        int waterNeeded = (int)Math.ceil(starvingFactor*weight*WATER_FACTOR);
        int foodNeeded = (int)Math.ceil(starvingFactor*weight*FOOD_FACTOR);

        int foodConsumed = storage.giveExactOrMostPossibleBurgerDesired(foodNeeded);
        int waterConsumed = storage.giveExactOrMostPossibleWaterDesired(waterNeeded);

        return foodNeeded == foodConsumed && waterNeeded == waterConsumed;
    }
}
