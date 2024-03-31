package com.joyfulgarden.model;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorsRepository extends JpaRepository<Instructors, Integer> {
	List<Instructors> findByEmail(String email);

}
