package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;
import java.util.List;

public interface GenreService {

    Genre getById(int id);

    List<Genre> getAll();

    Genre getByName(String name);

}
