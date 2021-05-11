package ru.otus.spring.service;

import ru.otus.spring.domainsql.AuthorSql;

import java.util.List;

public interface AuthorService {

    AuthorSql getById(int id);

    List<AuthorSql> getAll();

    AuthorSql getByName(String name);
}
