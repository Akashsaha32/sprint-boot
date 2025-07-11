package com.jpa.springjpa.onotoone;

import jakarta.persistence.*;
import lombok.*;

//@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer aid;
    private String house;
    private Integer road;

    /*
        want to delegate ownership, I want to access this table from only student_relationship table
     */
    @OneToOne(mappedBy = "address")
    private Student_Relationship sid;

    public Address(String house, Integer road) {
        this.house = house;
        this.road = road;
    }
}
