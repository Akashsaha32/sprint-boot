package com.jpa.springjpa.dao;

import com.jpa.springjpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDAO extends JpaRepository<Student, Integer> {
    @Query("SELECT s FROM Student_Relationship s")
    List<Student> getAll();
}
