package ru.otus.spring.daosql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domainsql.AuthorSql;

import java.util.Optional;

public interface AuthorSqlDao extends JpaRepository<AuthorSql, Long> {

    Optional<AuthorSql> findById(long id);

    Optional<AuthorSql> findByNameIgnoreCase(String name);

}
