package com.jpa.springjpa;


import com.jpa.springjpa.entity.Student;
import jakarta.persistence.*;
import org.hibernate.jpa.QueryHints;


import java.util.function.Consumer;

public class SpringJpaApplication {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
    private final static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

        transactional((em) -> {
            // N + 1 problem
            /*
            var student = em.find(Student.class, 1);
            System.out.println(student+ "\n" + student.getCourses());
            */

            //for this first get all Student and for every student_id get course
            //This is N+1 problem
            // for Student get query 1 and all N student call N query total : N+1
            /*var student = em.createQuery("Select s from Student s", Student.class)
                    .getResultList();*/

            //solution of N+1 problem
            var student = em.createQuery("Select s from Student s JOIN FETCH s.courses", Student.class)
                    .getResultList();
            System.out.println(student);
            for(var s: student) {
                System.out.println(s.getCourses());
            }
            System.out.println(student);

            //we can also use query hint and entity graph to achieve this
            /*var eg = em.createEntityGraph(Student.class);
            eg.addAttributeNodes("courses");

            var s = em.createQuery("select s from Student s", Student.class)
                    .setHint(QueryHints.JAKARTA_HINT_FETCHGRAPH, eg)
                    .getResultList();
            System.out.println(s);*/
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