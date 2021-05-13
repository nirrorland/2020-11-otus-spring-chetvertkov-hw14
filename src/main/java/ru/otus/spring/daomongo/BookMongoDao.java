package ru.otus.spring.daomongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domainmongo.BookMongo;

import java.util.Optional;

public interface BookMongoDao extends MongoRepository<BookMongo, String> {

    Optional<BookMongo> findByNameIgnoreCase(String name);

}
