package com.jpa.springjpa;


import com.jpa.springjpa.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.function.Consumer;

public class SpringJpaApplication {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
    private final static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

        /*try {
            em.getTransaction().begin(); //every entitymanager creates only one transaction
            Student s = new Student();
            s.setName("Jack Tharmost");
            s.setCgpa(3.77);
            em.persist(s); // just cash data

            double ans = 1/0;

            em.getTransaction().commit(); // reflect to db
        }catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }*/

        /*
        * 1. Transient State
        * 2. Persistence State / Managed State
        * 3. detach state
        * 4. remove state
        */
        transactional((em) -> {
            Student s = new Student(null, "Mark Beniof", 3.98);

            em.persist(s);
            s.setCgpa(2.50);
            System.out.println(s);
            em.detach(s);


        });
    }

    private static void transactional(Consumer<EntityManager> consumer) {
        // @transaction annotation work like this
        em.getTransaction().begin(); //before
        try {
            consumer.accept(em);
        }catch (Exception e) {
            em.getTransaction().rollback(); //after throw
            throw new RuntimeException("Error in transaction");
        }
        em.getTransaction().commit(); //after advice
        em.close();
        emf.close();
    }
}
