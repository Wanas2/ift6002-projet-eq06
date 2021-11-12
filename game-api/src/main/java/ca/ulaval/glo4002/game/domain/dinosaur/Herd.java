package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumption;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.NonExistentNameException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Herd {

    private final List<Dinosaur> dinosaurs;

    public Herd(List<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }

    public boolean hasDinosaurWithName(String name) {
        for(Dinosaur dino : dinosaurs) {
            if(dino.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addDinosaur(Dinosaur dinosaur) {
        if(!hasDinosaurWithName(dinosaur.getName())) {
            dinosaurs.add(dinosaur);
        }
    }

    public void feedDinosaurs() {
        List<FoodNeed> herbivorousFoodNeeds = new ArrayList<>();
        List<FoodNeed> carnivorousFoodNeeds = new ArrayList<>();
        List<Dinosaur> dinosaursSortedFromWeakerToStronger =
                dinosaurs.stream().sorted().collect(Collectors.toList());

        for(Dinosaur dinosaur: dinosaursSortedFromWeakerToStronger){
            List<FoodNeed> foodNeeds = dinosaur.askForFood();
            for(FoodNeed foodNeed: foodNeeds){
                if (foodNeed.getFoodConsumption() == FoodConsumption.CARNIVOROUS){
                    carnivorousFoodNeeds.add(0,foodNeed);
                }
                else if (foodNeed.getFoodConsumption() == FoodConsumption.HERBIVOROUS){
                    herbivorousFoodNeeds.add(foodNeed);
                }
            }
        }

        herbivorousFoodNeeds.forEach(FoodNeed::satisfy);
        carnivorousFoodNeeds.forEach(FoodNeed::satisfy);

        removeFastingDinosaurs();
    }

    public void increaseDinosaursAge() {
        for(Dinosaur dino : dinosaurs) {
            dino.age();
        }
    }

    public void reset() {
        dinosaurs.clear();
    }

    public Dinosaur getDinosaurWithName(String dinosaurName) {
        for(Dinosaur dino : dinosaurs) {
            if(dino.getName().equals(dinosaurName)) {
                return dino;
            }
        }
        throw new NonExistentNameException();
    }

    public List<Dinosaur> getAllDinosaurs() {
        return new ArrayList<>(dinosaurs);
    }

    private void removeFastingDinosaurs() {
        dinosaurs.removeIf((dinosaur) -> !dinosaur.isAlive());
    }
}
