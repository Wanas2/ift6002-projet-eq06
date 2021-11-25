package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.InvalidWeightChangeException;

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
            throw new InvalidWeightChangeException();
        }
        this.weight = newWeight;
    }
}
