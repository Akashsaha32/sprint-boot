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
@ToString(exclude = {"courses"})
@Getter
@Setter
@DynamicUpdate
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double cgpa;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    List<Course> courses;
}
