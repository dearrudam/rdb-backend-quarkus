package com.temaula.rdb.items;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
public class Item implements Serializable {

    @Id
    @NotEmpty(message = "data não pode estar em branco")
    private String data;

    @Deprecated
    public Item() {
    }

    public Item(
            @NotEmpty(message = "data não pode estar em branco") String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
