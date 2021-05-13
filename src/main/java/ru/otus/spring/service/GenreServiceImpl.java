package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.daosql.GenreSqlDao;
import ru.otus.spring.domainsql.GenreSql;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreSqlDao genreRepository;

    @Autowired
    public GenreServiceImpl(GenreSqlDao genreSqlDao) {
        this.genreRepository = genreSqlDao;
    }

    @Override
    public GenreSql getById(int id) {
        return genreRepository.findById(id).orElse(null);
    }

    @Override
    public List<GenreSql> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public GenreSql getByName(String name) {
        return genreRepository.findByNameIgnoreCase(name).orElse(null);
    }
}
