package com.joyfulgarden.controller;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.joyfulgarden.model.ASStudent;
import com.joyfulgarden.model.Activity;
import com.joyfulgarden.model.Student;
import com.joyfulgarden.service.ASStudentService;
import com.joyfulgarden.service.ActivityService;
import com.joyfulgarden.service.StudentService;

@Controller
public class StudentController {
	@Autowired
	private JavaMailSender javaMailSender;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ASStudentService aStudentService;
    @Autowired 
    private ActivityService activityService;
    
    // Get all students
    @GetMapping("/students")
    public List<Student> getAllStudents() {
    	
        return studentService.findAll();
    }
    
    @ResponseBody
    @PostMapping("/students/{activityId}")
    public String createStudent(@PathVariable("activityId") Integer activityId,@RequestParam("name") String name ,@RequestParam("email") String email,@RequestParam ("address")String address,@RequestParam("phone") String phone) {
        
        Student student = new Student(name, email, address, phone);

        ASStudent asStudent = new ASStudent();
        Activity activity =  activityService.findById(activityId);
  
       
        asStudent.setActivity(activity);
        asStudent.setStudent(student);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());
        asStudent.setRegisterDate(currentDate);
        
        
        
        //asStudent.setRegisterDate(new Date()); // 設置當前日期

        activity.getAsStudents().add(asStudent);
        student.getAsStudents().add(asStudent);
        
        studentService.insert(student);
        if(student.getEmail()!=null) {
        	SimpleMailMessage message = new SimpleMailMessage();
        	message.setTo(student.getEmail());
        	message.setSubject("Joyful Garden 手作美學 報名通知");
        	message.setText("報名詳細資料:\n" +"報名姓名："+ student.getName()+ "\n"+"電子信箱："+student.getEmail()+ "\n"+"地址："+student.getAddress()+ 
        			"\n"+"聯絡電話："+student.getPhone()+ "\n"+"活動日期："+activity.getActivityDate()+"\n"+"活動地點："+activity.getActivityLocation());
        	javaMailSender.send(message);
        }else {
        	System.out.println("not found mail");
        }

        //這個要最後才存
        aStudentService.insert(asStudent);

        return "success";
    }
    
    
    @GetMapping("/sign/{activityId}")
    
    public String signActivity(@PathVariable("activityId") int activityId,Model model) {
    	
    	model.addAttribute("ActivityId", activityId);
    	
    	return"web/ActivitySign";
    }
//    撈報名資料
    @GetMapping("/xxx")
    public String studentContent(Model model) {
    	
    	List<ASStudent> asStudents = aStudentService.findAll();
    
    	model.addAttribute("asStudents",asStudents);

    	return"web/ASStudent";
    }
    
    
    
    // Get a student by ID
    @ResponseBody
    @GetMapping("/students/{studentId}")
    public List<Student> getStudentById(@PathVariable int studentId) {
        return studentService.findByStudentID(studentId);
    }
    
    @DeleteMapping("/students/{studentId}")
    
    public String deleteStudent(@PathVariable int studentId) {
        studentService.deleteOne(studentId);
        return "redirect:/xxx";
    }
//    // Delete a student
//    @DeleteMapping("/students/{studentId}")
//    @ResponseBody
//    public String deleteStudent(@PathVariable int studentId) {
//    	studentService.deleteOne(studentId);
//    	return "delete OK!";
//    }

     
   }

