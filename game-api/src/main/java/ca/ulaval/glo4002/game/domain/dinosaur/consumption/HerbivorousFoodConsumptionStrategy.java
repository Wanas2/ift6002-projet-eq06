package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

public class HerbivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {
    private SaladWaterStorage storage;
    public HerbivorousFoodConsumptionStrategy(SaladWaterStorage storage){
        this.storage = storage;
    }
    @Override
    public boolean consumeFood(int weight, int entryTurn, int currentTurn) {
        int starvingFactor = entryTurn == currentTurn ? 2 : 1;
        int waterNeeded = (int) Math.ceil(starvingFactor * weight * 0.6);
        int foodNeeded = (int) Math.ceil(starvingFactor * weight * 0.5 / 200);

        int foodConsumed = storage.giveExactOrMostPossibleSaladDesired(foodNeeded);
        int waterConsumed = storage.giveExactOrMostPossibleWaterDesired(waterNeeded);

        return foodNeeded == foodConsumed && waterNeeded == waterConsumed;
    }
}
