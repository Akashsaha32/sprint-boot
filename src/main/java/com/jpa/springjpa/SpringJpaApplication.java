package com.jpa.springjpa;


import com.jpa.springjpa.dto.ProductSearchDTO;
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

        var filter = new ProductSearchDTO(
                "Mac",
                "Laptop",
                20000,
                180000,
                List.of("HP", "Samsung", "DELL", "MAC")
        );

        transactional((em) -> {

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