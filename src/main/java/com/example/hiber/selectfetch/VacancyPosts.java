package com.example.hiber.selectfetch;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vacancyposts")
public class VacancyPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    private Candidate candidate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public VacancyPosts(final String name, final Candidate candidate) {
        this.name = name;
        this.candidate = candidate;
    }

    public void addVacancy(final Vacancy vacancy) {
        vacancies.add(vacancy);
    }
}
