package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.daosql.BookSqlDao;
import ru.otus.spring.domainmongo.AuthorMongo;
import ru.otus.spring.domainmongo.BookMongo;
import ru.otus.spring.domainmongo.CommentMongo;
import ru.otus.spring.domainmongo.GenreMongo;
import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;
import ru.otus.spring.domainsql.GenreSql;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MigrationServiceImpl implements MigrationService {

    private final BookSqlDao bookSqlRepository;

    @Override
    public AuthorMongo authorToMongo(AuthorSql authorSql) {
        return new AuthorMongo(String.valueOf(authorSql.getId()), authorSql.getName());
    }

    @Override
    public GenreMongo genreToMongo(GenreSql genreSql) {
        return new GenreMongo(String.valueOf(genreSql.getId()), genreSql.getName());
    }


    @Override
    public BookMongo bookToMongo(BookSql bookSql) {

        AuthorMongo author = authorToMongo(bookSql.getAuthorSql());
        GenreMongo genre = genreToMongo(bookSql.getGenreSql());

        List<CommentMongo> comments = new ArrayList<>();
        for (CommentSql comment : bookSql.getCommentSqls()) {
            comments.add(commentToMongo(comment));
        }

        return new BookMongo(String.valueOf(bookSql.getId()), bookSql.getName(), author, genre, comments);
    }

    @Override
    public CommentMongo commentToMongo(CommentSql commentSql) {
        return new CommentMongo(String.valueOf(commentSql.getId()), commentSql.getText());
    }

}
