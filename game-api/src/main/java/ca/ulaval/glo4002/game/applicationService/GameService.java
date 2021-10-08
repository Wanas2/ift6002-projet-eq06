package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.applicationService.exceptions.DuplicateNameException;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurDTO;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodSummaryDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameService {

    private final TurnAssembler turnAssembler;
    private final DinosaurAssembler dinosaurAssembler;
    private final Game game;
    private final Pantry pantry;
    private final Herd herd;
    private final FoodAssembler foodAssembler;
    private final FoodSummaryAssembler foodSummaryAssembler;
    private final DinosaurFactory dinosaurFactory;
    private final HerdRepository herdRepository;

    public GameService(Game game, Herd herd, Pantry pantry, TurnAssembler turnAssembler, DinosaurAssembler dinosaurAssembler, FoodAssembler foodAssembler,
                       FoodSummaryAssembler foodSummaryAssembler, DinosaurFactory dinosaurFactory, HerdRepository herdRepository) {
        this.game = game;
        this.herd = herd;
        this.pantry = pantry;
        this.turnAssembler = turnAssembler;
        this.dinosaurAssembler = dinosaurAssembler;
        this.foodAssembler = foodAssembler;
        this.foodSummaryAssembler = foodSummaryAssembler;
        this.dinosaurFactory = dinosaurFactory;
        this.herdRepository = herdRepository;
    }
    //TODO : Utilisation du repository food pour pantry
    public void orderFood(FoodDTO foodDTO) {
        Map<FoodType, Food> food = foodAssembler.create(foodDTO);
        game.addFood(food);
    }

    public TurnNumberDTO playTurn() {
        int turnNumber = game.playTurn();
        herdRepository.save(herd);
        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public void addDinosaur(DinosaurDTO dinosaurDTO) {
        if (herd.existsByName(dinosaurDTO.name))
            throw new DuplicateNameException();
        Dinosaur dinosaur = dinosaurFactory.create(dinosaurDTO.gender,dinosaurDTO.weight,dinosaurDTO.name,
                dinosaurDTO.name);
        game.addDinosaur(dinosaur);
    }

    public DinosaurDTO showDinosaur(String dinosaurName){
        Dinosaur dino =  herd.find(dinosaurName);
        return dinosaurAssembler.toDTO(dino);
    }

    public List<DinosaurDTO> showAllDinosaurs(){
        List<Dinosaur> dinos = herd.findAll();
        return dinos.stream()
                .map(dinosaurAssembler::toDTO)
                .collect(Collectors.toList());
    }

    public FoodSummaryDTO getFoodQuantitySummary() {
        Map<String, Map<FoodType, Integer>> allFoodSummary = pantry.getFoodQuantitySummary();
        return foodSummaryAssembler.createDTO(allFoodSummary, foodAssembler);
    }

    public void reset() {
        game.reset();
        herdRepository.delete();
    }
}
