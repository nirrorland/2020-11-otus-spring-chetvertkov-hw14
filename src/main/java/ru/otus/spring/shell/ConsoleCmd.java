package ru.otus.spring.shell;

import org.h2.tools.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.service.BookStorage;
import ru.otus.spring.service.ConsoleIOService;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;

@ShellComponent
public class ConsoleCmd {

    final private BookStorage bookStorage;
    final private ConsoleIOService consoleIOService;

    private static final String NOVALUE = "NOVALUE";

    @Autowired
    public ConsoleCmd(BookStorage bookStorage, ConsoleIOService consoleIOService) {
        this.bookStorage = bookStorage;
        this.consoleIOService = consoleIOService;
    }

    @ShellMethod(value = "List all authors", key = {"la", "list-authors"})
    public void listAuthors() {
        List<Author> authors = bookStorage.getAllAuthors();
        if ((authors != null) && (authors.size() > 0)) {
            for (Author author : authors) {
                consoleIOService.out(author.getName());
            }
        } else {
            consoleIOService.out("No authors");
        }
    }

    @ShellMethod(value = "List all genres", key = {"lg", "list-genres"})
    public void listGenres() {
        List<Genre> genres = bookStorage.getAllGenres();

        if ((genres != null) && (genres.size() > 0)) {
            for (Genre genre : genres) {
                consoleIOService.out(genre.getName());
            }
        } else {
            consoleIOService.out("No genres");
        }
    }

    @ShellMethod(value = "List all books", key = {"lb", "list-books"})
    public void listBooks() {
        List<Book> books = bookStorage.getAllBooks();

        if ((books != null) && (books.size() > 0)) {
            for (Book book : books) {
                consoleIOService.out(book.toString() + " || GENRE=" + book.getGenre().getName() + " || AUTHOR=" + book.getAuthor().getName());
            }
        } else {
            consoleIOService.out("No books");
        }
    }

    @ShellMethod(value = "Insert new book {(String oldBookName, String newBookName, String newAuthorName, String newGenreName)}", key = {"ib", "insert-book"})
    public void insertBook(@ShellOption(defaultValue = NOVALUE) String bookName,
                           @ShellOption(defaultValue = NOVALUE) String authorName,
                           @ShellOption(defaultValue = NOVALUE) String genreName) {
        if (!bookName.equals(NOVALUE) && !authorName.equals(NOVALUE) && !genreName.equals(NOVALUE)) {
            bookStorage.insertBook(bookName, authorName, genreName);
        } else {
            consoleIOService.out("Cannot insert.");
        }
    }

    @ShellMethod(value = "Update book {(String oldBookName, String newBookName, String newAuthorName, String newGenreName)}", key = {"ub", "update-book"})
    public void updateBook(@ShellOption(defaultValue = NOVALUE) String oldBookName,
                           @ShellOption(defaultValue = NOVALUE) String newBookName,
                           @ShellOption(defaultValue = NOVALUE) String newAuthorName,
                           @ShellOption(defaultValue = NOVALUE) String newGenreName) {
        if (!oldBookName.equals(NOVALUE) && !newBookName.equals(NOVALUE) && !newAuthorName.equals(NOVALUE) && !newGenreName.equals(NOVALUE)) {
            bookStorage.updateBook(oldBookName, newBookName, newAuthorName, newGenreName);
        } else {
            consoleIOService.out("Wrong parameters. Cannot update.");
        }
    }

    @ShellMethod(value = "Delete book by name {(String bookName)}", key = {"db", "delete-book"})
    public void deleteBook(@ShellOption(defaultValue = NOVALUE) String bookName) {
        if (!bookName.equals(NOVALUE)) {
            bookStorage.deleteBook(bookName);
        } else {
            consoleIOService.out("Cannot delete.");
        }
    }

    @ShellMethod(value = "Add comment {(String bookName, String text)}", key = {"ac", "add-comment"})
    public void addComment(@ShellOption(defaultValue = NOVALUE) String bookName,
                           @ShellOption(defaultValue = NOVALUE) String text) {
        if (!bookName.equals(NOVALUE) && !text.equals(NOVALUE)) {
            bookStorage.addComment(bookName, text);
        } else {
            consoleIOService.out("Cannot add comment.");
        }
    }

    @ShellMethod(value = "View comments for {(String bookName)}", key = {"vc", "view-comments"})
    public void viewCommentsForBook(@ShellOption(defaultValue = NOVALUE) String bookName) {
        if (!bookName.equals(NOVALUE)) {
            List<Comment> comments = bookStorage.getCommentsForBook(bookName);

            if ((comments != null) && (comments.size() > 0)) {
                for (Comment comment : comments) {
                    consoleIOService.out(comment.toString());
                }
            } else {
                consoleIOService.out("No comments for this book");
            }

        }
    }

    @ShellMethod(value = "Delete comment by Id {(Integer ID)}", key = {"dc", "delete-comment"})
    public void deleteCommentById(@ShellOption Integer id) {
        if (id != null) {
            bookStorage.deleteCommentById(id);

        } else {
            consoleIOService.out("No comments for this book");
        }
    }

    @ShellMethod(value = "Open H2 console", key = {"h2c", "h2-console"})
    public void h2CpnsoleOpen() throws SQLException {
        Console.main();
    }

}
