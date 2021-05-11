package ru.otus.spring.daosql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domainsql.GenreSql;

import java.util.Optional;

public interface GenreSqlDao extends JpaRepository<GenreSql, Long> {

    Optional<GenreSql> findById(long id);

    Optional<GenreSql> findByNameIgnoreCase(String name);
}
