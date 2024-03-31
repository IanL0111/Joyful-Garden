package com.joyfulgarden.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joyfulgarden.model.Student;
import com.joyfulgarden.model.StudentRepository;

@Service
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;
	
	public List<Student> findAll(){
        return studentRepository.findAll();
    }
	
	public Student insert(Student student){
	        return studentRepository.save(student);
    }
	 
	public List<Student> findByStudentID (int studentId) {
        return studentRepository.findByStudentId(studentId);
    }
	
	public void deleteOne(int studentId){
       studentRepository.deleteById(studentId);
    }
	
	public Student update(Student student) {
    	return studentRepository.save(student);
    }
    
}


