package com.example.hiber.lazyInitialization;

import com.example.hiber.persistence.CrudPersist;

public class ModelStore extends CrudPersist<Modellz> {

    public ModelStore() {
        super(Modellz.class);
    }
}
