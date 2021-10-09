package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.*;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.Turn;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurRepository;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurRepositoryInMemoryImpl;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.*;
import ca.ulaval.glo4002.game.infrastructure.PantryRepositoryInMemory;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurRequestsValidator;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodValidator;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import ca.ulaval.glo4002.game.interfaces.rest.mappers.*;
import org.glassfish.jersey.server.ResourceConfig;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
        registerExceptionMappers();
    }

    private void registerResources() {
        PantryRepository pantryRepositoryInMemory = new PantryRepositoryInMemory();

        DinosaurRepository dinosaurRepositoryImplementation = new DinosaurRepositoryInMemoryImpl();
        Turn turn = new Turn();
        Herd herd = new Herd(dinosaurRepositoryImplementation);
        PantryFactory pantryFactory = new PantryFactory();
        Pantry pantry = pantryFactory.create();
        FoodQuantitySummaryCalculator foodQuantitySummaryCalculator = new FoodQuantitySummaryCalculator();
        Game game = new Game(herd, pantry, turn);

        FoodValidator foodValidator = new FoodValidator();
        DinosaurRequestsValidator requestValidator = new DinosaurRequestsValidator(dinosaurRepositoryImplementation);

        TurnAssembler turnAssembler = new TurnAssembler();
        FoodAssembler foodAssembler = new FoodAssembler();
        DinosaurAssembler dinosaurAssembler = new DinosaurAssembler(pantry,pantry);
        FoodSummaryAssembler foodSummaryAssembler = new FoodSummaryAssembler();

        GameService gameService = new GameService(game, herd, pantry, turnAssembler, dinosaurAssembler, foodAssembler,
                foodSummaryAssembler, pantryRepositoryInMemory, foodQuantitySummaryCalculator);

        HeartbeatResource heartbeatResource = new HeartbeatResource();
        GameResource gameResource = new GameResource(gameService);
        FoodResource foodResource = new FoodResource(gameService, foodValidator);
        DinosaurResource dinosaurResource = new DinosaurResource(gameService, requestValidator);

        register(heartbeatResource);
        register(gameResource);
        register(foodResource);
        register(dinosaurResource);
    }

    private void registerExceptionMappers() {
        register(new DuplicateNameExceptionMapper());
        register(new InvalidGenderExceptionMapper());
        register(new InvalidSpeciesExceptionMapper());
        register(new InvalidWeightExceptionMapper());
        register(new NonExistentNameExceptionMapper());
        register(new InvalidRessourceQuantityExceptionMapper());
    }
}
