package ru.otus.spring.domainsql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="book_id", unique = true, nullable = false)
    private long id;

    @Column(name ="book_name")
    private String name;

    @OneToOne(targetEntity = AuthorSql.class)
    @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    private AuthorSql authorSql;

    @OneToOne(targetEntity = GenreSql.class)
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private GenreSql genreSql;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private List<CommentSql> commentSqls;

    public BookSql(String name, AuthorSql authorSql, GenreSql genreSql) {
        this.name = name;
        this.authorSql = authorSql;
        this.genreSql = genreSql;
    }

    public BookSql(String name) {
        this.name = name;
    }

    public BookSql(long id, String name, AuthorSql authorSql, GenreSql genreSql) {
        this.id = id;
        this.name = name;
        this.authorSql = authorSql;
        this.genreSql = genreSql;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorSql getAuthorSql() {
        return authorSql;
    }

    public void setAuthorSql(AuthorSql authorSql) {
        this.authorSql = authorSql;
    }

    public GenreSql getGenreSql() {
        return genreSql;
    }

    public void setGenreSql(GenreSql genreSql) {
        this.genreSql = genreSql;
    }

    public List<CommentSql> getCommentSqls() {
        return commentSqls;
    }

    @Override
    public String toString() {
        return "Book{ID=" + id + ";" + "NAME=" + name + ";}";
    }
}
