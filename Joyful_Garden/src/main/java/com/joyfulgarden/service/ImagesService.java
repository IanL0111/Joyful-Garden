package com.joyfulgarden.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joyfulgarden.model.Activity;
import com.joyfulgarden.model.Images;
import com.joyfulgarden.model.ImagesRepository;

@Service
public class ImagesService {
	@Autowired
	private ImagesRepository imagesRepository;
	
	
	public List<Images> findAll(){
        return imagesRepository.findAll();
    }
	
	public Images insert(Images images){
	        return imagesRepository.save(images);
    }
	 
	public List<Images> findByActivityID (Activity activityId) {
        return imagesRepository.findByActivity(activityId);
    }
	
	public void deleteOne(int imagesId){
       imagesRepository.deleteById(imagesId);
    }
	
	public Images update(Images images) {
    	return imagesRepository.save(images);
    }
    
}
