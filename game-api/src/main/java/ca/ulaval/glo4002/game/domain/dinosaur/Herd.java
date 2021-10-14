package ca.ulaval.glo4002.game.domain.dinosaur;

import ca.ulaval.glo4002.game.domain.dinosaur.exceptions.NonExistentNameException;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Herd {

    private List<Dinosaur> dinosaurs;

    public Herd(List<Dinosaur> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }

    public boolean hasDinoosaurWithName(String name) {
        for(Dinosaur dino : dinosaurs) {
            if(dino.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addDinosaur(Dinosaur dinosaur) {
        if(!hasDinoosaurWithName(dinosaur.getName())) {
            dinosaurs.add(dinosaur);
        }
    }

    public void feedDinosaurs() {
        List<Dinosaur> dinosaursByPriority = new ArrayList<>(sortDinosaursByStrength().keySet());
        for(Dinosaur dinosaur : dinosaursByPriority) {
            dinosaur.eat();
        }
        removeFastingDinosaurs(dinosaursByPriority);
    }

    private void removeFastingDinosaurs(List<Dinosaur> allDinosaurs) {
        for(Dinosaur dinosaur : allDinosaurs) {
            if(!dinosaur.isAlive())
                dinosaurs.remove(dinosaur);
        }
    }

    private Map<Dinosaur, Integer> sortDinosaursByStrength() {
        Map<Dinosaur, Integer> dinosaursStrength = new HashMap<>();
        for(Dinosaur dinosaur : dinosaurs) {
            dinosaursStrength.put(dinosaur, dinosaur.calculateStrength());
        }
        return dinosaursStrength.entrySet().stream()
                .sorted(Map.Entry.<Dinosaur, Integer>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (dinosaur, strength)->dinosaur, LinkedHashMap::new));
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
}
