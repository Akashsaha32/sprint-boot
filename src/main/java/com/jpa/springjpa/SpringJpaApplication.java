package com.jpa.springjpa;


import com.jpa.springjpa.dto.ProductSearchDTO;
import com.jpa.springjpa.entity.Course;
import com.jpa.springjpa.entity.Product;
import com.jpa.springjpa.entity.Student;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.hibernate.jpa.QueryHints;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpringJpaApplication {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
    private final static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {


        transactional((em) -> {
            var c = new Course("CSE-226", "Algorithms");
            var s = new Student("Akash Saha", 3.28, c);
            em.persist(c);
            //when we want to save s it needed c_id, if c is not saved we don't get id so exception happen.
            em.persist(s);
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

    private static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}