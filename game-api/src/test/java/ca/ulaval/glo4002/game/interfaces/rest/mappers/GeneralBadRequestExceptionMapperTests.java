package ca.ulaval.glo4002.game.interfaces.rest.mappers;

import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeneralBadRequestExceptionMapperTests {
    private final static int BAD_REQUEST_STATUS = 400;
    private final static String AN_ERROR_CODE = "ERROR";
    private final static String A_MESSAGE = "Erreur";
    @Test
    public void whenBadRequstResponse_thenShouldBeStatus400(){
        GeneralBadRequestExceptionMapper mapper = new GeneralBadRequestExceptionMapper();

        Response response = mapper.badRequestResponse(AN_ERROR_CODE,A_MESSAGE);

        assertEquals(BAD_REQUEST_STATUS,response.getStatus());
    }
}
