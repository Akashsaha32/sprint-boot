package com.jpa.springjpa.onotoone;

import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student_Relationship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sid;
    private String name;
    private double cgpa;

    //here Student_Relationship is owner and Address is target Entity
    /*
        Here this constrain is add with this name
        Foreign Key Constrain is if Student exist then Address of that Student can not be deleted
        If we don't want constrain then can set "ConstraintMode.NO_CONSTRAINT"

        if we want foreign key is generate a separate table we can use @JoinTable instead of @JoinColumn
     */
    @OneToOne
    //@JoinColumn(name = "address_identifier", referencedColumnName = "aid")
    @JoinTable(
        name = "stu_add",
            joinColumns = @JoinColumn(name = "stu_id"),
            inverseJoinColumns = @JoinColumn(name = "add_id")
    )
    private Address address;
}