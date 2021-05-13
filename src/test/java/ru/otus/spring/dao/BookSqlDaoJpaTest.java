package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.daosql.AuthorSqlDao;
import ru.otus.spring.daosql.BookSqlDao;
import ru.otus.spring.daosql.GenreSqlDao;
import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.GenreSql;

import javax.transaction.Transactional;
import java.util.List;

@DataJpaTest
//@Import({BookDao.class, AuthorDao.class, GenreDao.class})
public class BookSqlDaoJpaTest {

    @Autowired
    private BookSqlDao bookSqlDao;

    @Autowired
    private AuthorSqlDao authorSqlDao;

    @Autowired
    private GenreSqlDao genreSqlDao;

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    @Transactional
    void getByIdTest() {
        Assert.assertEquals(bookSqlDao.findById(1).get().getName(), "Lord of the Rings");
        Assert.assertEquals(bookSqlDao.findById(1).get().getId(), 1);
        Assert.assertEquals(bookSqlDao.findById(1).get().getAuthorSql().getName(), "Tolkien");
        Assert.assertEquals(bookSqlDao.findById(1).get().getGenreSql().getName(), "Drama");
    }

    @Test
    @DisplayName("getById = null, когда ничего не найдено")
    @Transactional
    void getByIdNotFoundTest() {
        Assert.assertFalse(bookSqlDao.findById(0).isPresent());
    }

    @Test
    @DisplayName("getAll получает все записи")
    @Transactional
    void getAllTest() {
        List<BookSql> result = bookSqlDao.findAll();

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getName(), "Lord of the Rings");
        Assert.assertEquals(result.get(1).getName(), "Istorie Florentine");
        Assert.assertEquals(result.get(2).getName(), "Martian");
    }

    @Test
    @DisplayName("getByName получает нужный экземпляр по name")
    @Transactional
    void geByNameTest() {
        BookSql result = bookSqlDao.findByNameIgnoreCase("Martian").get();

        Assert.assertEquals(result.getName(), "Martian");
        Assert.assertEquals(result.getId(), 3);
        Assert.assertEquals(result.getAuthorSql().getName(), "Tolstoy");
        Assert.assertEquals(result.getGenreSql().getName(), "Comics");
    }

    @Test
    @DisplayName("getByName не зависит от регистра")
    @Transactional
    void geByNameIgnoreCaseTest() {
        BookSql result = bookSqlDao.findByNameIgnoreCase("MaRtian").get();

        Assert.assertEquals(result.getName(), "Martian");
        Assert.assertEquals(result.getId(), 3);
        Assert.assertEquals(result.getAuthorSql().getName(), "Tolstoy");
        Assert.assertEquals(result.getGenreSql().getName(), "Comics");
    }

    @Test
    @DisplayName("getByName = null, когда записи не найдены")
    @Transactional
    void geByNameNotFoundTest() {
        BookSql result = bookSqlDao.findByNameIgnoreCase("MaRtian123").orElse(null);

        Assert.assertNull(result);
    }

    @Test
    @DisplayName("insert")
    @Transactional
    void insertTest() {
        List<BookSql> result = bookSqlDao.findAll();
        int size = result.size();

        AuthorSql authorSql = authorSqlDao.findByNameIgnoreCase("Tolstoy").get();
        GenreSql genreSql = genreSqlDao.findByNameIgnoreCase("Comics").get();

        BookSql newBookSql = new BookSql("name of book", authorSql, genreSql);
        bookSqlDao.saveAndFlush(newBookSql);

        Assert.assertEquals(size + 1, bookSqlDao.findAll().size());
        Assert.assertEquals("name of book", bookSqlDao.findById(4).get().getName());
    }

    @Test
    @DisplayName("update")
    @Transactional
    void updateTest() {
        List<BookSql> result = bookSqlDao.findAll();
        int size = result.size();

        AuthorSql authorSql = authorSqlDao.findByNameIgnoreCase("Tolstoy").get();
        GenreSql genreSql = genreSqlDao.findByNameIgnoreCase("Comics").get();

        BookSql newBookSql = new BookSql(1, "name of book", authorSql, genreSql);
        bookSqlDao.saveAndFlush(newBookSql);

        Assert.assertEquals(size, bookSqlDao.findAll().size());
        Assert.assertEquals("name of book", bookSqlDao.findById(1).get().getName());
    }

    @Test
    @DisplayName("delete")
    @Transactional
    void deleteByIdTest() {
        List<BookSql> result = bookSqlDao.findAll();
        int size = result.size();

        bookSqlDao.deleteById(1);

        Assert.assertEquals(size - 1, bookSqlDao.findAll().size());
        Assert.assertFalse(bookSqlDao.findById(1).isPresent());
    }

}
