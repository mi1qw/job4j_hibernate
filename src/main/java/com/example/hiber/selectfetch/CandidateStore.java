package com.example.hiber.selectfetch;

import com.example.hiber.persistence.CrudPersist;

public class CandidateStore extends CrudPersist<Candidate> {

    public CandidateStore() {
        super(Candidate.class);
    }
}
