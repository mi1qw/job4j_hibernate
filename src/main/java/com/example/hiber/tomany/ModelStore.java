package com.example.hiber.tomany;

import com.example.hiber.persistence.CrudPersist;

public class ModelStore extends CrudPersist<Model> {

    public ModelStore() {
        super(Model.class);
    }
}
