package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;

public interface BookStorage {

    List<Author> getAllAuthors();

    List<Genre> getAllGenres();

    List<Book> getAllBooks();

    void insertBook(String bookName, String authorName, String genreName);

    void updateBook(String oldBookName, String newBookName, String newAuthorName, String newGenreName);

    void deleteBook(String bookName);

    void addComment(String bookName, String text);

    List<Comment> getCommentsForBook(String bookName);

    void deleteCommentById(long id);
}
