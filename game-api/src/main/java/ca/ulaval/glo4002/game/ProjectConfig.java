package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.*;
import ca.ulaval.glo4002.game.applicationService.Dinosaur.DinosaurAssembler;
import ca.ulaval.glo4002.game.applicationService.Dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.applicationService.Food.FoodAssembler;
import ca.ulaval.glo4002.game.applicationService.Food.FoodSummaryAssembler;
import ca.ulaval.glo4002.game.applicationService.Food.ResourceService;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.Turn;
import ca.ulaval.glo4002.game.domain.dinosaur.babyMaking.BabyFetcher;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.*;
import ca.ulaval.glo4002.game.infrastructure.PantryRepositoryInMemoryImpl;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.HerdRepositoryInMemoryImpl;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.DinosaurBreederExternal;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.DinoBreeder;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BabyFetcherFromExternalAPI;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.BreedingAssembler;
import ca.ulaval.glo4002.game.interfaces.rest.dino.DinosaurResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodValidator;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
import ca.ulaval.glo4002.game.interfaces.rest.heartbeat.HeartbeatResource;
import ca.ulaval.glo4002.game.interfaces.rest.mappers.*;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.ArrayList;

public class ProjectConfig extends ResourceConfig {

    public ProjectConfig() {
        registerResources();
        registerExceptionMappers();
    }

    private void registerResources() {
        PantryRepository pantryRepository = new PantryRepositoryInMemoryImpl();
        HerdRepository herdRepository = new HerdRepositoryInMemoryImpl();

        DinoBreeder dinoBreeder = new DinosaurBreederExternal();
        BabyFetcher dinosaurBabyFetcher = new BabyFetcherFromExternalAPI(dinoBreeder);

        Turn turn = new Turn();
        CookItSubscription cookItSubscription = new CookItSubscription();
        Pantry pantry = pantryRepository.find().
                orElse(new Pantry(cookItSubscription));

        FoodQuantitySummaryCalculator foodQuantitySummaryCalculator = new FoodQuantitySummaryCalculator();
        Herd herd = herdRepository.
                find()
                .orElse(new Herd(new ArrayList<>()));
        Game game = new Game(herd, pantry, turn);
        FoodValidator foodValidator = new FoodValidator();

        DinosaurFactory dinosaurFactory = new DinosaurFactory(pantry,pantry);

        TurnAssembler turnAssembler = new TurnAssembler();
        FoodAssembler foodAssembler = new FoodAssembler();
        DinosaurAssembler dinosaurAssembler = new DinosaurAssembler();
        FoodSummaryAssembler foodSummaryAssembler = new FoodSummaryAssembler();
        BreedingAssembler breedingAssembler = new BreedingAssembler();

        ResourceService resourceService = new ResourceService(foodQuantitySummaryCalculator, pantry, game,
                foodAssembler, foodSummaryAssembler);
        DinosaurService dinosaurService = new DinosaurService(dinosaurAssembler, breedingAssembler, dinosaurFactory, herd, game,
                dinosaurBabyFetcher);
        GameService gameService = new GameService(game, herd, pantry, turnAssembler, pantryRepository, herdRepository);

        HeartbeatResource heartbeatResource = new HeartbeatResource();
        GameResource gameResource = new GameResource(gameService);
        FoodResource foodResource = new FoodResource(resourceService, foodValidator);
        DinosaurResource dinosaurResource = new DinosaurResource(dinosaurService);

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
