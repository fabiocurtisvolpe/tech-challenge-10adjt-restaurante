package com.adjt.rest.exception;

import com.adjt.core.exception.NotificacaoException;
import io.quarkus.security.ForbiddenException;
import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.Priority;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manipulador global de exceções para Quarkus.
 */
@Provider
@Priority(Priorities.USER)
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOG = Logger.getLogger(GlobalExceptionHandler.class);

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(Throwable exception) {
        LOG.error("Exceção capturada: " + exception.getMessage(), exception);

        return switch (exception) {
            case NotificacaoException _ ->
                    createResponse(Response.Status.BAD_REQUEST, "Bad Request", exception.getMessage());
            case IllegalArgumentException _ ->
                    createResponse(Response.Status.BAD_REQUEST, "Invalid Argument", exception.getMessage());
            case ConstraintViolationException constraintViolationException ->
                    handleConstraintViolation(constraintViolationException);
            case UnauthorizedException _ ->
                    createResponse(Response.Status.UNAUTHORIZED, "Unauthorized", "Credenciais inválidas");
            case ForbiddenException _ ->
                    createResponse(Response.Status.FORBIDDEN, "Forbidden", "Acesso negado");
            default -> createResponse(Response.Status.INTERNAL_SERVER_ERROR, "Internal Server Error",
                    "An unexpected error occurred: " + exception.getMessage());
        };

    }

    private Response createResponse(Response.Status status, String error, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.getStatusCode());
        body.put("error", error);
        body.put("message", message);
        body.put("path", uriInfo.getPath());

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(body)
                .build();
    }

    private Response handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> {
                            String path = violation.getPropertyPath().toString();
                            return path.substring(path.lastIndexOf('.') + 1); // Pega apenas o nome do campo
                        },
                        ConstraintViolation::getMessage,
                        (existing, replacement) -> existing));

        ErrorResponse errorResponse = new ErrorResponse(
                "Erro de validação",
                "Os dados fornecidos são inválidos",
                Response.Status.BAD_REQUEST.getStatusCode(),
                errors);

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(errorResponse)
                .build();
    }

    public record ErrorResponse(
            String title,
            String detail,
            int status,
            Map<String, String> errors) {
    }
}
