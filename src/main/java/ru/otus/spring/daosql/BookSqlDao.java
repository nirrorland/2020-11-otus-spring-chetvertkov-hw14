package ru.otus.spring.daosql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domainsql.BookSql;

import java.util.Optional;

public interface BookSqlDao extends JpaRepository<BookSql, Long> {

    Optional<BookSql> findById(long id);

    Optional<BookSql> findByNameIgnoreCase(String name);

    void deleteById(long id);
}
