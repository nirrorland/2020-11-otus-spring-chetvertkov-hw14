package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.daosql.AuthorSqlDao;
import ru.otus.spring.domainsql.AuthorSql;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorSqlDao authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorSqlDao authorSqlDao) {
        this.authorRepository = authorSqlDao;
    }

    @Override
    public AuthorSql getById(int id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public AuthorSql getByName(String name) {
        return authorRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public List<AuthorSql> getAll() {
        return authorRepository.findAll();
    }
}
