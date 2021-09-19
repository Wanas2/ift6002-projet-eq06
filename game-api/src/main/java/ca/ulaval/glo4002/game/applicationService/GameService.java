package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.parkResources.Food;
import ca.ulaval.glo4002.game.domain.parkResources.FoodsFactory;
import ca.ulaval.glo4002.game.domain.parkResources.Pantry;
import ca.ulaval.glo4002.game.domain.parkResources.PantryRepository;
import ca.ulaval.glo4002.game.interfaces.rest.game.FoodsDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;

import java.util.List;

public class GameService {

    private final TurnAssembler turnAssembler;
    private final FoodsFactory foodsFactory;
    private final PantryRepository pantryRepository;
    private final Game game;
    private final Pantry pantry;

    public GameService(TurnAssembler turnAssembler, FoodsFactory foodsFactory, PantryRepository pantryRepository,
                       Game game, Pantry pantry) {
        this.turnAssembler = turnAssembler;
        this.foodsFactory = foodsFactory;
        this.pantryRepository = pantryRepository;
        this.game = game;
        this.pantry = pantry;
    }

    public TurnNumberDTO playTurn() {
        int turnNumber = game.playTurn();

        // Todo Get this DTO from a resource object that returns a DTO
        FoodsDTO  foodsDTO = new FoodsDTO(100, 250, 10);

        List<Food> foods = foodsFactory.create(foodsDTO.qtyBurger, foodsDTO.qtySalad, foodsDTO.qtyWater);
        pantry.addFood(foods);
        pantryRepository.save(foods);

        return turnAssembler.assembleTurnNumber(turnNumber);
    }

    public void reset() {
        game.reset();
    }
}
