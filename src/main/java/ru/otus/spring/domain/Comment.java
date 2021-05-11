package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment     {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="comment_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "text", nullable = false, unique = false)
    private String text;

    public Comment(Book book, String text) {
        this.book = book;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{ID=" + id + ";" + "Text=" + text + ";}";
    }
}
