package com.jpa.springjpa.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "course")
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Course {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String code;
    private String name;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
