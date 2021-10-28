package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumption;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;

public class Dinosaur implements Comparable<Dinosaur> {

    private Species species;
    private boolean isAlive = true;
    private int weight;
    private String name;
    private Gender gender;
    private FoodConsumptionStrategy foodConsumptionStrategy;
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
        return isAlive;
    }

    public void eat() {
        isAlive = foodConsumptionStrategy.consumeFood(weight, age);
    }

    public int calculateStrength() {
        return (int)Math.ceil(weight*gender.getStrengthFactor()*species.getStrength());
    }

    @Override
    public int compareTo(Dinosaur dinosaur) {
        return this.name.compareTo(dinosaur.name);
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
}
