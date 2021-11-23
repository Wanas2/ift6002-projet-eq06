package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;

import java.util.List;

abstract public class Dinosaur implements Comparable<Dinosaur> {

    private Species species;
    protected int weight;
    private String name;
    private Gender gender;
    protected final FoodConsumptionStrategy foodConsumptionStrategy;
    protected boolean isAlive = true;
    private boolean isStarving = true;

    public Dinosaur(Species species, int weight, String name, Gender gender,
                    FoodConsumptionStrategy foodConsumptionStrategy) {
        this.species = species;
        this.weight = weight;
        this.name = name;
        this.gender = gender;
        this.foodConsumptionStrategy = foodConsumptionStrategy;
    }

    abstract public boolean isAlive();

    abstract public void modifyWeight(int weightValue);

    public List<FoodNeed> askForFood() {
        List<FoodNeed> foodNeeds = isStarving ? foodConsumptionStrategy.getStarvingFoodNeeds(weight) :
                            foodConsumptionStrategy.getNonStarvingFoodNeeds(weight);
        isStarving = false;
        return foodNeeds;
    }

    @Override
    public int compareTo(Dinosaur dinosaur) {
        int comparingStrength = Integer.compare(this.calculateStrength(), dinosaur.calculateStrength());
        return comparingStrength != 0 ? comparingStrength : -this.name.compareTo(dinosaur.name);
    }

    public int compareStrength(Dinosaur dinosaur) {
        return Integer.compare(this.calculateStrength(), dinosaur.calculateStrength());
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Gender getGender() {
        return gender;
    }

    public Species getSpecies() {
        return species;
    }

    public void loseFight() {
        isAlive = false;
    }

    public void winFight() {
        isStarving = true;
    }

    private int calculateStrength() {
        return (int)Math.ceil(weight*gender.getGenderFactor()*species.getConsumptionStrength());
    }
}
