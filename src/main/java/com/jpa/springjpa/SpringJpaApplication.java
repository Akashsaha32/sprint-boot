package com.jpa.springjpa;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SpringJpaApplication {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
        EntityManager em = emf.createEntityManager();
        System.out.println(em);
    }
}
