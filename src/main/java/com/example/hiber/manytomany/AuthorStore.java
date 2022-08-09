package com.example.hiber.manytomany;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.CrudPersist;

import java.util.List;


public class AuthorStore implements CRUDStore<Author> {
    private final CRUDStore<Author> crud = new CrudPersist<>(Author.class);

    @Override
    public Author add(final Author item) {
        return crud.add(item);
    }

    @Override
    public boolean replace(final Long id, final Author item) {
        return crud.replace(id, item);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Author> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Author> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Author findById(final Long id) {
        return crud.findById(id);
    }
}
