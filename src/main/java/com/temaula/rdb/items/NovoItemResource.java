package com.temaula.rdb.items;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Map;

@Path("/itens")
public class NovoItemResource {

    @Context
    UriInfo uriInfo;

    @Inject
    ItemRepository itemRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response execute(@Valid NovoItemRequest request){
        Item novoItem = request.toModel();
        itemRepository.persist(novoItem);
        return Response.created(uriInfo.getBaseUriBuilder().path(NovoItemResource.class).path("{data}").resolveTemplate("data",novoItem.getData()).build()).build();
    }

    @GET
    @Path("{data}")
    public Response get(@PathParam("data") String data){
        final var entity = itemRepository.find("data = :data", Map.of("data",data)).firstResultOptional();
        if(entity.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(entity.get()).build();
    }
}
