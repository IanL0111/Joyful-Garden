package com.joyfulgarden.model;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {
	
	@Query(value = "SELECT a FROM Activity a left JOIN a.images i")
	List<Activity> findAllActivityAndImage();

}
