package com.example.hiber;

import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.SessionStore;
import com.example.hiber.selectfetch.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class VacancyPostsTest implements SessionStore {
    private static final CRUDStore<Vacancy> vacancyStore = new VacancyStore();
    private static final CRUDStore<Candidate> candidateStore = new CandidateStore();
    private static final CRUDStore<VacancyPosts> vacancyPostsStore = new VacancyPostsStore();

    @Test
    void getId() {
        Candidate candidate = candidateStore.add(
                new Candidate("vasya", "email", "password"));
        Vacancy vacancy = new Vacancy("Work for Junior", "description", LocalDateTime.now());
        VacancyPosts posts = new VacancyPosts("VasyaPosts", candidate);
        posts.addVacancy(vacancy);
        VacancyPosts vacancyPosts = vacancyPostsStore.add(posts);

        posts = tx(session ->
                session.createQuery("from VacancyPosts vp "
                                    + "join fetch vp.candidate c "
                                    + "join fetch vp.vacancies "
                                    + "where vp.id=:id", VacancyPosts.class)
                        .setParameter("id", vacancyPosts.getId())
                        .uniqueResult()
        );
        assertThat(posts)
                .satisfies(p -> assertThat(p.getVacancies().get(0).getName())
                        .isEqualTo("Work for Junior"))
                .satisfies(p -> assertThat(p.getCandidate().getName())
                        .isEqualTo("vasya"));
    }
}
