package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Long> {

    Optional<Book> findById(long id);

    Optional<Book> findByNameIgnoreCase(String name);

    void deleteById(long id);
}
