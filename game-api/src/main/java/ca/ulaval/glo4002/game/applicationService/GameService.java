package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.Food;
import ca.ulaval.glo4002.game.domain.food.FoodType;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.food.PantryRepository;
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
    private final PantryRepository pantryRepository;

    public GameService(Game game, Herd herd, Pantry pantry, TurnAssembler turnAssembler,
                       DinosaurAssembler dinosaurAssembler, FoodAssembler foodAssembler,
                       FoodSummaryAssembler foodSummaryAssembler, PantryRepository pantryRepository) {
        this.game = game;
        this.herd = herd;
        this.pantry = pantry;
        this.turnAssembler = turnAssembler;
        this.dinosaurAssembler = dinosaurAssembler;
        this.foodAssembler = foodAssembler;
        this.foodSummaryAssembler = foodSummaryAssembler;
        this.pantryRepository = pantryRepository;
    }
    //TODO : Utilisation du repository food/dino pour pantry/herd
    public void addFood(FoodDTO foodDTO) {
        Map<FoodType, Food> food = foodAssembler.create(foodDTO);
        game.addFood(food);
    }

    public void addDinosaur(DinosaurDTO dinosaurDTO) {
        Dinosaur dinosaur = dinosaurAssembler.create(dinosaurDTO);
        game.addDinosaur(dinosaur);
    }

    public TurnNumberDTO playTurn() {
        int turnNumber = game.playTurn();
        pantryRepository.update(pantry);
        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public DinosaurDTO showDinosaur(String dinosaurName){
        Dinosaur dino =  herd.find(dinosaurName);
        return dinosaurAssembler.format(dino);
    }

    public List<DinosaurDTO> showAllDinosaurs(){
        List<Dinosaur> dinos = herd.findAll();
        return dinos.stream()
                .map(dinosaurAssembler::format)
                .collect(Collectors.toList());
    }

    public FoodSummaryDTO getFoodQuantitySummary() {
        Pantry pantry = pantryRepository.getPantry();

//        return foodSummaryAssembler.createDTO(allFoodSummary, foodAssembler);
        return null;
    }

    public void reset() {
        game.reset();
    }
}
