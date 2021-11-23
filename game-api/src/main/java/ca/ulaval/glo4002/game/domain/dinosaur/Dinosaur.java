package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;

import java.util.List;

public class Dinosaur{

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
        List<FoodNeed> foodNeeds = isStarving ? foodConsumptionStrategy.getStarvingFoodNeeds(weight) :
                            foodConsumptionStrategy.getNonStarvingFoodNeeds(weight);
        isStarving = false;
        return foodNeeds;
    }

    public void loseFight() {
        isAlive = false;
    }

    public void winFight() {
        isStarving = true;
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

    public int compareStrength(Dinosaur dinosaur) {
        return Integer.compare(this.calculateStrength(), dinosaur.calculateStrength());
    }

    private int calculateStrength() {
        return (int)Math.ceil(weight*gender.getGenderFactor()*species.getConsumptionStrength());
    }
}
