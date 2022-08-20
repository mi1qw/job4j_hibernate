package com.example.hiber.selectfetch;

import com.example.hiber.persistence.CrudPersist;

public class VacancyStore extends CrudPersist<Vacancy> {

    public VacancyStore() {
        super(Vacancy.class);
    }
}
