package ca.ulaval.glo4002.game.applicationService.Dinosaur;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.babyMaking.BabyFetcher;
import ca.ulaval.glo4002.game.domain.dinosaur.babyMaking.DinosaurBreedingCouple;
import ca.ulaval.glo4002.game.domain.dinosaur.babyMaking.DinosaurBreedingCoupleFactory;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BreedingAssembler;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.SpeciesWillNotBreedException;
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
    private final BabyFetcher babyFetcher;

    public DinosaurService(DinosaurAssembler dinosaurAssembler, BreedingAssembler breedingAssembler,
                           DinosaurFactory dinosaurFactory, Herd herd, Game game, BabyFetcher babyFetcher) {
        this.dinosaurAssembler = dinosaurAssembler;
        this.breedingAssembler = breedingAssembler;
        this.dinosaurFactory = dinosaurFactory;
        this.herd = herd;
        this.game = game;
        this.babyFetcher = babyFetcher;
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
        DinosaurBreedingCoupleFactory dinosaurBreedingCoupleFactory =
                new DinosaurBreedingCoupleFactory(herd);
        DinosaurBreedingCouple dinosaurBreedingCouple =
                dinosaurBreedingCoupleFactory.create(breedingRequestDTO.fatherName, breedingRequestDTO.motherName);

        Dinosaur fatherDinosaur = dinosaurBreedingCouple.getFatherDinosaur();
        Dinosaur motherDinosaur = dinosaurBreedingCouple.getMotherDinosaur();
        try {
            babyFetcher.fetch(fatherDinosaur, motherDinosaur); // Todo Retourner un bébé dinosaure, pas un DTO
        } catch (SpeciesWillNotBreedException ignored) {
        }

        dinosaurBreedingCouple.breed(); // Todo Prendre un bébé dinosaure en entré

        // Todo Ajouter à la liste d'actions en attente
    }
}
