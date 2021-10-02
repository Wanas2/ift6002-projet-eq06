package ca.ulaval.glo4002.game.domain.dinosaur;

import java.util.*;

import static java.util.stream.Collectors.toMap;

public class Herd {

    private List<Dinosaur> dinosaurs;
    private DinosaurRepository dinosaurRepository;

    public Herd(DinosaurRepository dinosaurRepository){
        this.dinosaurRepository = dinosaurRepository;
        this.dinosaurs =  dinosaurRepository.findAll();
    }

    private boolean existsByName(String name){
        for (Dinosaur dino: dinosaurs) {
            if(dino.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void add(Dinosaur dinosaur){
        if (!existsByName(dinosaur.getName())){
            dinosaurs.add(dinosaur);
            dinosaurRepository.syncAll(dinosaurs);
        }
    }

    public void feed() {
        List<Dinosaur> dinosaursByPriority = new ArrayList<>(sortDinosaursByStrength().keySet());
        for (Dinosaur dinosaur : dinosaursByPriority) {
            dinosaur.eat();
        }
        removeFastingDinosaurs(dinosaursByPriority);
    }

    private void removeFastingDinosaurs(List<Dinosaur> allDinosaurs){
        for(Dinosaur dinosaur : allDinosaurs){
            if(!dinosaur.isAlive())
                dinosaurs.remove(dinosaur);
        }

        dinosaurRepository.syncAll(dinosaurs);
    }

    private Map<Dinosaur, Float> sortDinosaursByStrength(){
        Map<Dinosaur, Float> dinosaursStrength = new HashMap<>();
        for(Dinosaur dinosaur : dinosaurs){
            dinosaursStrength.put(dinosaur, dinosaur.calculateStrength());
        }
        return dinosaursStrength.entrySet().stream()
                .sorted(Map.Entry.<Dinosaur, Float>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (dinosaur, strength) -> dinosaur, LinkedHashMap::new));
    }

    public void increaseAge(){
        for(Dinosaur dino: dinosaurs){
            dino.age();
        }
    }
    public void reset(){
        dinosaurs.clear();
        dinosaurRepository.deleteAll();
    }

}
