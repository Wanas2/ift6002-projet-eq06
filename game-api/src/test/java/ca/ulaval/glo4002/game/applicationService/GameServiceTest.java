package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.domain.Game;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.dinosaur.HerdRepository;
import ca.ulaval.glo4002.game.domain.food.Pantry;
import ca.ulaval.glo4002.game.domain.food.PantryRepository;
import ca.ulaval.glo4002.game.interfaces.rest.food.FoodDTO;
import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GameServiceTest {

    private final static int A_QUANTITY_OF_BURGER_ORDERED = 100;
    private final static int A_QUANTITY_OF_SALAD_ORDERED = 250;
    private final static int A_QUANTITY_OF_WATER_IN_LITERS_ORDERED = 10;

    private FoodDTO aFoodDTO;
    private Game game;
    private Herd herd;
    private Pantry pantry;
    private TurnAssembler turnAssembler;
    private PantryRepository pantryRepository;
    private HerdRepository herdRepository;
    private GameService gameService;

    @BeforeEach
    void setUp() {
        initializeAFoodDTO();
        game = mock(Game.class);
        herd = mock(Herd.class);
        pantry = mock(Pantry.class);
        turnAssembler = new TurnAssembler();
        pantryRepository = mock(PantryRepository.class);
        herdRepository = mock(HerdRepository.class);
        gameService = new GameService(game, herd, pantry, turnAssembler, pantryRepository, herdRepository);
    }

    @Test
    public void givenFoods_whenPlayTurn_thenGameIsPlayed() {
        gameService.playTurn();

        verify(game).playTurn();
    }

    @Test
    public void whenPlayTurn_thenPantryIsUpdated() {
        gameService.playTurn();

        verify(pantryRepository).save(pantry);
    }

    @Test
    public void whenPlayTurn_thenHerdIsSaved() {
        gameService.playTurn();

        verify(herdRepository).save(herd);
    }

    @Test
    public void givenATurnNumber_whenPlayTurn_thenTheAppropriateTurnNumberDTOIsCreated() {
        int aTurnNumber = 143;
        when(game.playTurn()).thenReturn(aTurnNumber);

        gameService.playTurn();
        TurnNumberDTO turnNumberDTO = turnAssembler.assembleTurnNumber(aTurnNumber);

        assertEquals(aTurnNumber, turnNumberDTO.turnNumber);
    }

    @Test
    public void whenReset_thenGameIsReset() {
        gameService.reset();

        verify(game).reset();
    }

    @Test
    public void whenReset_thenHerdIsDeleted() {
        gameService.reset();

        verify(herdRepository).delete();
    }

    @Test
    public void whenReset_thenPantryIsIsDeleted() {
        gameService.reset();

        verify(pantryRepository).delete();
    }

    private void initializeAFoodDTO() {
        aFoodDTO = new FoodDTO();
        aFoodDTO.qtyBurger = A_QUANTITY_OF_BURGER_ORDERED;
        aFoodDTO.qtySalad = A_QUANTITY_OF_SALAD_ORDERED;
        aFoodDTO.qtyWater = A_QUANTITY_OF_WATER_IN_LITERS_ORDERED;
    }
}
