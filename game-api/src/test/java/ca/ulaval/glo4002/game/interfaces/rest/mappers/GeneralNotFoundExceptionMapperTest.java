package ca.ulaval.glo4002.game.interfaces.rest.mappers;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralNotFoundExceptionMapperTest {

    private final static int NOT_FOUND_STATUS = 404;
    private final static String AN_ERROR_CODE = "ERROR";
    private final static String A_MESSAGE = "Erreur";

    @Test
    public void whenNotFoundResponse_thenShouldBeStatus404() {
        GeneralNotFoundExceptionMapper mapper = new GeneralNotFoundExceptionMapper();

        Response response = mapper.notFoundResponse(AN_ERROR_CODE, A_MESSAGE);

        assertEquals(NOT_FOUND_STATUS, response.getStatus());
    }
}
