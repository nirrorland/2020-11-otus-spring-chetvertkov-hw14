package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Genre;
import java.util.Optional;

public interface GenreDao extends JpaRepository<Genre, Long> {

    Optional<Genre> findById(long id);

    Optional<Genre> findByNameIgnoreCase(String name);
}
