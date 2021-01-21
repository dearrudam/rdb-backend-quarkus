package com.temaula.rdb.validation;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        List<Map> errorMessages = constraintViolations.stream()
                .map(constraintViolation -> {
                    final LinkedList<String> names = new LinkedList<>();
                    constraintViolation.getPropertyPath().forEach(node -> names.add(node.getName()));
                    String name = names.getLast();
                    return Map.of("field",name ,"message", constraintViolation.getMessage());
                })
                .collect(Collectors.toList());
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(errorMessages)
                .build();
    }
}