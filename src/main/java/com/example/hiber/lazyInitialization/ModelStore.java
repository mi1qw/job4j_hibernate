package com.example.hiber.lazyInitialization;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.CrudPersist;

import java.util.List;


public class ModelStore implements CRUDStore<Modellz> {
    private final CRUDStore<Modellz> crud = new CrudPersist<>(
            Modellz.class);

    @Override
    public Modellz add(final Modellz item) {
        return crud.add(item);
    }

    @Override
    public boolean replace(final Long id, final Modellz item) {
        return crud.replace(id, item);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Modellz> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Modellz> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Modellz findById(final Long id) {
        return crud.findById(id);
    }
}
