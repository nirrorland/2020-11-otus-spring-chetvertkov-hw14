package ru.otus.spring.domainmongo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentMongo {

    private String id;

    private String text;

    public CommentMongo(String text) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{ID=" + id + ";" + "Text=" + text + ";}";
    }
}
