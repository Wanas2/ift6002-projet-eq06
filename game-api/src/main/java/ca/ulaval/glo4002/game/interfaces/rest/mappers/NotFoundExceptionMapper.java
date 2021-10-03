package ca.ulaval.glo4002.game.interfaces.rest.mappers;

import ca.ulaval.glo4002.game.interfaces.rest.exceptions.GeneralNotFoundException;
import ca.ulaval.glo4002.game.interfaces.rest.exceptions.dto.ExceptionDTO;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<GeneralNotFoundException> {

    @Override
    public Response toResponse(GeneralNotFoundException notFoundException) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(notFoundException.getError(),
                notFoundException.getDescription());
        return Response.status(404).entity(exceptionDTO).build();
    }
}
