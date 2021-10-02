package ca.ulaval.glo4002.game.domain;

import static org.junit.jupiter.api.Assertions.*;

import ca.ulaval.glo4002.game.domain.action.AddDinosaurAction;
import ca.ulaval.glo4002.game.domain.action.ExecutableAction;
import ca.ulaval.glo4002.game.domain.dinosaur.Dinosaur;
import ca.ulaval.glo4002.game.domain.dinosaur.Gender;
import ca.ulaval.glo4002.game.domain.dinosaur.Herd;
import ca.ulaval.glo4002.game.domain.dinosaur.Species;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.*;

class GameTest {

    private Turn turn;
    private Herd herd;
    private Game game;

    @BeforeEach
    void setUp() {
        turn = mock(Turn.class);
        herd = mock(Herd.class);
        game = new Game(turn, herd);
    }

    @Test
    public void whenPlayTurn_thenTurnIsPlayed() {
        game.playTurn();

        verify(turn).play();
    }

    @Test
    public void whenPlayTurn_thenShouldReturnTheTurnNumber() {
        int expectedTurnNumber = 12;
        willReturn(expectedTurnNumber).given(turn).play();

        int turnNumber = game.playTurn();

        assertSame(expectedTurnNumber, turnNumber);
    }

    @Test
    public void whenReset_thenTurnIsReset() {
        game.reset();

        verify(turn).reset();
    }

    @Test
    public void givenNewDinosaur_whenAddDinosaur_thenDinosaurIsAddedToList() {
        Dinosaur dinosaur = new Dinosaur(Species.Ankylosaurus, 100, "bob", Gender.M, null);
        ExecutableAction addDinosaurAction = new AddDinosaurAction(herd, dinosaur);
        game.addDinosaur(dinosaur);
        verify(turn).acquireNewAction(addDinosaurAction);
    }

}
