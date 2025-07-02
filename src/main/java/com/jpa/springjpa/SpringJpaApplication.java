package com.jpa.springjpa;


import com.jpa.springjpa.entity.Student;
import jakarta.persistence.*;

import java.util.List;
import java.util.function.Consumer;

public class SpringJpaApplication {
    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("test_01");
    private final static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {

        var s2 = new Student(8, "Jack Fabric", 3.2);
        transactional((em) -> {
            var s = em.find(Student.class, 17);
            var s3 = em.getReference(Student.class, 14);
            /*System.out.println(em.contains(s));
            System.out.println(em.contains(s2));
            if(em.contains(s)){
                em.remove(s);
            }*/
            //s.setName("Akash");

            //System.out.println(s);
            //System.out.println(s3);  //it will get a proxy object, If needed then query called

            //jpql -> Java Persistence Query Language

            var s4 = em.find(Student.class, 4);
            var s5 = em.find(Student.class, 17); //for s, s4, s5 should be called 3 select query but called 2
                                                            // cause 17 is in already persistence context
                                                            // but if we need intermeadate quary we can refresh it
            System.out.println(s);
            sleep(2000);

            em.refresh(s);
            s = em.find(Student.class, 17);
            System.out.println(s);

            /*
                for large data set we can first data in db cached it is not permanent
                after commit permanently save data to db
                always use clear() with flush() other it should have to check save data is in or not
            */
            em.flush();
            em.clear();

            // createQuery
            Query q = em.createQuery("select s from Student s where s.id=1");
            Query q1 = em.createQuery("select s from Student s where s.id=?1");
            q1.setParameter(1, 2);
            Query q2 = em.createQuery("select s from Student s where s.id=:stu_id");
            q2.setParameter("stu_id", 3);

            System.out.println(q.getSingleResult());
            System.out.println(q1.getSingleResult());
            System.out.println(q2.getSingleResult());

            System.out.println("printling all students\n______________________________");

            /*TypedQuery<Student> q3 = em.createQuery("select s from Student s", Student.class);

            //q3.setFirstResult(2);
            q3.setMaxResults(3);

            List<Student> students = q3.getResultList();
            students.forEach(System.out::println);
            */
            em.createQuery("select s from Student s order by id asc ", Student.class)
                    .setFirstResult(2)
                    .setMaxResults(3)
                    .getResultList()
                    .forEach(System.out::println);

            System.out.println(em.getDelegate());
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