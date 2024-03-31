package com.joyfulgarden.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Enrollments")
public class Enrollments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ENROLLMENTID")
    private Integer enrollmentID;

    @Column(name = "STUDENTID")
    private Integer studentID;

    @Column(name = "COURSEID")
    private Integer courseID;

    @Column(name = "ENROLLMENTDATE", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "PAYMENTSTATUS", length = 50)
    private String paymentStatus;

    @Column(name = "PAYMENTAMOUNT", precision = 10, scale = 2)
    private BigDecimal paymentAmount;

    @Column(name = "PAYMENTDATE")
    private Date paymentDate;

    @ManyToOne
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID", insertable = false, updatable = false)
    private Students students;

    @ManyToOne
    @JoinColumn(name = "COURSEID", referencedColumnName = "COURSEID", insertable = false, updatable = false)
    private Courses courses;

    // Getter and Setter methods
    
    public Integer getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(Integer enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public Integer setCourseID(Integer courseID) {
        return this.courseID = courseID;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate localDate) {
        this.enrollmentDate = localDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Students getStudent() {
        return students;
    }

    public void setStudent(Students students) {
        this.students = students;
    }

    public Courses getCourse() {
        return courses;
    }

    public void setCourse(Courses courses) {
        this.courses = courses;
    }

}
