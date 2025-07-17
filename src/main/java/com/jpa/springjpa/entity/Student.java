package com.jpa.springjpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Entity
@Table(name = "Student_table")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double cgpa;


    /*
    *   If mapping is lie in only student class it will be called unidirection
    *   if make mapping in course class also it will be called bidirectional
    *   in many to many relationship there is no owner ship
    */
    @OneToOne
    private Course course;
}

// DDL
// DML
// Application Layer