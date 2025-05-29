package com.jpa.springjpa;


import com.jpa.springjpa.dao.StudentDAO;
import com.jpa.springjpa.entity.Student;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJpaApplication implements CommandLineRunner {

    @Autowired
    private EntityManager em;
    public static void main(String[] args) {
        SpringApplication.run(SpringJpaApplication.class, args);
    }

    public void run(String... args) throws Exception {
        System.out.println(em.find(Student.class, 1));
    }
}
