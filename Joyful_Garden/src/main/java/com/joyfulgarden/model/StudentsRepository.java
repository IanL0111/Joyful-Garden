package com.joyfulgarden.model;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.validation.Valid;

@Repository
public interface StudentsRepository extends JpaRepository<Students, Integer> {
	

	Courses save(@Valid Integer courseID);
	Students findByEmail(String email);

}
