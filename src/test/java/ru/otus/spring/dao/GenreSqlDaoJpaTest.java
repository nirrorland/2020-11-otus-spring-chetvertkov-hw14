package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.daosql.GenreSqlDao;
import ru.otus.spring.domainsql.GenreSql;

import java.util.List;

@DataJpaTest
//@Import(GenreDao.class)
public class GenreSqlDaoJpaTest {
    @Autowired
    private GenreSqlDao genreSqlDao;

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getByIdTest() {
        Assert.assertEquals(genreSqlDao.findById(1).get().getName(), "Drama");
    }

    @Test
    @DisplayName("getById = null, если ничего не найдено")
    void getByIdNotFoundTest() {
        Assert.assertFalse(genreSqlDao.findById(6).isPresent());
    }

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getAllTest() {
        List<GenreSql> result = genreSqlDao.findAll();

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getName(), "Drama");
        Assert.assertEquals(result.get(1).getName(), "Comics");
        Assert.assertEquals(result.get(2).getName(), "History");
    }

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getByNameTest() {
        GenreSql result = genreSqlDao.findByNameIgnoreCase("History").get();

        Assert.assertEquals("History", result.getName());
        Assert.assertEquals(3, result.getId());
    }

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getByNameIgnoreCaseTest() {
        GenreSql result = genreSqlDao.findByNameIgnoreCase("HiStOry").get();

        Assert.assertEquals("History", result.getName());
        Assert.assertEquals(3, result.getId());
    }

}
