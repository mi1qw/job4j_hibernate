package com.example.hiber;

import com.example.hiber.manytomany.Author;
import com.example.hiber.manytomany.AuthorStore;
import com.example.hiber.manytomany.Book;
import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.SessionStore;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


public class AuthorBooksTest implements SessionStore {
    private static final CRUDStore<Author> authorStore = new AuthorStore();

    @Test
    void manyToMany5MarksInModels() {
        txv(session -> {
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
                }
        );

        Author ann = authorStore.findByName("Аня").get(0);
        txv(session -> session.remove(ann));
        assertThat(authorStore.findByName("Вася"))
                .hasSize(1)
                .first()
                .extracting("name", as(InstanceOfAssertFactories.STRING))
                .isEqualTo("Вася");
    }
}
