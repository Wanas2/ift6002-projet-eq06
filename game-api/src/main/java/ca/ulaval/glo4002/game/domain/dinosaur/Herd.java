package ca.ulaval.glo4002.game.domain.dinosaur;

import java.util.*;

import static java.util.stream.Collectors.toMap;

//TODO : sortir repository de Herd
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

    private Map<Dinosaur, Integer> sortDinosaursByStrength(){
        Map<Dinosaur, Integer> dinosaursStrength = new HashMap<>();
        for(Dinosaur dinosaur : dinosaurs){
            dinosaursStrength.put(dinosaur, dinosaur.calculateStrength());
        }
        return dinosaursStrength.entrySet().stream()
                .sorted(Map.Entry.<Dinosaur, Integer>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (dinosaur, strength) -> dinosaur, LinkedHashMap::new));
    }

    public void increaseAge(){
        for(Dinosaur dino: dinosaurs){
            dino.age();
        }
        dinosaurRepository.syncAll(dinosaurs);
    }
    public void reset(){
        dinosaurs.clear();
        dinosaurRepository.deleteAll();
    }

    public Dinosaur find(String dinosaurName){
        return dinosaurRepository.findByName(dinosaurName);
    }

    public List<Dinosaur> findAll(){
        return dinosaurRepository.findAll();
    }

}
