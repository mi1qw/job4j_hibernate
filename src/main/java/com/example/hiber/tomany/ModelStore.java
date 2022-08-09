package com.example.hiber.tomany;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.CrudPersist;

import java.util.List;


public class ModelStore implements CRUDStore<Model> {
    private final CRUDStore<Model> crud = new CrudPersist<>(Model.class);

    @Override
    public Model add(final Model item) {
        return crud.add(item);
    }

    @Override
    public boolean replace(final Long id, final Model item) {
        return crud.replace(id, item);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Model> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Model> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Model findById(final Long id) {
        return crud.findById(id);
    }
}
