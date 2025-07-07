package com.jpa.springjpa;


import com.jpa.springjpa.entity.Student;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.hibernate.jpa.QueryHints;


import java.util.function.Consumer;

public class SpringJpaApplication {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
    private final static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

        transactional((em) -> {
            // need to customization or category wise search
            /*CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Student> cq = cb.createQuery(Student.class);
            Root<Student> root = cq.from(Student.class);
            root.fetch("courses", JoinType.LEFT);
            cq.where(cb.equal(root.get("name"), "Akash Saha"));
            //cq.where(cb.equal(root.get("courses").get("code"), "CSE-231"));

            TypedQuery<Student> query = em.createQuery(cq);
            query.getResultStream().forEach(System.out::println);
             */
            CriteriaBuilder cb = em.getCriteriaBuilder();
            //CriteriaQuery<String> cq = cb.createQuery(String.class);
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
            Root<Student> root = cq.from(Student.class);
            //root.fetch("courses", JoinType.LEFT);
            cq.multiselect(root.get("name"), root.get("cgpa"));
            cq.where(cb.equal(root.get("id"), 1));
            //cq.where(cb.equal(root.get("name"), "Akash Saha"));
            //cq.where(cb.equal(root.get("courses").get("code"), "CSE-231"));

            TypedQuery<Object[]> query = em.createQuery(cq);
            query.getResultStream().forEach((a) -> {
                System.out.println(a[0]+", "+a[1]);
            });
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