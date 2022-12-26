package com.codede.spring.entity;

import lombok.Data;
import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Data
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "age")
    private int age;

    @Column(name = "address")
    private String address;

    @Column(name = "departmentid")
    private int departmentId;
}
