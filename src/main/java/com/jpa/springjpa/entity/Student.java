package com.jpa.springjpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "Student_table")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@DynamicUpdate
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private double cgpa;
}
