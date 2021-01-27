package com.github.temaula.rdb.eventos;

import javax.ejb.Local;
import javax.management.Attribute;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    /**
     * Não utilizar.
     */
    @Deprecated
    public Evento() {
    }

    public Evento(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
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

    public static interface DataInicio extends IsValid.Attribute {
        @Override
        default Optional<?> getValue(Object source) {
            return getDataInicio(source);
        }

        Optional<LocalDate> getDataInicio(Object source);
    }

    public static interface DataFim extends IsValid.Attribute {
        @Override
        default Optional<?> getValue(Object source) {
            return getDataFim(source);
        }
        Optional<LocalDate> getDataFim(Object source);
    }

    public static class PeriodoValidador implements IsValid.CustomValidator {
        @Override
        public boolean isValid(Object source, IsValid.Attribute[] attributes) {
            if(Objects.isNull(source))
                return false;
            if(Objects.isNull(attributes))
                return false;

            final var supportedAttributes = new HashMap<Object, Optional<IsValid.Attribute>>();

            Arrays.stream(attributes)
                    .filter(Objects::nonNull)
                    .forEach(a -> {
                        if (DataInicio.class.isAssignableFrom(a.getClass())) {
                            supportedAttributes.put(DataInicio.class, Optional.of(a));
                        } else if (DataFim.class.isAssignableFrom(a.getClass())) {
                            supportedAttributes.put(DataFim.class, Optional.of(a));
                        }
                    });

            if (!supportedAttributes.containsKey(DataInicio.class))
                return false;

            final var dataInicio = supportedAttributes
                    .get(DataInicio.class)
                    .map(DataInicio.class::cast).get().getDataInicio(source);

            if (dataInicio.isEmpty())
                return false;

            if (!supportedAttributes.containsKey(DataFim.class))
                return true;

            final var dataFim = supportedAttributes
                    .get(DataFim.class)
                    .map(DataFim.class::cast).get().getDataFim(source);

            if (dataFim.isEmpty())
                return true;

            return Period.between(dataInicio.get(), dataFim.get()).getDays() >= 0;

        }
    }
}