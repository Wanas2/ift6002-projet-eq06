package ca.ulaval.glo4002.game.domain.dinosaur;


public class DinosaurRepositoryImplementation implements DinosaurRepository{

    private Herd herd;

    public DinosaurRepositoryImplementation(Herd herd){
        this.herd = herd;
    }


    public boolean existsByName(String name){
        return herd.existsByName(name);
    }
}
