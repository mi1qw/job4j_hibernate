package com.example.hiber;

import com.example.hiber.manytomany.Author;
import com.example.hiber.manytomany.AuthorStore;
import com.example.hiber.manytomany.Book;
import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.PersistConfig;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


public class AuthorBooksTest {
    private static final CRUDStore<Author> authorStore = new AuthorStore();
    private static final Function<Function<Session, Object>, Object> tx
            = PersistConfig.INST.sessionTx();

    @Test
    void manyToMany5MarksInModels() {
        tx.apply(session -> {
                    Book aBook = new Book("A книга");
                    session.persist(aBook);
                    Book bBook = new Book("B книга");
                    session.persist(bBook);

                    Author ann = new Author("Аня");
                    ann.add(aBook);
                    ann.add(bBook);

                    Author vasya = new Author("Вася");
                    vasya.add(aBook);
                    vasya.add(bBook);
                    session.persist(ann);
                    session.persist(vasya);
                    return null;
                }
        );

        Author ann = authorStore.findByName("Аня").get(0);
        tx.apply(session -> {
                    session.remove(ann);
                    return null;
                }
        );
        assertThat(authorStore.findByName("Вася"))
                .hasSize(1)
                .first()
                .extracting("name", as(InstanceOfAssertFactories.STRING))
                .isEqualTo("Вася");
    }
}
