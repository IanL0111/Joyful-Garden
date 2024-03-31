package com.joyfulgarden.controller;
import java.io.ByteArrayOutputStream;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.joyfulgarden.model.ASStudent;
import com.joyfulgarden.model.Activity;
import com.joyfulgarden.model.Images;
import com.joyfulgarden.service.ASStudentService;
import com.joyfulgarden.service.ActivityService;
import com.joyfulgarden.service.ImagesService;
import com.joyfulgarden.utils.ExcelExportUtility;
import com.joyfulgarden.utils.ExcelExportUtility2;


@Controller

public class ActivityController {
	@Autowired
	private ActivityService activityService;
	@Autowired
	private ImagesService imagesService;
	@Autowired
    private ASStudentService asStudentService;

	@GetMapping("/activity")
	public String activity(Model model) {
		List<Activity> activity = activityService.findAI();
		

		model.addAttribute("activity", activity);

		return "web/Activity";
	}
	
	@GetMapping("/activity2")
	public String activity2(Model model) {
		List<Activity> activitys = activityService.findAI();
		
		for(Activity a : activitys) {
			String c = a.getActivityContent().substring(0,29);
			c += "...";
			a.setActivityContent(c);
		}

		model.addAttribute("activity", activitys);

		return "web/ActivityFrontend";
	}

	@GetMapping("/getImages/{activityId}")
	@ResponseBody
	public List<Images> getImages(@PathVariable Activity activityId, Model model) {
		List<Images> images = imagesService.findByActivityID(activityId);
		return images;
	}

	@ResponseBody
	@PostMapping("/web/activity")
	public ModelAndView processInsert(@RequestParam("activityName") String activityName,
	                            @RequestParam("activityDate") String activityDate,
	                            @RequestParam("activityLocation") String activityLocation,
	                            @RequestParam("activityContent") String activityContent,
	                            @RequestParam("activityImage") MultipartFile[] activityImages,ModelAndView modelAndView) throws IOException {

	    Activity newActivity = new Activity(activityName, activityDate, activityLocation, activityContent);
	    int activityId = activityService.insert(newActivity).getActivityId();

	    for (MultipartFile image : activityImages) {
	        byte[] imgContent = image.getBytes();
	        String mimeType = image.getContentType();
	        String base64String = "data:%s;base64,".formatted(mimeType) + Base64.getEncoder().encodeToString(imgContent);

	        Images newImage = new Images();
	        // 获取具有给定ID的活动对象
	        Activity activity = activityService.findById(activityId);
	        newImage.setActivity(activity);
	        newImage.setImagesPath(base64String);

	        imagesService.insert(newImage);
	        
	        modelAndView.setViewName("redirect:/activity");
	    }
	    return modelAndView;
	}


	@GetMapping("/web/activity")
	public String AddActivity() {
		return "web/AddActivity";
	}

	@GetMapping("/activity/{activityId}")
	@ResponseBody
	private Activity getOne(@PathVariable int activityId) {
		Activity activity = activityService.findById(activityId);
		
		return activity;
		
	}
	@GetMapping("/act/{activityId}")

