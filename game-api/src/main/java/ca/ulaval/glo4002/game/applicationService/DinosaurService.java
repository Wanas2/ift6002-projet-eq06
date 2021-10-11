package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.exceptions.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BabyDinoResponseDTO;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BreedingAssembler;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BreedingRequestExternalDTO;
import ca.ulaval.glo4002.game.interfaces.rest.dino.BreedingRequestDTO;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DinosaurService {

    private final DinosaurAssembler dinosaurAssembler;
    private final BreedingAssembler breedingAssembler;
    private final DinosaurFactory dinosaurFactory;
    private final Herd herd;
    private final Game game;
    private final Breeder breeder;

    public DinosaurService(DinosaurAssembler dinosaurAssembler, BreedingAssembler breedingAssembler,
                           DinosaurFactory dinosaurFactory, Herd herd, Game game, Breeder breeder) {
        this.dinosaurAssembler = dinosaurAssembler;
        this.breedingAssembler = breedingAssembler;
        this.dinosaurFactory = dinosaurFactory;
        this.herd = herd;
        this.game = game;
        this.breeder = breeder;
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

    public void breedDino(BreedingRequestDTO breedingRequestDTO) {
        Dinosaur fatherDinosaur = herd.getDinosaurWithName(breedingRequestDTO.fatherName);
        Dinosaur motherDinosaur = herd.getDinosaurWithName(breedingRequestDTO.motherName);

        BreedingRequestExternalDTO breedingRequestExternalDTO = breedingAssembler
                .createExternalAPIDTO(fatherDinosaur, motherDinosaur);

        BabyDinoResponseDTO babyDinoResponseDTO = breeder.breed(breedingRequestExternalDTO);

        // Todo Créer le babyDinosaure et l'ajouter aux actions en attente
    }
}
