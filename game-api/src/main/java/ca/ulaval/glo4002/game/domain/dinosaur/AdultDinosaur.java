package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;

public class AdultDinosaur extends Dinosaur{

    private final static int WEIGHT_LIMIT = 100;
    public AdultDinosaur(Species species, int weight, String name, Gender gender, FoodConsumptionStrategy foodConsumptionStrategy) {
        super(species, weight, name, gender, foodConsumptionStrategy);
    }

    @Override
    public boolean isAlive() {
        return isAlive && foodConsumptionStrategy.areFoodNeedsSatisfied();
    }

    @Override
    public void modifyWeight(int weightValue) {
        int newWeight = this.weight + weightValue;
        if(newWeight < WEIGHT_LIMIT) {
            System.out.println("Impossible"); // A remplacer par l'exception INVALID_WEIGHT_CHANGE
        }
        this.weight = newWeight;
    }
}
