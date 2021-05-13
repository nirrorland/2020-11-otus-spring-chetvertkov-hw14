package ru.otus.spring.domainsql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class CommentSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="comment_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id")
    private BookSql bookSql;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    public CommentSql(BookSql bookSql, String text) {
        this.bookSql = bookSql;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{ID=" + id + ";" + "Text=" + text + ";}";
    }
}
