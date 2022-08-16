package com.example.hiber;

import com.example.hiber.manytomany.Author;
import com.example.hiber.manytomany.AuthorStore;
import com.example.hiber.manytomany.Book;
import com.example.hiber.manytomany.BookStore;
import com.example.hiber.persistence.CRUDStore;
import com.example.hiber.persistence.SessionStore;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;


public class AuthorBooksTest implements SessionStore {
    private static final CRUDStore<Author> authorStore = new AuthorStore();
    private static final CRUDStore<Book> bookStore = new BookStore();

    @Test
    void manyToManyAuthorAndBooks() {
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

        ann = authorStore.findByName("Аня").get(0);
        authorStore.delete(ann.getId());
        assertThat(authorStore.findByName("Вася"))
                .hasSize(1)
                .first()
                .extracting("name", as(InstanceOfAssertFactories.STRING))
                .isEqualTo("Вася");
    }
}
