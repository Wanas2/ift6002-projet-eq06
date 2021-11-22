package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;

import java.util.List;

public class Dinosaur implements Comparable<Dinosaur> {

    private Species species;
    private int weight;
    private String name;
    private Gender gender;
    private final FoodConsumptionStrategy foodConsumptionStrategy;
    private boolean isAlive = true;
    private boolean isStarving = true;

    public Dinosaur(Species species, int weight, String name, Gender gender,
                    FoodConsumptionStrategy foodConsumptionStrategy) {
        this.species = species;
        this.weight = weight;
        this.name = name;
        this.gender = gender;
        this.foodConsumptionStrategy = foodConsumptionStrategy;
    }

    public boolean isAlive() {
        return isAlive && foodConsumptionStrategy.areFoodNeedsSatisfied();
    }

    public List<FoodNeed> askForFood() {
        return isStarving ? foodConsumptionStrategy.getStarvingFoodNeeds(weight) :
                            foodConsumptionStrategy.getNormalFoodNeeds(weight);
    }

    @Override
    public int compareTo(Dinosaur dinosaur) {
        int comparingStrength = Integer.compare(this.calculateStrength(), dinosaur.calculateStrength());
        return comparingStrength != 0 ? comparingStrength : -this.name.compareTo(dinosaur.name);
    }

    public int compareStrength(Dinosaur dinosaur) {
        return Integer.compare(this.calculateStrength(), dinosaur.calculateStrength());
    }

    public void age() {
        isStarving = false;
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
