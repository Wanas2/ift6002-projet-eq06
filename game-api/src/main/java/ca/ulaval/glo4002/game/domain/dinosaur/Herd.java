package ca.ulaval.glo4002.game.domain.dinosaur;

import java.util.ArrayList;
import java.util.List;

public class Herd {

    private List<Dinosaur> dinosaurs = new ArrayList<>();
    private DinosaurRepositoryInMemoryImpl dinosaurRepositoryInMemory;

    public Herd(DinosaurRepository dinosaurRepositoryInMemory){
        this.dinosaurRepositoryInMemory = (DinosaurRepositoryInMemoryImpl)dinosaurRepositoryInMemory;
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
            dinosaurRepositoryInMemory.syncAll(dinosaurs);
        }
    }

    public void increaseAge(){
        for(Dinosaur dino: dinosaurs){
            dino.age();
        }
    }
    public void reset(){
        dinosaurs.clear();
    }

}
