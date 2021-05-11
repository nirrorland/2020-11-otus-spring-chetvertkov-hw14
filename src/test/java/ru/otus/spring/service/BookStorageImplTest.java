package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
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
class BookStorageImplTest {

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
        List<Author> authors = bookStorageService.getAllAuthors();

        assertEquals(authors.size(), 3);
        assertEquals(authors.get(0).getName(), "Tolkien");
        assertEquals(authors.get(1).getName(), "Machiavelli");
        assertEquals(authors.get(2).getName(), "Tolstoy");
    }

    @Test
    void getAllGenresTest() {
        List<Genre> genres = bookStorageService.getAllGenres();

        assertEquals(genres.size(), 3);
        assertEquals(genres.get(0).getName(), "Drama");
        assertEquals(genres.get(1).getName(), "Comics");
        assertEquals(genres.get(2).getName(), "History");
    }

    @Test
    void getAllBooksTest() {
        List<Book> books = bookStorageService.getAllBooks();

        assertEquals(books.size(), 3);
        assertEquals(books.get(0).getName(), "Lord of the Rings");
        assertEquals(books.get(1).getName(), "Istorie Florentine");
        assertEquals(books.get(2).getName(), "Martian");
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

        Book book = bookService.getByName("NewMartian");
        assertNotNull(book);
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
        Book book = bookService.getByName("Lord of the Rings");
        assertNotNull(book);

        bookStorageService.deleteBook("Lord of the Rings");

        book = bookService.getByName("Lord of the Rings");
        assertNull(book);
    }

    @Test
    @Transactional
    void addCommentTest() {
        List<Comment> comments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(comments.size(), 0);

        bookStorageService.addComment("Lord of the Rings", "TestComment");

        em.clear();

        comments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(comments.size(), 1);
    }

    @Test
    @Transactional
    void getCommentsForBookTest() {
        List<Comment> comments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(comments.size(), 0);

        bookStorageService.addComment("Lord of the Rings", "TestComment2");
        em.clear();

        List<Comment> resultComments = bookStorageService.getCommentsForBook("Lord of the Rings");
        assertEquals(resultComments.get(0).getText(), "TestComment2");
    }

    @Test
    @Transactional
    void deleteCommentByIdTest() {
        List<Comment> comments = bookStorageService.getCommentsForBook("Martian");
        assertEquals(comments.size(), 2);

        bookStorageService.deleteCommentById(comments.get(0).getId());
        em.clear();

        comments = bookStorageService.getCommentsForBook("Martian");
        assertEquals(comments.size(), 1);

    }
}