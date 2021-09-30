package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

public class HerbivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {
    private HerbivorousFoodStorage storage;
    private final int STARVING_FACTOR = 2;
    private final double WATER_FACTOR = 0.6;
    private final double FOOD_FACTOR = 0.005;
    public HerbivorousFoodConsumptionStrategy(HerbivorousFoodStorage storage){
        this.storage = storage;
    }
    @Override
    public boolean consumeFood(int weight, int entryTurn, int currentTurn) {
        int starvingFactor = entryTurn == currentTurn ? STARVING_FACTOR : 1;
        int waterNeeded = (int) Math.ceil(starvingFactor * weight * WATER_FACTOR);
        int foodNeeded = (int) Math.ceil(starvingFactor * weight * FOOD_FACTOR);

        int foodConsumed = storage.giveExactOrMostPossibleSaladDesired(foodNeeded);
        int waterConsumed = storage.giveExactOrMostPossibleWaterDesired(waterNeeded);

        return foodNeeded == foodConsumed && waterNeeded == waterConsumed;
    }
}
