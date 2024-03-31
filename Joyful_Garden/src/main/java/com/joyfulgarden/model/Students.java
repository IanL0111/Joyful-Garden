package com.joyfulgarden.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Students")
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STUDENTID")
    private Integer studentID;

    @NotEmpty
    @Column(name = "STUDENTNAME", nullable = false, length = 50)
    private String studentName;

    @Column(name = "CONTACTNUMBER", length = 15)
    private String contactNumber;

    @Column(name = "ADDRESS", length = 255)
    private String address;

    @NotEmpty
    @Email(message = "請輸入有效的電子郵件地址")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "請輸入有效的電子郵件地址")
    @Column(name = "EMAIL", unique = true, nullable = false, length = 100)
    private String email;

    // Getter and Setter methods

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}