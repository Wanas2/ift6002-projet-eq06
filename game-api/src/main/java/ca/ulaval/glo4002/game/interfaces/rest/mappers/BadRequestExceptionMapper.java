package ca.ulaval.glo4002.game.interfaces.rest.mappers;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralBadRequestException;
import ca.ulaval.glo4002.game.interfaces.rest.exceptions.dto.ExceptionDTO;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<GeneralBadRequestException> {

    @Override
    public Response toResponse(GeneralBadRequestException badRequestException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(badRequestException.getError(),
                badRequestException.getDescription());

        return Response.status(400).entity(exceptionDTO).build();
    }
}
