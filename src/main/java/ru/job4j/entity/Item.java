package ru.job4j.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Timestamp created;

    @Getter
    @Setter
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private JUser user;
}

