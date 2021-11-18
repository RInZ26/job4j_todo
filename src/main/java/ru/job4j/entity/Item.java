package ru.job4j.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Getter
    @Setter
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private JUser user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Getter
    @Setter
    private Set<Category> categories = new HashSet<>();

    public static Item of(String description) {
        Item item = new Item();
        item.setDescription(description);
        item.setCreated(new Date(System.currentTimeMillis()));
        return item;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}

