package com.joyfulgarden.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentsRepository extends JpaRepository<Enrollments, Integer> {
	List<Enrollments> findByPaymentStatus(String paymentStatus);

	@Query("SELECT e FROM Enrollments e WHERE e.courseID = :courseID AND e.studentID = :studentID")
	Optional<Enrollments> findByCourseIDAndStudentID(@Param("courseID") Integer courseID,
			@Param("studentID") Integer studentID);

	List<Enrollments> findByCoursesCourseNameContaining(String searchInput);

	
	

}
