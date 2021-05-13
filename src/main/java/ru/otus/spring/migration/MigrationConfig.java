package ru.otus.spring.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.daosql.AuthorSqlDao;
import ru.otus.spring.daosql.BookSqlDao;
import ru.otus.spring.daosql.CommentSqlDao;
import ru.otus.spring.daosql.GenreSqlDao;
import ru.otus.spring.domainmongo.AuthorMongo;
import ru.otus.spring.domainmongo.BookMongo;
import ru.otus.spring.domainmongo.CommentMongo;
import ru.otus.spring.domainmongo.GenreMongo;
import ru.otus.spring.domainsql.AuthorSql;
import ru.otus.spring.domainsql.BookSql;
import ru.otus.spring.domainsql.CommentSql;
import ru.otus.spring.domainsql.GenreSql;
import ru.otus.spring.service.MigrationService;

import java.util.Collections;

@EnableBatchProcessing
@Configuration
@RequiredArgsConstructor
public class MigrationConfig {

    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;
    private final MongoTemplate mongoTemplate;
    private final BookSqlDao bookSqlRepository;
    private final GenreSqlDao genreSqlRepository;
    private final AuthorSqlDao authorSqlRepository;
    private final CommentSqlDao commentSqlRepository;
    private final MigrationService migrationService;

    @Bean
    public Job jobMigration(Step stepAuthors, Step stepGenres, Step stepBooks, Step stepComments) {
        return jobBuilderFactory.get("jobMigration")
                .incrementer(new RunIdIncrementer())
                .start(stepAuthors)
                .next(stepGenres)
                .next(stepBooks)
                .next(stepComments)
                .build();
    }

    @Bean
    public Step stepComments(RepositoryItemReader<CommentSql> commentItemReader,
                             ItemProcessor<CommentSql, CommentMongo> commentProcessor,
                             ItemWriter<CommentMongo> commentWriter) {
        return stepBuilderFactory.get("stepComments")
                .<CommentSql, CommentMongo>chunk(5)
                .reader(commentItemReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step stepGenres(RepositoryItemReader<GenreSql> genreItemReader,
                           ItemProcessor<GenreSql, GenreMongo> genreProcessor,
                           ItemWriter<GenreMongo> genreWriter) {
        return stepBuilderFactory.get("stepGenres")
                .<GenreSql, GenreMongo>chunk(5)
                .reader(genreItemReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step stepBooks(RepositoryItemReader<BookSql> bookItemReader,
                          ItemProcessor<BookSql, BookMongo> bookProcessor,
                          ItemWriter<BookMongo> bookWriter) {
        return stepBuilderFactory.get("stepBooks")
                .<BookSql, BookMongo>chunk(5)
                .reader(bookItemReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public Step stepAuthors(RepositoryItemReader<AuthorSql> authorItemReader,
                            ItemProcessor<AuthorSql, AuthorMongo> authorProcessor,
                            ItemWriter<AuthorMongo> authorWriter) {
        return stepBuilderFactory.get("stepAuthors")
                .<AuthorSql, AuthorMongo>chunk(5)
                .reader(authorItemReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .allowStartIfComplete(true)
                .build();
    }


    @Bean
    public RepositoryItemReader<AuthorSql> authorItemReader() {
        return new RepositoryItemReaderBuilder<AuthorSql>()
                .repository(authorSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemReader<GenreSql> genreItemReader() {
        return new RepositoryItemReaderBuilder<GenreSql>()
                .repository(genreSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemReader<BookSql> bookItemReader() {
        return new RepositoryItemReaderBuilder<BookSql>()
                .repository(bookSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }

    @Bean
    public RepositoryItemReader<CommentSql> commentItemReader() {
        return new RepositoryItemReaderBuilder<CommentSql>()
                .repository(commentSqlRepository)
                .methodName("findAll")
                .sorts(Collections.emptyMap())
                .saveState(false)
                .build();
    }

    @Bean
    public ItemProcessor<AuthorSql, AuthorMongo> authorProcessor() {
        return migrationService::authorToMongo;
    }

    @Bean
    public ItemProcessor<GenreSql, GenreMongo> genreProcessor() {
        return migrationService::genreToMongo;
    }

    @Bean
    public ItemProcessor<BookSql, BookMongo> bookProcessor() {
        return migrationService::bookToMongo;
    }

    @Bean
    public ItemProcessor<CommentSql, CommentMongo> commentProcessor() {
        return migrationService::commentToMongo;
    }

    @Bean
    public MongoItemWriter<AuthorMongo> authorWriter() {
        return new MongoItemWriterBuilder<AuthorMongo>()
                .collection("authors")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<GenreMongo> genreWriter() {
        return new MongoItemWriterBuilder<GenreMongo>()
                .collection("genres")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<BookMongo> bookWriter() {
        return new MongoItemWriterBuilder<BookMongo>()
                .collection("books")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public MongoItemWriter<CommentMongo> commentWriter() {
        return new MongoItemWriterBuilder<CommentMongo>()
                .collection("comment")
                .template(mongoTemplate)
                .build();
    }
}
