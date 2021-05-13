package ru.otus.spring.domainmongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookMongo {

    @Id
    private String id;

    private String name;

    @DBRef
    @Field(name = "author")
    private AuthorMongo author;

    @DBRef
    @Field(name = "genre")
    private GenreMongo genre;

    private List<CommentMongo> comments = new ArrayList<>();

    public BookMongo(String name, AuthorMongo author, GenreMongo genre) {
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public BookMongo(String name) {
        this.name = name;
    }

    public BookMongo(String id, String name, AuthorMongo author, GenreMongo genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorMongo getAuthor() {
        return author;
    }

    public void setAuthor(AuthorMongo author) {
        this.author = author;
    }

    public GenreMongo getGenre() {
        return genre;
    }

    public void setGenre(GenreMongo genre) {
        this.genre = genre;
    }

    public List<CommentMongo> getComments() {
        return this.comments;
    }

    public void deleteCommentById(String id){
        this.getComments().removeIf(comm -> id.equals(comm.getId()));
    }

    public void addComment(CommentMongo comment) {
        this.comments.add(comment);
    }

    @Override
    public String toString() {
        return "Book{ID=" + id + ";" + "NAME=" + name + ";}";
    }
}
