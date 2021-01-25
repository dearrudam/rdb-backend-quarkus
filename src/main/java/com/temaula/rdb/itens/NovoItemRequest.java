package com.temaula.rdb.itens;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.temaula.rdb.validation.constraints.Unique;

import javax.validation.constraints.NotBlank;

public class NovoItemRequest {

    @NotBlank(message = "não pode estar em branco")
    @Unique(entityType = Item.class,fieldName = "data")
    private final String data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovoItemRequest(
            @NotBlank(message = "não pode estar em branco")
            @Unique(entityType = Item.class,fieldName = "data")
            @JsonProperty("data") String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public Item toModel(){
        return new Item(this.data);
    }

}
