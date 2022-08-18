package com.example.hiber;

import com.example.hiber.manytomany.Author;
import com.example.hiber.manytomany.AuthorStore;
import com.example.hiber.manytomany.Book;
import com.example.hiber.manytomany.BookStore;
import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.SessionStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthorBooksTest implements SessionStore {
    private static final CRUDStore<Author> authorStore = new AuthorStore();
    private static final CRUDStore<Book> bookStore = new BookStore();

    @BeforeEach
    void setUp() {
        Book aBook = new Book("A книга");
        Book bBook = new Book("B книга");
        bookStore.add(aBook);
        bookStore.add(bBook);
        Author ann = new Author("Аня");
        ann.add(aBook);
        ann.add(bBook);
        Author vasya = new Author("Вася");
        vasya.add(aBook);
        vasya.add(bBook);
        authorStore.add(ann);
        authorStore.add(vasya);
    }

    @AfterEach
    void clear() {
        authorStore.findAll().forEach(n -> authorStore.delete(n.getId()));
        bookStore.findAll().forEach(n -> bookStore.delete(n.getId()));
        assertThat(authorStore.findAll()).isEmpty();
        assertThat(bookStore.findAll()).isEmpty();
    }

    @Test
    void addPersistedEntityWithCascadeTypeMERGE() {
        assertThat(bookStore.findAll())
                .hasSize(2)
                .extracting("name", String.class)
                .containsExactlyInAnyOrder("A книга", "B книга");
        assertThat(authorStore.findAll())
                .hasSize(2)
                .extracting("name", String.class)
                .containsExactlyInAnyOrder("Аня", "Вася");
    }

    @Test
    void deleteAuthorShouldRemainJoinTableBooksAssociatedWithAnotherAuthor() {
        authorStore.findByName("Аня").forEach(ann -> authorStore.delete(ann.getId()));
        assertThat(authorStore.findAll())
                .hasSize(1)
                .extracting("name", String.class)
                .containsOnly("Вася");
        assertThat(bookStore.findAll())
                .hasSize(2)
                .extracting("name", String.class)
                .containsExactlyInAnyOrder("A книга", "B книга");
        Long vasyaID = authorStore.findByName("Вася").get(0).getId();
        List<Long> authorId = tx(session ->
                session.createNativeQuery("select ab.author_id from author_book ab;", Long.class)
                        .list());
        assertThat(authorId).hasSize(2).containsOnly(vasyaID);
    }
}
