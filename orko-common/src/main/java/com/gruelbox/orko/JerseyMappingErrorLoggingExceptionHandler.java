package com.gruelbox.orko;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * Implementation if {@link ExceptionMapper} to send down a "400 Bad Request"
 * response in the event that unmappable JSON is received. <p> Note that
 * {@link javax.ws.rs.ext.Provider} annotation was include up to Jackson 2.7,
 * but removed from 2.8 (as per [jaxrs-providers#22]
 *
 * @since 2.2
 */
public class JerseyMappingErrorLoggingExceptionHandler implements ExceptionMapper<JsonMappingException> {

  private static final Logger LOGGER = LoggerFactory.getLogger(JerseyMappingErrorLoggingExceptionHandler.class);

  @Override
  public Response toResponse(JsonMappingException exception) {
    LOGGER.error("JSON mapping error at " + exception.getPath(), exception);
    return Response.status(Response.Status.BAD_REQUEST).entity(exception.getMessage()).type("text/plain").build();
  }
}