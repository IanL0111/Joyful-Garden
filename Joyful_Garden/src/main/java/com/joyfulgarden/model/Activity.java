package com.joyfulgarden.model;


import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "Activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ACTIVITYID")
    private int activityId;
    @Column(name="ACTIVITYNAME")
    private String activityName;
    @Column(name="ACTIVITYDATE")
    private String activityDate;
    @Column(name="ACTIVITYLOCATION")
    private String activityLocation;
    @Column(name="ACTIVITYCONTENT")
    private String activityContent;
    
    
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images;
    
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ASStudent> asStudents;
        
    
    public List<ASStudent> getAsStudents() {
		return asStudents;
	}

	public void setAsStudents(List<ASStudent> asStudents) {
		this.asStudents = asStudents;
	}

	public Activity(){

    }
    
    public Activity (String activityName, String activityDate , String activityLocation, 
    				String activityContent) {
    	
    	this.activityName = activityName;
    	this.activityDate = activityDate;
    	this.activityLocation = activityLocation;
    	this.activityContent = activityContent;
    	
    }
    


    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityLocation() {
        return activityLocation;
    }

    public void setActivityLocation(String activityLocation) {
        this.activityLocation = activityLocation;
    }

    public String getActivityContent() {
        return activityContent;
    }

    public void setActivityContent(String activityContent) {
        this.activityContent = activityContent;
    }

	public List<Images> getImages() {
		return images;
	}

	public void setImages(List<Images> images) {
		this.images = images;
	}
}
