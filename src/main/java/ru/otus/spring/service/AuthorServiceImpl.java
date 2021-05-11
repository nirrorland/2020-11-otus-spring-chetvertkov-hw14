package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorDao authorDao) {
        this.authorRepository = authorDao;
    }

    @Override
    public Author getById(int id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public Author getByName(String name) {
        return authorRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }
}
