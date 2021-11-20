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
    private int age;

    public Dinosaur(Species species, int weight, String name, Gender gender,
                    FoodConsumptionStrategy foodConsumptionStrategy) {
        this.species = species;
        this.weight = weight;
        this.name = name;
        this.gender = gender;
        this.foodConsumptionStrategy = foodConsumptionStrategy;
        this.age = 0;
    }

    public boolean isAlive() {
        return foodConsumptionStrategy.areFoodNeedsSatisfied();
    }

    public List<FoodNeed> askForFood() {
        return foodConsumptionStrategy.getFoodNeeds(weight,age);
    }

    public void age() {
        age++;
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

    private int calculateStrength() {
        return (int)Math.ceil(weight*gender.getGenderFactor()*species.getConsumptionStrength());
    }

    public int compareStrength(Dinosaur dinosaur) {
        return Integer.compare(this.calculateStrength(), dinosaur.calculateStrength());
    }
}
