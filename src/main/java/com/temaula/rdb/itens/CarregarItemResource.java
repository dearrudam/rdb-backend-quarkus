package com.temaula.rdb.itens;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/itens")
public class CarregarItemResource {

    @Inject
    ItemRepository itemRepository;

    @GET
    @Path("{data}")
    public Response get(@PathParam("data") String data){
        final var entity = itemRepository.find("data", data).firstResultOptional();
        if(entity.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(entity.get()).build();
    }
}
