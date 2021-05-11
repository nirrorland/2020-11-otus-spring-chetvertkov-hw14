package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import javax.transaction.Transactional;
import java.util.List;

@DataJpaTest
//@Import({BookDao.class, AuthorDao.class, GenreDao.class})
public class BookDaoJpaTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    @Transactional
    void getByIdTest() {
        Assert.assertEquals(bookDao.findById(1).get().getName(), "Lord of the Rings");
        Assert.assertEquals(bookDao.findById(1).get().getId(), 1);
        Assert.assertEquals(bookDao.findById(1).get().getAuthor().getName(), "Tolkien");
        Assert.assertEquals(bookDao.findById(1).get().getGenre().getName(), "Drama");
    }

    @Test
    @DisplayName("getById = null, когда ничего не найдено")
    @Transactional
    void getByIdNotFoundTest() {
        Assert.assertFalse(bookDao.findById(0).isPresent());
    }

    @Test
    @DisplayName("getAll получает все записи")
    @Transactional
    void getAllTest() {
        List<Book> result = bookDao.findAll();

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getName(), "Lord of the Rings");
        Assert.assertEquals(result.get(1).getName(), "Istorie Florentine");
        Assert.assertEquals(result.get(2).getName(), "Martian");
    }

    @Test
    @DisplayName("getByName получает нужный экземпляр по name")
    @Transactional
    void geByNameTest() {
        Book result = bookDao.findByNameIgnoreCase("Martian").get();

        Assert.assertEquals(result.getName(), "Martian");
        Assert.assertEquals(result.getId(), 3);
        Assert.assertEquals(result.getAuthor().getName(), "Tolstoy");
        Assert.assertEquals(result.getGenre().getName(), "Comics");
    }

    @Test
    @DisplayName("getByName не зависит от регистра")
    @Transactional
    void geByNameIgnoreCaseTest() {
        Book result = bookDao.findByNameIgnoreCase("MaRtian").get();

        Assert.assertEquals(result.getName(), "Martian");
        Assert.assertEquals(result.getId(), 3);
        Assert.assertEquals(result.getAuthor().getName(), "Tolstoy");
        Assert.assertEquals(result.getGenre().getName(), "Comics");
    }

    @Test
    @DisplayName("getByName = null, когда записи не найдены")
    @Transactional
    void geByNameNotFoundTest() {
        Book result = bookDao.findByNameIgnoreCase("MaRtian123").orElse(null);

        Assert.assertNull(result);
    }

    @Test
    @DisplayName("insert")
    @Transactional
    void insertTest() {
        List<Book> result = bookDao.findAll();
        int size = result.size();

        Author author = authorDao.findByNameIgnoreCase("Tolstoy").get();
        Genre genre = genreDao.findByNameIgnoreCase("Comics").get();

        Book newBook = new Book("name of book", author, genre);
        bookDao.saveAndFlush(newBook);

        Assert.assertEquals(size + 1, bookDao.findAll().size());
        Assert.assertEquals("name of book", bookDao.findById(4).get().getName());
    }

    @Test
    @DisplayName("update")
    @Transactional
    void updateTest() {
        List<Book> result = bookDao.findAll();
        int size = result.size();

        Author author = authorDao.findByNameIgnoreCase("Tolstoy").get();
        Genre genre = genreDao.findByNameIgnoreCase("Comics").get();

        Book newBook = new Book(1, "name of book", author, genre);
        bookDao.saveAndFlush(newBook);

        Assert.assertEquals(size, bookDao.findAll().size());
        Assert.assertEquals("name of book", bookDao.findById(1).get().getName());
    }

    @Test
    @DisplayName("delete")
    @Transactional
    void deleteByIdTest() {
        List<Book> result = bookDao.findAll();
        int size = result.size();

        bookDao.deleteById(1);

        Assert.assertEquals(size - 1, bookDao.findAll().size());
        Assert.assertFalse(bookDao.findById(1).isPresent());
    }

}
