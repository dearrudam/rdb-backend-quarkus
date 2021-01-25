package com.temaula.rdb.itens;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemRepository implements PanacheRepositoryBase<Item, String> {
}
