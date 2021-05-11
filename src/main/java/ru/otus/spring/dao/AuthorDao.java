package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Author;
import java.util.Optional;

public interface AuthorDao  extends JpaRepository<Author, Long> {

    Optional<Author> findById(long id);

    Optional<Author> findByNameIgnoreCase(String name);

}
