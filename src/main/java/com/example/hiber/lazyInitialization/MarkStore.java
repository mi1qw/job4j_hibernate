package com.example.hiber.lazyInitialization;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.CrudPersist;

import java.util.List;


public class MarkStore implements CRUDStore<Marklz> {
    private final CRUDStore<Marklz> crud = new CrudPersist<>(Marklz.class);

    @Override
    public Marklz add(final Marklz item) {
        return crud.add(item);
    }

    @Override
    public boolean replace(final Long id, final Marklz item) {
        return crud.replace(id, item);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Marklz> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Marklz> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Marklz findById(final Long id) {
        return crud.findById(id);
    }
}
