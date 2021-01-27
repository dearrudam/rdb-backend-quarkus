package com.github.temaula.rdb.eventos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Optional;

@IsValid(
        by = Evento.PeriodoValidador.class,
        attributes = {
                NovoEventoRequest.GetDataInicio.class,
                NovoEventoRequest.GetDataFim.class
        },
        message = "data inicial deve ser menor ou igual a data final")
public class NovoEventoRequest {

    @NotEmpty
    private String nome;
    @Length(max = 400)
    private String descricao;
    @NotNull
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public static class GetDataInicio extends Evento.GetDataInicio {
        @Override
        public Optional<LocalDate> getDataInicio(Object source) {
            return Optional.ofNullable(Optional.of(source).map(NovoEventoRequest.class::cast).get().getDataInicio());
        }
    }

    public static class GetDataFim extends Evento.GetDataFim {
        @Override
        public Optional<LocalDate> getDataFim(Object source) {
            return Optional.ofNullable(Optional.of(source).map(NovoEventoRequest.class::cast).get().getDataFim());
        }
    }
}
