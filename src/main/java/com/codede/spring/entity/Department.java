package com.codede.spring.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", columnDefinition="text", length=10485760)
    private  String name;

    @Column(name = "location")
    private String location;

    @Column(name = "country")
    private String country;
}
