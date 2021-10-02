package ca.ulaval.glo4002.game.domain.dinosaur;


import java.util.ArrayList;
import java.util.List;

public class DinosaurRepositoryInMemoryImpl implements DinosaurRepository{

    private List<Dinosaur> dinoStorage;

    public DinosaurRepositoryInMemoryImpl(){
        this.dinoStorage = new ArrayList<>();
    }


    public boolean existsByName(String name){
        for (Dinosaur dino: dinoStorage){
            if (dino.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public List<Dinosaur> findAll(){
        return new ArrayList<>(dinoStorage);
    }

    public void syncAll(List<Dinosaur> dinosaurs){
        this.dinoStorage = dinosaurs;
    }

    public void deleteAll(){
        this.dinoStorage = new ArrayList<>();
    }
}