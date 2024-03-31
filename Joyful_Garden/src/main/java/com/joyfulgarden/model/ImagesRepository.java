package com.joyfulgarden.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images,Integer> {
	
	List<Images> findByActivity(Activity activity);
}