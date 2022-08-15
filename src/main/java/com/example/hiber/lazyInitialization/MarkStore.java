package com.example.hiber.lazyInitialization;

import com.example.hiber.persistence.CrudPersist;

public class MarkStore extends CrudPersist<Marklz> {

    public MarkStore() {
        super(Marklz.class);
    }
}
