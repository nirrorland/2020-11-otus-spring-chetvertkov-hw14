package ru.otus.spring.daomongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.domainmongo.AuthorMongo;

import java.util.Optional;

public interface AuthorMongoDao extends MongoRepository<AuthorMongo, String> {

    Optional<AuthorMongo> findByNameIgnoreCase(String name);

}
