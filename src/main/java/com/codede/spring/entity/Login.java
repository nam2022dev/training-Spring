package com.codede.spring.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "login")
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;
    private String password;
    private String phone;
    private String role;
}
