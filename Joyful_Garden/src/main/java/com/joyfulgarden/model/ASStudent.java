package com.joyfulgarden.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

	@Entity
	@Table(name="Activity_Student")
	public class ASStudent {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ASSTUDENTSID")
	private int ASStudentsId;
	@ManyToOne
	@JoinColumn(name="STUDENTID",insertable=true, updatable=true)
	private Student student;
	@ManyToOne
	@JoinColumn(name="ACTIVITYID",insertable=true, updatable=true)
	private Activity activity;
	@Column(name="REGISTERDATE")
    private String registerDate;
	
	
	
	public ASStudent() {
		
	}
	
	

	public ASStudent(Student student, Activity activity, String registerDate) {
		this.student = student;
		this.activity = activity;
		this.registerDate = registerDate;
	}



	public int getASStudentsId() {
		return ASStudentsId;
	}

	public void setASStudentsId(int aSStudentsId) {
		ASStudentsId = aSStudentsId;
	}	
	
	public String getRegisterDate() {
		return registerDate;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	
	
}
