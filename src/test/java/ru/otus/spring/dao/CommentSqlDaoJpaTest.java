package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.daosql.BookSqlDao;
import ru.otus.spring.daosql.CommentSqlDao;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@DataJpaTest
//@Import({CommentDao.class, BookDao.class})
@Transactional
public class CommentSqlDaoJpaTest {
    @Autowired
    private CommentSqlDao commentRepo;

    @Autowired
    private BookSqlDao bookRepo;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("addComment добавляет комментарий")
    void addComment() {
        BookSql bookSql = bookRepo.findByNameIgnoreCase("Martian").get();
        List<CommentSql> commentSqls = bookSql.getCommentSqls();
        int size = commentSqls.size();

        CommentSql commentSql = new CommentSql(bookSql, "test comment");
        commentRepo.saveAndFlush(commentSql);

        em.clear();

        BookSql bookSql2 = bookRepo.findByNameIgnoreCase("Martian").get();
        List<CommentSql>comments2 = bookSql2.getCommentSqls();;
        Assert.assertEquals(comments2.size(), size + 1);
    }

    @Test
    @DisplayName("deleteComment удаляет комментарий")
    void deleteComment() {
        BookSql bookSql = bookRepo.findByNameIgnoreCase("Martian").get();
        List<CommentSql> commentSqls = bookSql.getCommentSqls();
        int size = commentSqls.size();

        CommentSql commentSql = commentRepo.findById(1).get();

        commentRepo.delete(commentSql);
        commentRepo.flush();
        em.clear();

        BookSql bookSql2 = bookRepo.findByNameIgnoreCase("Martian").get();
        List<CommentSql> comments2 = bookSql2.getCommentSqls();
        Assert.assertEquals(comments2.size(), size - 1);
    }

    @Test
    void findCommentForBook() {
        BookSql bookSql = bookRepo.findByNameIgnoreCase("Martian").get();
        List<CommentSql> commentSqls = bookSql.getCommentSqls();
        Assert.assertEquals(commentSqls.size(), 2);
    }

    @Test
    void findCommentForBookNoResult() {
        BookSql bookSql = bookRepo.findByNameIgnoreCase("Lord of the Rings").get();
        List<CommentSql> commentSqls = bookSql.getCommentSqls();
        Assert.assertTrue(commentSqls.isEmpty());
    }

    @Test
    void findCommentById() {
        CommentSql commentSql = commentRepo.findById(2).get();
        Assert.assertEquals(commentSql.getText(), "Martian Comment 2");
    }


}
