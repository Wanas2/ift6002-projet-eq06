package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TurnAssemblerTest {

    private static final int A_TURN_NUMBER = 2;
    private TurnAssembler turnAssembler;

    @BeforeEach
    void setUp() {
        turnAssembler = new TurnAssembler();
    }

    @Test
    public void givenATurnNumber_whenAssembleTurnNumber_thenTurnNumberIsAssembled() {
        TurnNumberDTO turnNumberDTO = turnAssembler.assembleTurnNumber(A_TURN_NUMBER);

        assertNotNull(turnNumberDTO);
    }

    // Todo Tester que le DTO est créé avec le bon turnNumber
}
