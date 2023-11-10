package io.zethange.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class BaseException extends WebApplicationException {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public BaseException(Integer status, String message, String description) {
        super(Response.status(status).entity(
                objectMapper.createObjectNode()
                        .put("statusCode", status)
                        .put("message", message)
                        .put("description", description)
                        .toString()
        ).build());
    }
}
