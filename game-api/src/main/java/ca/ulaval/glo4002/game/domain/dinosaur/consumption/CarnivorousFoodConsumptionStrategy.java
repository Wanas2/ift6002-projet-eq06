package ca.ulaval.glo4002.game.domain.dinosaur.consumption;

public class CarnivorousFoodConsumptionStrategy implements FoodConsumptionStrategy {
    private BurgerWaterStorage storage;
    public CarnivorousFoodConsumptionStrategy(BurgerWaterStorage storage){
        this.storage = storage;
    }
    @Override
    public boolean consumeFood(int weight, int entryTurn, int currentTurn) {
        int starvingFactor = entryTurn == currentTurn ? 2 : 1;
        int waterNeeded = (int) Math.ceil(starvingFactor * weight * 0.6);
        int foodNeeded = (int) Math.ceil(starvingFactor * weight * 0.2 / 200);

        int foodConsumed = storage.giveExactOrMostPossibleBurgerDesired(foodNeeded);
        int waterConsumed = storage.giveExactOrMostPossibleWaterDesired(waterNeeded);

        return foodNeeded == foodConsumed && waterNeeded == waterConsumed;
    }
}
