package com.jpa.springjpa.orphanRemoval;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CartItem> items;

    public Cart(String username, List<CartItem> items) {
        this.items = items;
        this.username = username;
    }
}
