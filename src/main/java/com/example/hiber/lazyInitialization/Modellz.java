package com.example.hiber.lazyInitialization;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "modellz")
public class Modellz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marklz_id")
    private Marklz marklz;

    public Modellz(final String name, final Marklz marklz) {
        this.name = name;
        this.marklz = marklz;
    }
}
