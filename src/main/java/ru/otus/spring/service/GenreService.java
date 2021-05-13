package ru.otus.spring.service;


import ru.otus.spring.domainsql.GenreSql;

import java.util.List;

public interface GenreService {

    GenreSql getById(int id);

    List<GenreSql> getAll();

    GenreSql getByName(String name);

}
