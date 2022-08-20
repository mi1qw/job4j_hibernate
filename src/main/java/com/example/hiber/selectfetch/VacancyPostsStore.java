package com.example.hiber.selectfetch;

import com.example.hiber.persistence.CrudPersist;

public class VacancyPostsStore extends CrudPersist<VacancyPosts> {

    public VacancyPostsStore() {
        super(VacancyPosts.class);
    }
}
