package ca.ulaval.glo4002.game.domain.dinosaur;

import java.util.ArrayList;
import java.util.List;

public class Herd {

    private List<Dinosaur> dinosaurs = new ArrayList<>();

    public boolean existsByName(String name){
        for (Dinosaur dino: dinosaurs) {
            if(dino.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
    public int getSize(){
        return dinosaurs.size();
    }

    public void add(Dinosaur dinosaur){
        dinosaurs.add(dinosaur);
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
