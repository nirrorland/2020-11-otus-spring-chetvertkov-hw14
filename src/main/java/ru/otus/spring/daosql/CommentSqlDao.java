package ru.otus.spring.daosql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domainsql.CommentSql;

import java.util.Optional;

public interface CommentSqlDao extends JpaRepository<CommentSql, Long> {

    Optional<CommentSql> findById(long id);

    CommentSql save(CommentSql commentSql);

    void delete(CommentSql commentSql);

    void deleteById(long id);

}
