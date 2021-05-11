package ru.otus.spring.shell;

import org.h2.tools.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;
import ru.otus.spring.domainsql.GenreSql;
import ru.otus.spring.service.BookStorage;
import ru.otus.spring.service.ConsoleIOService;

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
        List<AuthorSql> authorsSql = bookStorage.getAllAuthors();
        if ((authorsSql != null) && (authorsSql.size() > 0)) {
            for (AuthorSql authorSql : authorsSql) {
                consoleIOService.out(authorSql.getName());
            }
        } else {
            consoleIOService.out("No authors");
        }
    }

    @ShellMethod(value = "List all genres", key = {"lg", "list-genres"})
    public void listGenres() {
        List<GenreSql> genreSqls = bookStorage.getAllGenres();

        if ((genreSqls != null) && (genreSqls.size() > 0)) {
            for (GenreSql genreSql : genreSqls) {
                consoleIOService.out(genreSql.getName());
            }
        } else {
            consoleIOService.out("No genres");
        }
    }

    @ShellMethod(value = "List all books", key = {"lb", "list-books"})
    public void listBooks() {
        List<BookSql> booksSql = bookStorage.getAllBooks();

        if ((booksSql != null) && (booksSql.size() > 0)) {
            for (BookSql bookSql : booksSql) {
                consoleIOService.out(bookSql.toString() + " || GENRE=" + bookSql.getGenreSql().getName() + " || AUTHOR=" + bookSql.getAuthorSql().getName());
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
            List<CommentSql> commentSqls = bookStorage.getCommentsForBook(bookName);

            if ((commentSqls != null) && (commentSqls.size() > 0)) {
                for (CommentSql commentSql : commentSqls) {
                    consoleIOService.out(commentSql.toString());
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
