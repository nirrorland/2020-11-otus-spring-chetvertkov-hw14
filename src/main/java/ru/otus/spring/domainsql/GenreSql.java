package ru.otus.spring.domainsql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="genre_id", unique = true, nullable = false)
    private long id;

    @Column(name ="genre_name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GenreSql(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
