package ru.otus.spring.dao;

import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;
import java.util.List;

@DataJpaTest
//@Import(GenreDao.class)
public class GenreDaoJpaTest {
    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getByIdTest() {
        Assert.assertEquals(genreDao.findById(1).get().getName(), "Drama");
    }

    @Test
    @DisplayName("getById = null, если ничего не найдено")
    void getByIdNotFoundTest() {
        Assert.assertFalse(genreDao.findById(6).isPresent());
    }

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getAllTest() {
        List<Genre> result = genreDao.findAll();

        Assert.assertEquals(result.size(), 3);
        Assert.assertEquals(result.get(0).getName(), "Drama");
        Assert.assertEquals(result.get(1).getName(), "Comics");
        Assert.assertEquals(result.get(2).getName(), "History");
    }

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getByNameTest() {
        Genre result = genreDao.findByNameIgnoreCase("History").get();

        Assert.assertEquals("History", result.getName());
        Assert.assertEquals(3, result.getId());
    }

    @Test
    @DisplayName("getById получает нужный экземпляр по id")
    void getByNameIgnoreCaseTest() {
        Genre result = genreDao.findByNameIgnoreCase("HiStOry").get();

        Assert.assertEquals("History", result.getName());
        Assert.assertEquals(3, result.getId());
    }

}
