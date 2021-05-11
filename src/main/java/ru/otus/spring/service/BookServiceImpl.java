package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.daosql.BookSqlDao;
import ru.otus.spring.domainsql.BookSql;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookSqlDao bookRepository;

    @Autowired
    public BookServiceImpl(BookSqlDao BookSqlDao) {
        this.bookRepository = BookSqlDao;
    }

    @Override
    public BookSql getById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public BookSql getByName(String name) {
        return bookRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public List<BookSql> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void insert(BookSql bookSql) {
        bookRepository.saveAndFlush(bookSql);
    }

    @Override
    @Transactional
    public void update(BookSql bookSql) {
        bookRepository.saveAndFlush(bookSql);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
        bookRepository.flush();
    }


}