	private String getOnee(@PathVariable("activityId") Integer activityId ,@PathVariable("activityId") Activity activityIdd,Model model) {
		Activity activity = activityService.findById(activityId);
		model.addAttribute("activity",activity);
		List<Images> Images = imagesService.findByActivityID(activityIdd);
		model.addAttribute("Images",Images);

		
		return "web/ActivityContent";
		
	}

//    @ResponseBody
//    @PostMapping("/activity/{activityId}")
//    public String update  (@PathVariable int activityId,
//    					   @RequestParam String activityName,
//                           @RequestParam String activityDate,
//                           @RequestParam String activityLocation,
//                           @RequestParam String activityContent
//                           ){
//        Activity activity = new Activity();
//        System.out.println(activityId);
//        activity.setActivityId(activityId);
//        activity.setActivityName(activityName);
//        activity.setActivityDate(activityDate);
//        activity.setActivityLocation(activityLocation);
//        activity.setActivityContent(activityContent);
//        activityService.update(activity);
//        
//        return "update success!";
//    }
	@ResponseBody
	@PostMapping("/activity/{activityId}")
	public String update(@PathVariable int activityId, @RequestParam String activityName,
			@RequestParam String activityDate, @RequestParam String activityLocation,
			@RequestParam String activityContent, @RequestParam List<MultipartFile> activityImageList) throws IOException {
		System.out.println("10238102938");

		Activity activity = activityService.findById(activityId);

		activity.setActivityId(activityId);
		activity.setActivityName(activityName);
		activity.setActivityDate(activityDate);
		activity.setActivityLocation(activityLocation);
		activity.setActivityContent(activityContent);

		
		List<Images> originImagesList = activity.getImages();
		activityService.update(activity);

		if ( CollectionUtils.isNotEmpty(activityImageList)) {
			
			activityImageList.forEach(c-> {

				Images newImage = new Images();
				
				byte[] imgContent = null;
				try {
					imgContent = c.getBytes();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				String mimeType = c.getContentType();
				String base64String = "";
				if(imgContent != null) {
					 base64String = "data:%s;base64,".formatted(mimeType) + Base64.getEncoder().encodeToString(imgContent);
				}

				newImage.setImagesPath(base64String);
				newImage.setActivity(activity);
				imagesService.update(newImage);

				originImagesList.add(newImage);
			});
							

		} else {}

		System.out.println("success!!");

		return "update success!";
	}

	@ResponseBody
	@PutMapping("/activity/{activityId}")
	public String update(@PathVariable int activityId, @RequestParam String activityName,
			@RequestParam String activityDate, @RequestParam String activityLocation,
			@RequestParam String activityContent) {
		System.out.println("imm in backend put");

		System.out.println(activityName);
		Activity activity = activityService.findById(activityId);

		activity.setActivityId(activityId);
		activity.setActivityName(activityName);
		activity.setActivityDate(activityDate);
		activity.setActivityLocation(activityLocation);
		activity.setActivityContent(activityContent);
		activityService.update(activity);

		System.out.println(activity);

//        List<Images> newImage = imagesService.findById(activity);
//        newImage[0].setImagesPath(activityImage);
//        imagesService.update(newImage);

		return "update success!";
	}

	@GetMapping("/activity/activityCompiler/{activityId}")
	public String ActivityCompiler(@PathVariable("activityId") int activityId,
			@PathVariable("activityId") Activity activityId1, Model model) {
		model.addAttribute("act", activityService.findById(activityId));
		model.addAttribute("images", imagesService.findByActivityID(activityId1));
		return "web/CompilerActivity";
	}

	@DeleteMapping("/activity/{activityId}")
	@ResponseBody
	public String deleteOne(@PathVariable int activityId) {
		activityService.deleteOne(activityId);
		return "delete OK!";
	}
	
	@PostMapping("/exportActivities")
	@ResponseBody
    public ResponseEntity<byte[]> exportActivitiesToExcel() {
        try {
            List<Activity> activities = activityService.findAll();
            String sheetName = "ActivityDetails";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ExcelExportUtility.exportActivitiesToExcel(outputStream, sheetName, activities);

            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "Employee.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @PostMapping("/exportASStudents")
    @ResponseBody
    public ResponseEntity<byte[]> exportASStudentsToExcel() {
        try {
            List<ASStudent> asStudents = asStudentService.findAll(); // Assuming findAll() fetches all ASStudents
            String sheetName = "ASStudentsDetails";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ExcelExportUtility2.exportActivitiesToExcel(outputStream, sheetName, asStudents);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "ASStudentsDetails.xlsx");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    
    
    
    
    
	
	@GetMapping("/activityContent")
    public String getActivityContentPage() {
        return "web/ActivityContent";
    }
	
	@GetMapping("web/{activityId}")
    public String getActivityById(@PathVariable Integer activityId, Model model) {      
        Activity activity = activityService.findById(activityId);      
        model.addAttribute("activity", activity);
        return "web/activityContent"; 
    }
	
	
}
