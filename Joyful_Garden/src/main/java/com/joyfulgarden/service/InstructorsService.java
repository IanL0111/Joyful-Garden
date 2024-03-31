package com.joyfulgarden.service;

import com.joyfulgarden.model.Instructors;
import com.joyfulgarden.model.InstructorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorsService {

	@Autowired
	private final InstructorsRepository instructorsRepository;

	public InstructorsService(InstructorsRepository instructorsRepository) {
		this.instructorsRepository = instructorsRepository;
	}

	public List<Instructors> getAllInstructors() {
		return instructorsRepository.findAll();
	}

	public Optional<Instructors> getInstructorById(Integer instructorID) {
		return instructorsRepository.findById(instructorID);
	}

	public Instructors saveInstructor(Instructors instructor) {
		return instructorsRepository.save(instructor);
	}

	public void deleteInstructor(Integer instructorID) {
		instructorsRepository.deleteById(instructorID);
	}
}
