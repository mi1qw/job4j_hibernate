package com.example.hiber.manytomany;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.CrudPersist;

import java.util.List;


public class BookStore implements CRUDStore<Book> {
    private final CRUDStore<Book> crud = new CrudPersist<>(Book.class);

    @Override
    public Book add(final Book item) {
        return crud.add(item);
    }

    @Override
    public boolean replace(final Long id, final Book item) {
        return crud.replace(id, item);
    }

    @Override
    public boolean delete(final Long id) {
        return crud.delete(id);
    }

    @Override
    public List<Book> findAll() {
        return crud.findAll();
    }

    @Override
    public List<Book> findByName(final String name) {
        return crud.findByName(name);
    }

    @Override
    public Book findById(final Long id) {
        return crud.findById(id);
    }
}
