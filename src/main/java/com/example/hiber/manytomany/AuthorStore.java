package com.example.hiber.manytomany;

import com.example.hiber.persistence.CrudPersist;

public class AuthorStore extends CrudPersist<Author> {

    public AuthorStore() {
        super(Author.class);
    }
}
