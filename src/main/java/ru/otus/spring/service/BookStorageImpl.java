package ru.otus.spring.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
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
    public List<Author> getAllAuthors() {
        return authorService.getAll();
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @Override
    public void insertBook(String bookName, String authorName, String genreName) {
        Author author = authorService.getByName(authorName);
        Genre genre = genreService.getByName(genreName);
        Book oldBook = bookService.getByName(bookName);


        boolean isNotReadyForInsertion = false;

        if (author == null) {
            isNotReadyForInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_AUTHOR_NOT_FOUND);
        }

        if (genre == null) {
            isNotReadyForInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_GENRE_NOT_FOUND);
        }

        if (oldBook != null) {
            isNotReadyForInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOKNAME_ALREADY_EXISTS);
        }

        if (!isNotReadyForInsertion) {
            Book book = new Book(bookName, author, genre);
            bookService.insert(book);
        }
    }

    @Override
    public void updateBook(String oldBookName, String newBookName, String newAuthorName, String newGenreName) {
        Author author = authorService.getByName(newAuthorName);
        Genre genre = genreService.getByName(newGenreName);
        Book oldBook = bookService.getByName(oldBookName);


        boolean isNotReadyForUpdate = false;

        if (author == null) {
            isNotReadyForUpdate = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_AUTHOR_NOT_FOUND);
        }

        if (genre == null) {
            isNotReadyForUpdate = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_GENRE_NOT_FOUND);
        }

        if (oldBook == null) {
            isNotReadyForUpdate = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReadyForUpdate) {
            Book book = new Book(newBookName, author, genre);
            book.setId(oldBook.getId());
            bookService.update(book);
        }
    }

    @Override
    public void deleteBook(String bookName) {
        Book book = bookService.getByName(bookName);


        boolean isNotReadyForDelete = false;

        if (book == null) {
            isNotReadyForDelete = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReadyForDelete) {
            bookService.deleteById(book.getId());
        }
    }

    @Override
    @Transactional
    public void addComment(String bookName, String text) {
        boolean isNotReadyForCommentInsertion = false;
        Book book = bookService.getByName(bookName);

        if (book == null) {
            isNotReadyForCommentInsertion = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReadyForCommentInsertion) {
            Comment comment = new Comment(book, text);
            commentService.addComment(comment);
        }
    }

    @Override
    @Transactional
    public List<Comment> getCommentsForBook(String bookName) {
        boolean isNotReady = false;
        Book book = bookService.getByName(bookName);

        if (book == null) {
            isNotReady = true;
            eventsPublisher.publishErrorEvent(EventMessage.EM_BOOK_NOT_FOUND);
        }

        if (!isNotReady) {
            Hibernate.initialize(book.getComments());
            return book.getComments();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteCommentById(long id) {
        Comment comment = commentService.findCommentByID(id);

        if (comment != null) {
            commentService.deleteById(comment.getId());

        }
    }

}
