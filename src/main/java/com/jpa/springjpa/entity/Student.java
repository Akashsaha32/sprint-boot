package com.jpa.springjpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Student_table")
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
    *   When we use Course object to save student it will check before save student it will
    * save course
    * that's why it is called Cascade Relationship
    * There are 5 cascade type
    */
    @OneToOne(cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    private Course course;

    public Student(String name, double cgpa, Course course) {
        this.name = name;
        this.cgpa = cgpa;
        this.course = course;
    }
}

// DDL
// DML
// Application Layer