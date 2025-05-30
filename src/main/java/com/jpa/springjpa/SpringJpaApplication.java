package com.jpa.springjpa;


import com.jpa.springjpa.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class SpringJpaApplication {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
        EntityManager em = emf.createEntityManager();

        try {
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
        }
    }
}
