package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;
import ru.otus.spring.domainsql.GenreSql;
import ru.otus.spring.event.ErrorEvent;
import ru.otus.spring.event.EventListner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
class BookSqlStorageImplTest {

    @Autowired
    BookService bookService;

    @Autowired
    CommentService commentService;

    @MockBean
    EventListner eventListner;

    @Autowired
    BookStorage bookStorageService;

    @Autowired
    private EntityManager em;

    @Test
    void getAllAuthorsTest() {
        List<AuthorSql> authorsSql = bookStorageService.getAllAuthors();

        assertEquals(authorsSql.size(), 3);
        assertEquals(authorsSql.get(0).getName(), "Tolkien");
        assertEquals(authorsSql.get(1).getName(), "Machiavelli");
        assertEquals(authorsSql.get(2).getName(), "Tolstoy");
    }

    @Test
    void getAllGenresTest() {
        List<GenreSql> genreSqls = bookStorageService.getAllGenres();

        assertEquals(genreSqls.size(), 3);
        assertEquals(genreSqls.get(0).getName(), "Drama");
        assertEquals(genreSqls.get(1).getName(), "Comics");
        assertEquals(genreSqls.get(2).getName(), "History");
    }

    @Test
    void getAllBooksTest() {
        List<BookSql> booksSql = bookStorageService.getAllBooks();

        assertEquals(booksSql.size(), 3);
        assertEquals(booksSql.get(0).getName(), "Lord of the Rings");
        assertEquals(booksSql.get(1).getName(), "Istorie Florentine");
        assertEquals(booksSql.get(2).getName(), "Martian");
    }

    @Test
    @Transactional
    void insertBookTest() {
        bookStorageService.insertBook("123", "Tolstoy", "Drama");

        assertNotNull(bookService.getByName("123"));
    }

    @Test
    @Transactional
    void insertBookTestWhenNoAuthor() {
        bookStorageService.insertBook("123", "Tolstoy22", "Drama");

        assertNull(bookService.getByName("123"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Transactional
    void insertBookTestWhenNoGenre() {
        bookStorageService.insertBook("123", "Tolstoy", "Drama22");

        assertNull(bookService.getByName("123"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Transactional
    void insertBookTestWhenNoGenreAndNoAuthor() {
        bookStorageService.insertBook("123", "Tolstoy22", "Drama22");

        assertNull(bookService.getByName("123"));
        verify(eventListner, times(2)).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Transactional
    void updateBookTest() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy", "Drama");

        BookSql bookSql = bookService.getByName("NewMartian");
        assertNotNull(bookSql);
    }

    @Test
    @Transactional
    void updateBookTestNoAuthor() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy22", "Drama");

        assertNull(bookService.getByName("NewMartian"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Transactional
    void updateBookTestNoGenre() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy", "Drama22");

        assertNull(bookService.getByName("NewMartian"));
        verify(eventListner).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Transactional
    void updateBookTestNoAuthorAndNoGenre() {
        bookStorageService.updateBook("Martian", "NewMartian", "Tolstoy22", "Drama22");

        assertNull(bookService.getByName("NewMartian"));
        verify(eventListner, times(2)).handleErrorListener(any(ErrorEvent.class));
    }

    @Test
    @Transactional
    void deleteBookTest() {
        BookSql bookSql = bookService.getByName("Lord of the Rings");
        assertNotNull(bookSql);

        bookStorageService.deleteBook("Lord of the Rings");

        bookSql = bookService.getByName("Lord of the Rings");
        assertNull(bookSql);
    }

    @Test
    @Transactional
    void addCommentTest() {
        List<CommentSql> commentSqls = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(commentSqls.size(), 0);

        bookStorageService.addComment("Lord of the Rings", "TestComment");

        em.clear();

        commentSqls = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(commentSqls.size(), 1);
    }

    @Test
    @Transactional
    void getCommentsForBookTest() {
        List<CommentSql> commentSqls = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(commentSqls.size(), 0);

        bookStorageService.addComment("Lord of the Rings", "TestComment2");
        em.clear();

        List<CommentSql> resultCommentSqls = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(resultCommentSqls.get(0).getText(), "TestComment2");
    }

    @Test
    @Transactional
    void deleteCommentByIdTest() {
        List<CommentSql> commentSqls = bookStorageService.getCommentsForBook("Martian");
        assertEquals(commentSqls.size(), 2);

        bookStorageService.deleteCommentById(commentSqls.get(0).getId());
        em.clear();

        commentSqls = bookStorageService.getCommentsForBook("Martian");
        assertEquals(commentSqls.size(), 1);

    }
}