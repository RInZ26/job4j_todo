package ru.job4j.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class JUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Column(unique = true)
    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;
}
