package com.example.hiber.lazyInitialization;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "marklz")
public class Marklz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "marklz", cascade = CascadeType.ALL)
    private List<Modellz> models = new ArrayList<>();

    public Marklz(final String name) {
        this.name = name;
    }

    public void add(final Modellz model) {
        this.models.add(model);
    }
}
