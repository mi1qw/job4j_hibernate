package com.example.hiber.tomany;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.CrudPersist;

import java.util.List;


public class MarkStore implements CRUDStore<Mark> {
    private final CRUDStore<Mark> crud = new CrudPersist<>(Mark.class);

    @Override
    public Mark add(final Mark item) {
        return crud.add(item);
    }

    @Override
    public boolean replace(final Long id, final Mark item) {
        return crud.replace(id, item);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Mark> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Mark> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Mark findById(final Long id) {
        return crud.findById(id);
    }
}
