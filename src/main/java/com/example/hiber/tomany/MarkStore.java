package com.example.hiber.tomany;

import com.example.hiber.persistence.CrudPersist;

public class MarkStore extends CrudPersist<Mark> {

    public MarkStore() {
        super(Mark.class);
    }
}
