package ru.otus.spring.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;
import ru.otus.spring.domainsql.GenreSql;
import ru.otus.spring.event.EventMessage;
import ru.otus.spring.event.EventPublisher;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BookStorageImpl implements BookStorage {
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;
    private final CommentService commentService;
    private final ConsoleIOService consoleIOService;
    private final EventPublisher eventsPublisher;

    public BookStorageImpl(AuthorService authorService, GenreService genreService, BookService bookService, CommentService commentService, ConsoleIOService consoleIOService, EventPublisher eventsPublisher) {
        this.authorService = authorService;
        this.genreService = genreService;
        this.bookService = bookService;
        this.commentService = commentService;
        this.consoleIOService = consoleIOService;
        this.eventsPublisher = eventsPublisher;
    }

    @Override
    public List<AuthorSql> getAllAuthors() {
        return authorService.getAll();
    }

    @Override
    public List<GenreSql> getAllGenres() {
        return genreService.getAll();
    }

    @Override
    public List<BookSql> getAllBooks() {
        return bookService.getAll();
    }

    @Override
    public void insertBook(String bookName, String authorName, String genreName) {
        AuthorSql authorSql = authorService.getByName(authorName);
        GenreSql genreSql = genreService.getByName(genreName);
        BookSql oldBookSql = bookService.getByName(bookName);


        boolean isNotReadyForInsertion = false;

        if (authorSql == null) {
            isNotReadyForInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_AUTHOR_NOT_FOUND);
        }

        if (genreSql == null) {
            isNotReadyForInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_GENRE_NOT_FOUND);
        }

        if (oldBookSql != null) {
            isNotReadyForInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOKNAME_ALREADY_EXISTS);
        }

        if (!isNotReadyForInsertion) {
            BookSql bookSql = new BookSql(bookName, authorSql, genreSql);
            bookService.insert(bookSql);
        }
    }

    @Override
    public void updateBook(String oldBookName, String newBookName, String newAuthorName, String newGenreName) {
        AuthorSql authorSql = authorService.getByName(newAuthorName);
        GenreSql genreSql = genreService.getByName(newGenreName);
        BookSql oldBookSql = bookService.getByName(oldBookName);


        boolean isNotReadyForUpdate = false;

        if (authorSql == null) {
            isNotReadyForUpdate = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_AUTHOR_NOT_FOUND);
        }

        if (genreSql == null) {
            isNotReadyForUpdate = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_GENRE_NOT_FOUND);
        }

        if (oldBookSql == null) {
            isNotReadyForUpdate = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReadyForUpdate) {
            BookSql bookSql = new BookSql(newBookName, authorSql, genreSql);
            bookSql.setId(oldBookSql.getId());
            bookService.update(bookSql);
        }
    }

    @Override
    public void deleteBook(String bookName) {
        BookSql bookSql = bookService.getByName(bookName);


        boolean isNotReadyForDelete = false;

        if (bookSql == null) {
            isNotReadyForDelete = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReadyForDelete) {
            bookService.deleteById(bookSql.getId());
        }
    }

    @Override
    @Transactional
    public void addComment(String bookName, String text) {
        boolean isNotReadyForCommentInsertion = false;
        BookSql bookSql = bookService.getByName(bookName);

        if (bookSql == null) {
            isNotReadyForCommentInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReadyForCommentInsertion) {
            CommentSql commentSql = new CommentSql(bookSql, text);
            commentService.addComment(commentSql);
        }
    }

    @Override
    @Transactional
    public List<CommentSql> getCommentsForBook(String bookName) {
        boolean isNotReady = false;
        BookSql bookSql = bookService.getByName(bookName);

        if (bookSql == null) {
            isNotReady = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReady) {
            Hibernate.initialize(bookSql.getCommentSqls());
            return bookSql.getCommentSqls();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        CommentSql commentSql = commentService.findCommentByID(id);

        if (commentSql != null) {
            commentService.deleteById(commentSql.getId());

        }
    }

}
