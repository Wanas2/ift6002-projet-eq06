package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;

public class Dinosaur {
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

    public boolean isAlive(){
        return isAlive;
    }

    public void eat(){
        isAlive = foodConsumptionStrategy.consumeFood(weight, age);
    }

    public void age(){
        age++;
    }
}
