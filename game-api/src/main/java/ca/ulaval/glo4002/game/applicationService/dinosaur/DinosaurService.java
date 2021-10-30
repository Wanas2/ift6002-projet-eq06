package ca.ulaval.glo4002.game.applicationService.dinosaur;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;

import java.util.List;
import java.util.Optional;

public class DinosaurService {

    private final DinosaurFactory dinosaurFactory;
    private final Herd herd;
    private final Game game;
    private final BabyFetcher babyFetcher;

    public DinosaurService(DinosaurFactory dinosaurFactory, Herd herd, Game game, BabyFetcher babyFetcher) {
        this.dinosaurFactory = dinosaurFactory;
        this.herd = herd;
        this.game = game;
        this.babyFetcher = babyFetcher;
    }

    public void addDinosaur(String name, int weight, String gender, String species) {
        if(herd.hasDinoosaurWithName(name))
            throw new DuplicateNameException();
        Dinosaur dinosaur = dinosaurFactory.create(gender, weight, species, name);
        game.addDinosaur(dinosaur);
    }

    public void breedDino(String babyDinosaurName, String fatherName, String motherName) {
        Dinosaur fatherDinosaur = herd.getDinosaurWithName(fatherName);
        Dinosaur motherDinosaur = herd.getDinosaurWithName(motherName);

        Optional<BabyDinosaur> babyDinosaur = babyFetcher.fetch(fatherDinosaur, motherDinosaur, babyDinosaurName);
        if(babyDinosaur.isPresent())
            game.addDinosaur(babyDinosaur.get());
    }

    public Dinosaur showDinosaur(String dinosaurName) {
        return herd.getDinosaurWithName(dinosaurName);
    }

    public List<Dinosaur> showAllDinosaurs() {
        return herd.getAllDinosaurs();
    }
}
