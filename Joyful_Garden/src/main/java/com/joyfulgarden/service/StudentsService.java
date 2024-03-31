package com.joyfulgarden.service;

import com.joyfulgarden.model.Students;
import com.joyfulgarden.model.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentsService {
	
    @Autowired
    private final StudentsRepository studentsRepository;

  
    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public List<Students> getAllStudents() {
        return studentsRepository.findAll();
    }

    public Optional<Students> getStudentById(Integer studentID) {
        return studentsRepository.findById(studentID);
    }

    public Students saveStudent(Students student) {
        return studentsRepository.save(student);
    }

    public void deleteStudent(Integer studentID) {
        studentsRepository.deleteById(studentID);
    }
    
    public String getStudentNameById(Integer studentID) {
        Optional<Students> studentOptional = studentsRepository.findById(studentID);
        return studentOptional.map(Students::getStudentName).orElse("未知學生");
    }
}
