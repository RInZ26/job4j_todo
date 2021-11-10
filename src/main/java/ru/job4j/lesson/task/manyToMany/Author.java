package ru.job4j.lesson.task.manyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "authors")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Setter
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Getter
    @Setter
    List<Book> books = new ArrayList<>();

    public static Author of(String name) {
        Author author = new Author();
        author.setName(name);
        return author;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
