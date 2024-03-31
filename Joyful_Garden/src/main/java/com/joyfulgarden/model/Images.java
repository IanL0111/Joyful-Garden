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
@Table(name = "Images")
public class Images {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="IMAGESID")
	private int imagesID;
	@Column(name="IMAGESPATH")
	private String imagesPath;
	
	@ManyToOne
    @JoinColumn(name = "ACTIVITYID", unique = true)
    private Activity activity;
	
	
	
	public Images() {
		
	}
	
	
	public int getImagesID() {
		return imagesID;
	}

	public void setImagesID(int imagesID) {
		this.imagesID = imagesID;
	}

	
	public String getImagesPath() {
		return imagesPath;
	}

	public void setImagesPath(String imagesPath) {
		this.imagesPath = imagesPath;
	}


	public Activity getActivity() {
		return activity;
	}


	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	

	
}
