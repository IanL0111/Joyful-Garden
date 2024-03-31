package com.joyfulgarden.model;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
	
	List<Student> findByStudentId(int studentId);
}