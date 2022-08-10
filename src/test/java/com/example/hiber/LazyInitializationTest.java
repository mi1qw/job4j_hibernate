package com.example.hiber;

import com.example.hiber.lazyInitialization.MarkStore;
import com.example.hiber.lazyInitialization.Marklz;
import com.example.hiber.lazyInitialization.Modellz;
import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.SessionStore;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class LazyInitializationTest implements SessionStore {
    private static final CRUDStore<Marklz> markStore = new MarkStore();

    @BeforeEach
    void serUp() {
        Marklz mercedesBenz = new Marklz("Mercedes-Benz");
        Modellz maybach = new Modellz("S-Class Maybach", mercedesBenz);
        Modellz eClass = new Modellz("E-Class", mercedesBenz);
        Modellz amgSL = new Modellz("AMG SL", mercedesBenz);
        Modellz eqsSUV = new Modellz("EQS SUV", mercedesBenz);
        Modellz tClass = new Modellz("T-Class", mercedesBenz);
        mercedesBenz.add(maybach);
        mercedesBenz.add(eClass);
        mercedesBenz.add(amgSL);
        mercedesBenz.add(eqsSUV);
        mercedesBenz.add(tClass);
        markStore.add(mercedesBenz);
    }

    @Test
    void whenGetCollectionInLazyShouldThrowLazyInitializationException() {
        List<Marklz> byName = markStore.findByName("Mercedes-Benz");
        List<Modellz> models = byName.get(0).getModels();
        assertThatThrownBy(models::size).isInstanceOf(LazyInitializationException.class);
    }

    @Test
    void whenGetCollectionInLazyInSessionShouldReturnCollection() {
        Long id = markStore.findByName("Mercedes-Benz").get(0).getId();
        Marklz markDB = tx(session -> {
            Marklz marklz = session.createQuery("from Marklz where id=:id", Marklz.class)
                    .setParameter("id", id)
                    .getSingleResult();

            marklz.getModels().forEach(n -> log.info("{}", n.getName()));
            return marklz;
        });
        assertThat(markDB.getModels())
                .hasSize(5)
                .extracting("name", String.class)
                .containsExactlyInAnyOrder(
                        "S-Class Maybach",
                        "E-Class",
                        "AMG SL",
                        "EQS SUV",
                        "T-Class"
                );
    }

    @Test
    void whenGetCollectionInLazyWithJoinShouldReturnCollection() {
        Long id = markStore.findByName("Mercedes-Benz").get(0).getId();
        Marklz markDB = tx(session ->
                session.createQuery(
                                "select distinct m from Marklz m join fetch m.models "
                                + "where m.id=:id",
                                Marklz.class)
                        .setParameter("id", id)
                        .getSingleResult()
        );
        assertThat(markDB.getModels())
                .hasSize(5)
                .extracting("name", String.class)
                .containsExactlyInAnyOrder(
                        "S-Class Maybach",
                        "E-Class",
                        "AMG SL",
                        "EQS SUV",
                        "T-Class"
                );
    }
}
