package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookDao bookRepository;

    @Autowired
    public BookServiceImpl(BookDao BookDao) {
        this.bookRepository = BookDao;
    }

    @Override
    public Book getById(long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book getByName(String name) {
        return bookRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public void insert(Book book) {
        bookRepository.saveAndFlush(book);
    }

    @Override
    @Transactional
    public void update(Book book) {
        bookRepository.saveAndFlush(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
        bookRepository.flush();
    }


}
