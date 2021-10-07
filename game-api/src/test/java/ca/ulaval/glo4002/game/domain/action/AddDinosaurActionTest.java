package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.dinosaur.*;
import ca.ulaval.glo4002.game.domain.dinosaur.consumption.FoodConsumptionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddDinosaurActionTest {
    private final Species A_SPECIES = Species.Allosaurus;
    private final int WEIGHT = 5;
    private final String A_NAME= "DinoA";
    private final Gender A_GENDER = Gender.F;
    private FoodConsumptionStrategy foodConsumptionStrategy;
    private DinosaurRepository dinosaurRepository;
    private Dinosaur dinosaur;
    private Herd herd;
    private AddDinosaurAction addDinosaurAction;

    @BeforeEach
    public void setUp() {
        foodConsumptionStrategy = mock(FoodConsumptionStrategy.class);
        dinosaurRepository = mock(DinosaurRepository.class);
        dinosaur = new Dinosaur(A_SPECIES, WEIGHT, A_NAME, A_GENDER, foodConsumptionStrategy);
        herd = new Herd(dinosaurRepository);
        addDinosaurAction = new AddDinosaurAction(herd, dinosaur);
    }

    @Test
    public void givenADinosaur_WhenExecute_thenDinosaurIsAddedToHerd() {
        when(dinosaurRepository.findAll()).thenReturn(new ArrayList<>());
        addDinosaurAction.execute();
        assertFalse(herd.isEmpty());
    }
}
