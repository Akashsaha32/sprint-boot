package com.jpa.springjpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "course")
@Setter
@Getter
@ToString
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String code;
    private String name;

    @ManyToOne
    private Student student;
}
