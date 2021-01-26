package com.github.temaula.rdb.eventos;

import java.time.LocalDate;

public class NovoEventoResponse {

    private final Long id;
    private final String nome;
    private final String descricao;
    private final LocalDate dataInicio;
    private final LocalDate dataFim;

    public NovoEventoResponse(Evento evento) {
        this.id=evento.getId();
        this.nome=evento.getNome();
        this.descricao=evento.getDescricao();
        this.dataInicio=evento.getDataInicio();
        this.dataFim=evento.getDataFim();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }
}
