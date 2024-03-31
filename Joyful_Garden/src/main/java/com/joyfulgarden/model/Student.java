package com.joyfulgarden.model;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student")
@Component
public class Student {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name="STUDENTID")
	 private int studentId;
	 @Column(name="NAME")
	 private String name;
	 @Column(name="EMAIL")
	 private String email;
	 @Column(name="ADDRESS")
	 private String address;
	 @Column(name="PHONE")
	 private String phone;
	
	 
	 @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<ASStudent> asStudents = new ArrayList<>();
	 
	 
	 public Student (String name, String email , String address,String phone) {
	
	this.name = name;
	this.email = email;
	this.address = address;
	this.phone = phone;
} 
	 
	 
	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getStudentId() {
		return studentId;
	}


	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}

	
	

	public List<ASStudent> getAsStudents() {
		return asStudents;
	}


	public void setAsStudents(List<ASStudent> asStudents) {
		this.asStudents = asStudents;
	}


	public  Student() {
		
	}
	
	
}
