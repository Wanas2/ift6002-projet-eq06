package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;

public class AdultDinosaur extends Dinosaur{

    private final static int MINIMUM_WEIGHT = 100;

    public AdultDinosaur(Species species, int weight, String name, Gender gender,
                         FoodConsumptionStrategy foodConsumptionStrategy) {
        super(species, weight, name, gender, foodConsumptionStrategy);
    }

    @Override
    public void modifyWeight(int weightVariation) {
        int newWeight = this.weight + weightVariation;
        if(newWeight < MINIMUM_WEIGHT) {
            System.out.println("Impossible"); // TODO A remplacer par l'exception INVALID_WEIGHT_CHANGE
        }
        this.weight = newWeight;
    }
}
