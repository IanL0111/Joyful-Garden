package com.joyfulgarden.model;

import java.math.BigDecimal;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Courses")
public class Courses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COURSEID")
    private Integer courseID;

    @Column(name = "COURSENAME", nullable = false, length = 255)
    private String courseName;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "STARTDATE", nullable = false)
    private Date startDate;

    @Column(name = "INSTRUCTORID")
    private Integer instructorID;

    @Column(name = "CATEGORYID")
    private Integer categoryID;

    @Column(name = "IMAGE", columnDefinition = "VARCHAR(MAX)")
    private String image;

    @ManyToOne
    @JoinColumn(name = "INSTRUCTORID", referencedColumnName = "INSTRUCTORID", insertable = false, updatable = false)
    private Instructors instructor;

    @ManyToOne
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "CATEGORYID", insertable = false, updatable = false)
    private Categories category;

	public Integer getCourseID() {
		return courseID;
	}
	
    // Getter and Setter methods
	
	

	public void setCourseID(Integer courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getInstructorID() {
		return instructorID;
	}

	public void setInstructorID(Integer instructorID) {
		this.instructorID = instructorID;
	}

	public Integer getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(Integer categoryID) {
		this.categoryID = categoryID;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Instructors getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructors instructor) {
		this.instructor = instructor;
	}

	public Categories getCategory() {
		return category;
	}

	public void setCategory(Categories category) {
		this.category = category;
	}

	public String getBase64Image() {
		// TODO Auto-generated method stub
		return null;
	}


   
}
