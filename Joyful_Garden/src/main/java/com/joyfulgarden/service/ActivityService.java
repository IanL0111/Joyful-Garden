package com.joyfulgarden.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.joyfulgarden.model.Activity;
import com.joyfulgarden.model.ActivityRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> findAll(){
        return activityRepository.findAll();
    }

    public Activity insert(Activity activity){
        return activityRepository.save(activity);
    }

    public Activity findById(int activityId) {
    	Optional<Activity> optional = activityRepository.findById(activityId);
    	if (optional.isPresent())
    		return optional.get();
    	else {
			return null;
		}
    }
    

    public void deleteOne(int activityId){
        activityRepository.deleteById(activityId);
    }
    public Activity update(Activity activity) {
    	return activityRepository.save(activity);
    }
    public List<Activity> findAI() {
    	return activityRepository.findAllActivityAndImage();
    }
       
}
