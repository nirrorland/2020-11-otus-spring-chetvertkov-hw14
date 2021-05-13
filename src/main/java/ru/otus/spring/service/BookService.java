package ru.otus.spring.service;

import ru.otus.spring.domainsql.BookSql;

import java.util.List;

public interface BookService {

    BookSql getById(long id);

    BookSql getByName(String name);

    List<BookSql> getAll();

    void insert(BookSql bookSql);

    void update(BookSql bookSql);

    void deleteById(long id);
}
