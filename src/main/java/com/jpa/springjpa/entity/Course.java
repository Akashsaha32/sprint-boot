package com.jpa.springjpa.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


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
}
