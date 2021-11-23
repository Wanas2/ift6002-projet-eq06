package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;

import java.util.Optional;

public class BabyDinosaur extends Dinosaur {

    protected final static int INITIAL_BABY_WEIGHT = 1;
    private final static int BABY_WEIGHT_TO_ADD = 33;
    private final static int WEIGHT_TO_BECOME_ADULT = 100;

    protected Dinosaur fatherDinosaur;
    protected Dinosaur motherDinosaur;

    public BabyDinosaur(Species species, String name, Gender gender,
                        FoodConsumptionStrategy foodConsumptionStrategy, Dinosaur fatherDinosaur,
                        Dinosaur motherDinosaur) {
        super(species, INITIAL_BABY_WEIGHT, name, gender, foodConsumptionStrategy);
        this.fatherDinosaur = fatherDinosaur;
        this.motherDinosaur = motherDinosaur;
    }

    @Override
    public boolean isAlive() {
        return isAlive && (fatherDinosaur.isAlive() || motherDinosaur.isAlive());
    }

    @Override
    public void modifyWeight(int weightValue) {
        System.out.println("Impossible"); // A remplacer par l'exception INVALID_BABY_WEIGHT_CHANGE
    }

    public void increaseWeight() {
        if(this.weight < WEIGHT_TO_BECOME_ADULT) {
            this.weight += BABY_WEIGHT_TO_ADD;
        }
    }

    public Optional<AdultDinosaur> becomeAdult() {
        Optional<AdultDinosaur> adultDinosaur = Optional.empty();
        if (this.weight >= WEIGHT_TO_BECOME_ADULT) {
            adultDinosaur = Optional.of(new AdultDinosaur(this.getSpecies(), this.weight,
                    this.getName(), this.getGender(), this.foodConsumptionStrategy));
        }

        return adultDinosaur;
    }
}
