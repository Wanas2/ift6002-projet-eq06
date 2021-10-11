package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.exceptions.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DinosaurService {

    private final DinosaurAssembler dinosaurAssembler;
    private final DinosaurFactory dinosaurFactory;
    private final Herd herd;
    private final Game game;

    public DinosaurService(DinosaurAssembler dinosaurAssembler, DinosaurFactory dinosaurFactory, Herd herd, Game game) {
        this.dinosaurAssembler = dinosaurAssembler;
        this.dinosaurFactory = dinosaurFactory;
        this.herd = herd;
        this.game = game;
    }

    public void addDinosaur(DinosaurDTO dinosaurDTO) {
        if (herd.hasDinoosaurWithName(dinosaurDTO.name))
            throw new DuplicateNameException();
        Dinosaur dinosaur = dinosaurFactory.create(dinosaurDTO.gender,dinosaurDTO.weight,dinosaurDTO.species,
                dinosaurDTO.name);
        game.addDinosaur(dinosaur);
    }

    public DinosaurDTO showDinosaur(String dinosaurName) {
        Dinosaur dino =  herd.getDinosaurWithName(dinosaurName);
        return dinosaurAssembler.toDTO(dino);
    }

    public List<DinosaurDTO> showAllDinosaurs() {
        List<Dinosaur> dinosaur = herd.getAllDinosaurs();
        return dinosaur.stream()
                .map(dinosaurAssembler::toDTO)
                .collect(Collectors.toList());
    }
}
