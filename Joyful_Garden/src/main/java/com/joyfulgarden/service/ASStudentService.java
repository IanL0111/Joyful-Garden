package com.joyfulgarden.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyfulgarden.model.ASStudent;
import com.joyfulgarden.model.ASStudentRepository;


@Service
public class ASStudentService {
	@Autowired
	private ASStudentRepository	asStudentRepository;
	
	
	public List<ASStudent> findAll(){
		return asStudentRepository.findAll();
	}
	
	public ASStudent insert(ASStudent asStudent) {
		return asStudentRepository.save(asStudent);
	}
	
	public void deleteOne(int ASStudentsId) {
		asStudentRepository.deleteById(ASStudentsId);
	}
	
	public ASStudent update(ASStudent asStudent) {
		return asStudentRepository.save(asStudent);
	}
}
