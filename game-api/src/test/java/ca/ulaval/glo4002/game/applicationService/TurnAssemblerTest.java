package ca.ulaval.glo4002.game.applicationService;

import ca.ulaval.glo4002.game.interfaces.rest.game.TurnNumberDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnAssemblerTest {

    private TurnAssembler turnAssembler;

    @BeforeEach
    void setUp() {
        turnAssembler = new TurnAssembler();
    }

    @Test
    void givenATurnNumber_whenAssembleTurnNumber_thenTheAppropriateTurnNumberDTOIsReturned() {
        int aTurnNumber = 132;

        TurnNumberDTO createdDTO = turnAssembler.assembleTurnNumber(aTurnNumber);

        assertEquals(aTurnNumber, createdDTO.turnNumber);
    }
}
