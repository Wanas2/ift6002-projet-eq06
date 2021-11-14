package ca.ulaval.glo4002.game;

import ca.ulaval.glo4002.game.applicationService.*;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurAssembler;
import ca.ulaval.glo4002.game.applicationService.dinosaur.DinosaurService;
import ca.ulaval.glo4002.game.applicationService.food.FoodAssembler;
import ca.ulaval.glo4002.game.applicationService.food.FoodSummaryAssembler;
import ca.ulaval.glo4002.game.applicationService.food.ResourceService;
import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.Turn;
import ca.ulaval.glo4002.game.domain.dinosaur.BabyFetcher;
import ca.ulaval.glo4002.game.domain.dinosaur.DinosaurFactory;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.food.*;
import ca.ulaval.glo4002.game.infrastructure.PantryRepositoryInMemoryImpl;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.HerdRepositoryInMemoryImpl;
import ca.ulaval.glo4002.game.infrastructure.dinosaur.dinosaurBreederExternal.*;
import ca.ulaval.glo4002.game.interfaces.rest.dinosaur.DinosaurResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodResource;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodValidator;
import ca.ulaval.glo4002.game.interfaces.rest.game.GameResource;
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

        DinosaurBreederExternal dinoBreeder = new DinosaurBreederExternal();
        ParentsGenderValidator parentsGenderValidator = new ParentsGenderValidator();
        BabyFetcher dinosaurBabyFetcher
                = new BabyFetcherFromExternalAPI(dinoBreeder, dinosaurFactory, parentsGenderValidator);

        TurnAssembler turnAssembler = new TurnAssembler();
        FoodAssembler foodAssembler = new FoodAssembler();
        DinosaurAssembler dinosaurAssembler = new DinosaurAssembler();
        FoodSummaryAssembler foodSummaryAssembler = new FoodSummaryAssembler();

        ResourceService resourceService = new ResourceService(foodQuantitySummaryCalculator, pantry, game);
        DinosaurService dinosaurService = new DinosaurService(dinosaurFactory, herd, game, dinosaurBabyFetcher);
        GameService gameService = new GameService(game, herd, pantry, pantryRepository, herdRepository);

        GameResource gameResource = new GameResource(gameService, turnAssembler);
        FoodResource foodResource = new FoodResource(resourceService, foodValidator, foodAssembler,
                foodSummaryAssembler);
        DinosaurResource dinosaurResource = new DinosaurResource(dinosaurService, dinosaurAssembler);

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
        register(new InvalidResourceQuantityExceptionMapper());
        register(new InvalidFatherExceptionMapper());
        register(new InvalidMotherExceptionMapper());
    }
}
