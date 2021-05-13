package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.otus.spring.daosql.AuthorSqlDao;
import ru.otus.spring.domainsql.AuthorSql;

import javax.transaction.Transactional;
import java.util.List;

@DataJpaTest
@Transactional
public class AuthorSqlDaoJpaTest {

    @Autowired
    private AuthorSqlDao authorSqlDao;

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getByIdTest() {
        Assert.assertEquals(authorSqlDao.findById(1).get().getName(), "Tolkien");
    }

    @Test
    @DisplayName("getById получает null, если ничего не найдено")
    void getByIdNotFoundTest() {
        Assert.assertNull(authorSqlDao.findById(5).orElse(null));
    }

    @Test
    @DisplayName("getAll получает все значения")
    void getAllTest() {
        List<AuthorSql> result = authorSqlDao.findAll();

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getName(), "Tolkien");
        Assert.assertEquals(result.get(1).getName(), "Machiavelli");
        Assert.assertEquals(result.get(2).getName(), "Tolstoy");
    }

    @Test
    @DisplayName("getByName получает нужный экземпляр по name")
    void getByNameTest() {
        AuthorSql result = authorSqlDao.findByNameIgnoreCase("Machiavelli").get();

        Assert.assertEquals("Machiavelli", result.getName());
        Assert.assertEquals(2, result.getId());
    }

    @Test
    @DisplayName("getByname получает нужный экземпляр по name вне зависимости от регистра")
    void getByNameIgnoreCaseTest() {
        AuthorSql result = authorSqlDao.findByNameIgnoreCase("mAchiavELLi").get();

        Assert.assertEquals("Machiavelli", result.getName());
        Assert.assertEquals(2, result.getId());
    }
}

