package ru.otus.spring.service;


import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;
import ru.otus.spring.domainsql.GenreSql;

import java.util.List;

public interface BookStorage {

    List<AuthorSql> getAllAuthors();

    List<GenreSql> getAllGenres();

    List<BookSql> getAllBooks();

    void insertBook(String bookName, String authorName, String genreName);

    void updateBook(String oldBookName, String newBookName, String newAuthorName, String newGenreName);

    void deleteBook(String bookName);

    void addComment(String bookName, String text);

    List<CommentSql> getCommentsForBook(String bookName);

    void deleteCommentById(long id);
}
