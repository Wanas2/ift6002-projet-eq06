package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;

public class Dinosaur {
    private Species species;
    private int entryTurn;
    private boolean isAlive = true;
    private int weight;
    private String name;
    private Gender gender;
    private FoodConsumptionStrategy foodConsumptionStrategy;

    public Dinosaur(Species species, int entryTurn,
                    int weight, String name, Gender gender,FoodConsumptionStrategy foodConsumptionStrategy) {
        this.species = species;
        this.entryTurn = entryTurn;
        this.weight = weight;
        this.name = name;
        this.gender = gender;
        this.foodConsumptionStrategy = foodConsumptionStrategy;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public void eat(int currentTurn){
        isAlive = foodConsumptionStrategy.consumeFood(weight,entryTurn,currentTurn);
    }
}
