package com.github.temaula.rdb.eventos;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/eventos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class NovoEventoResource {

    @Inject
    EventoRepository repository;

    @POST
    public Response executar(@Valid NovoEventoRequest request) {

        Evento evento = new Evento(request.getNome(),
                request.getDescricao(),
                request.getDataInicio(),
                request.getDataFim());

        repository.persist(evento);

        return Response.ok(new NovoEventoResponse(evento)).build();
    }
}
