package ru.otus.spring.daomongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domainmongo.GenreMongo;

import java.util.Optional;

public interface GenreMongoDao extends MongoRepository<GenreMongo, String> {

    Optional<GenreMongo> findById(long id);

    Optional<GenreMongo> findByNameIgnoreCase(String name);
}
