package ca.ulaval.glo4002.game.domain.dinosaur;

import java.util.ArrayList;
import java.util.List;

public class Herd {

    private List<Dinosaur> dinosaurs;
    private DinosaurRepository dinosaurRepository;

    public Herd(DinosaurRepository dinosaurRepository){
        this.dinosaurRepository = (DinosaurRepositoryInMemoryImpl)dinosaurRepository;
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
