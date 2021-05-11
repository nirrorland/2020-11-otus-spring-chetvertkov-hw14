package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@DataJpaTest
//@Import({CommentDao.class, BookDao.class})
@Transactional
public class CommentDaoJpaTest {
    @Autowired
    private CommentDao commentRepo;

    @Autowired
    private BookDao bookRepo;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("addComment добавляет комментарий")
    void addComment() {
        Book book = bookRepo.findByNameIgnoreCase("Martian").get();
        List<Comment> comments = book.getComments();
        int size = comments.size();

        Comment comment = new Comment(book, "test comment");
        commentRepo.saveAndFlush(comment);

        em.clear();

        Book book2 = bookRepo.findByNameIgnoreCase("Martian").get();
        List<Comment>comments2 = book2.getComments();;
        Assert.assertEquals(comments2.size(), size + 1);
    }

    @Test
    @DisplayName("deleteComment удаляет комментарий")
    void deleteComment() {
        Book book = bookRepo.findByNameIgnoreCase("Martian").get();
        List<Comment> comments = book.getComments();
        int size = comments.size();

        Comment comment = commentRepo.findById(1).get();

        commentRepo.delete(comment);
        commentRepo.flush();
        em.clear();

        Book book2 = bookRepo.findByNameIgnoreCase("Martian").get();
        List<Comment> comments2 = book2.getComments();
        Assert.assertEquals(comments2.size(), size - 1);
    }

    @Test
    void findCommentForBook() {
        Book book = bookRepo.findByNameIgnoreCase("Martian").get();
        List<Comment> comments = book.getComments();
        Assert.assertEquals(comments.size(), 2);
    }

    @Test
    void findCommentForBookNoResult() {
        Book book = bookRepo.findByNameIgnoreCase("Lord of the Rings").get();
        List<Comment> comments = book.getComments();
        Assert.assertTrue(comments.isEmpty());
    }

    @Test
    void findCommentById() {
        Comment comment = commentRepo.findById(2).get();
        Assert.assertEquals(comment.getText(), "Martian Comment 2");
    }


}
