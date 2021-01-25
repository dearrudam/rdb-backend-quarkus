package com.temaula.rdb.validation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        var errorMessages = exception.getConstraintViolations().stream().map(constraintViolation -> {
            var names = new LinkedList<String>();
            constraintViolation.getPropertyPath().forEach(node -> names.add(node.getName()));
            String name = names.getLast();
            return Map.of("field", name, "message", constraintViolation.getMessage());
        }).collect(Collectors.toList());
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON_TYPE).entity(errorMessages)
                .build();
    }

    
    public static void main(String[] args) {

        String[][] tabela = new String[][] {};

        tabela = new String[][] { new String[] { "A", "B" }, new String[] { "1", "9" } };

        print(tabela);

        String[] novaColuna = new String [] {"4","6"};
        
        System.out.println("");
        
        for (int i = 0; i < tabela.length ; i++) {
            String[] row = tabela[i];
            String [] newRow = Arrays.copyOf(row, row.length + 1);  
            newRow[ row.length] = novaColuna[i];
            tabela[i] = newRow;
        }

        print(tabela);

    }
 
    public static void print(String[][] data){
        for (int i = 0; i < data.length ; i++) {
            System.out.print("[");
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(String.format("%s ", data[i][j]));
            }
            System.out.println("]");
        }
    }
}