package ru.otus.spring.service;

import ru.otus.spring.domainmongo.AuthorMongo;
import ru.otus.spring.domainmongo.BookMongo;
import ru.otus.spring.domainmongo.CommentMongo;
import ru.otus.spring.domainmongo.GenreMongo;
import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;
import ru.otus.spring.domainsql.GenreSql;

public interface MigrationService {

        AuthorMongo authorToMongo(AuthorSql authorSql);

        GenreMongo genreToMongo(GenreSql genreSql);

        BookMongo bookToMongo(BookSql bookSql);

        CommentMongo commentToMongo(CommentSql commentSql);

}
