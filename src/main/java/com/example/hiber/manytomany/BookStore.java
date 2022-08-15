package com.example.hiber.manytomany;

import com.example.hiber.persistence.CrudPersist;

public class BookStore extends CrudPersist<Book> {
    public BookStore() {
        super(Book.class);
    }
}
