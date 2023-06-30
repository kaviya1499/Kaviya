package com.atdxt.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="auth_table")
public class Auth_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;

    public Auth_Entity(String username, String decodedPassword) {
    }
}
