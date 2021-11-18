package ca.ulaval.glo4002.game.domain.dinosaur.herd;

import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodNeed;
import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.NonExistentNameException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Herd {

    private final List<Dinosaur> dinosaurs;
    private final List<DinosaurFeeder> dinosaurFeeders;

    public Herd(List<Dinosaur> dinosaurs, List<DinosaurFeeder> dinosaurFeeders) {
        this.dinosaurs = dinosaurs;
        this.dinosaurFeeders = dinosaurFeeders;
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
        Map<Dinosaur,List<FoodNeed>> dinosaursWithNeed = new HashMap<>();
        dinosaurs.forEach(dinosaur -> dinosaursWithNeed.put(dinosaur,dinosaur.askForFood()));

        dinosaurFeeders.forEach(dinosaurFeeder -> dinosaurFeeder.feedDinosaurs(dinosaursWithNeed));

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
        return dinosaurs;
    }

    private void removeFastingDinosaurs() {
        dinosaurs.removeIf((dinosaur) -> !dinosaur.isAlive());
    }

    public void organizeSumoFight(Dinosaur firstDinosaurFighter, Dinosaur secondDinosaurFighter) {

    }
}
