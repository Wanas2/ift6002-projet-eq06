package ca.ulaval.glo4002.game.applicationService.dinosaur;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.interfaces.rest.dino.BreedingRequestDTO;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DinosaurService {

    private final DinosaurAssembler dinosaurAssembler;
    private final DinosaurFactory dinosaurFactory;
    private final Herd herd;
    private final Game game;
    private final BabyFetcher babyFetcher;

    public DinosaurService(DinosaurAssembler dinosaurAssembler,
                           DinosaurFactory dinosaurFactory, Herd herd, Game game, BabyFetcher babyFetcher) {
        this.dinosaurAssembler = dinosaurAssembler;
        this.dinosaurFactory = dinosaurFactory;
        this.herd = herd;
        this.game = game;
        this.babyFetcher = babyFetcher;
    }

    public void addDinosaur(DinosaurDTO dinosaurDTO) {
        if(herd.hasDinosaurWithName(dinosaurDTO.name))
            throw new DuplicateNameException();
        Dinosaur dinosaur = dinosaurFactory.create(dinosaurDTO.gender, dinosaurDTO.weight, dinosaurDTO.species,
                dinosaurDTO.name);
        game.addDinosaur(dinosaur);
    }

    public DinosaurDTO showDinosaur(String dinosaurName) {
        Dinosaur dino = herd.getDinosaurWithName(dinosaurName);
        return dinosaurAssembler.toDTO(dino);
    }

    public List<DinosaurDTO> showAllDinosaurs() {
        List<Dinosaur> dinosaur = herd.getAllDinosaurs();
        return dinosaur.stream()
                .map(dinosaurAssembler::toDTO)
                .collect(Collectors.toList());
    }

    public void breedDino(BreedingRequestDTO breedingRequestDTO) {
        String fatherName = breedingRequestDTO.fatherName;
        String motherName = breedingRequestDTO.motherName;

        Dinosaur fatherDinosaur = herd.getDinosaurWithName(fatherName);
        Dinosaur motherDinosaur = herd.getDinosaurWithName(motherName);
        String babyDinoName = breedingRequestDTO.name;
        Optional<BabyDinosaur> babyDinosaur = babyFetcher.fetch(fatherDinosaur, motherDinosaur, babyDinoName);
        if(babyDinosaur.isPresent())
            game.addDinosaur(babyDinosaur.get());
    }
}
