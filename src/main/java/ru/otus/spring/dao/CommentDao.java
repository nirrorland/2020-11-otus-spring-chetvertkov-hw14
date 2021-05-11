package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Comment;
import java.util.Optional;

public interface CommentDao extends JpaRepository<Comment, Long> {

    Optional<Comment> findById(long id);

    Comment save(Comment comment);

    void delete(Comment comment);

    void deleteById(long id);

}
