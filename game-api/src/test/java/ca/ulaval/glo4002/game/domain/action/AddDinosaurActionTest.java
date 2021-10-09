package ca.ulaval.glo4002.game.domain.action;

import ca.ulaval.glo4002.game.domain.dinosaur.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AddDinosaurActionTest {
    private Dinosaur dinosaur;
    private Herd herd;
    private AddDinosaurAction addDinosaurAction;

    @BeforeEach
    public void setUp() {
        dinosaur = mock(Dinosaur.class);
        herd = mock(Herd.class);
        addDinosaurAction = new AddDinosaurAction(herd, dinosaur);
    }

    @Test
    public void givenADinosaur_WhenExecute_thenDinosaurIsAddedToHerd() {
        addDinosaurAction.execute();

        verify(herd).add(dinosaur);
    }
}
