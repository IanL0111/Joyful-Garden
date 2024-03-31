package com.joyfulgarden.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Instructors")
public class Instructors {

    @Id
    @Column(name = "INSTRUCTORID")
    private Integer instructorID;

    @NotEmpty
    @Column(name = "INSTRUCTORNAME", nullable = false, length = 50)
    private String instructorName;

    @Column(name = "CONTACTNUMBER", length = 15)
    private String contactNumber;

    @NotEmpty
    @Column(name = "EMAIL", unique = true, nullable = false, length = 100)
    private String email;

    // Getter and Setter methods

    public Integer getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(Integer instructorID) {
        this.instructorID = instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
