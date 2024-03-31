package com.joyfulgarden.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.joyfulgarden.model.Courses;
import com.joyfulgarden.service.CoursesService;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    private final CoursesService coursesService;
    
  
    @GetMapping("/userlist")
    public String showUserList(Model model) {

        List<Courses> userCoursesList = coursesService.getAllCourses(); // 請根據實際情況修改這行
        model.addAttribute("userCoursesList", userCoursesList); // 使用與後台管理頁面相同的模型屬性名稱
        return "courses/userlist";

    }

    public CoursesController(CoursesService coursesService) {
        this.coursesService = coursesService;
    }

    @GetMapping("/list")
    public String listCourses(Model model) {
        List<Courses> coursesList = coursesService.getAllCourses();
        model.addAttribute("coursesList", coursesList);
        return "courses/list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Courses course = new Courses();
        model.addAttribute("course", course);
        // 您可能需要為下拉選單或其他 UI 元素填充額外的資料
        return "courses/courseform";
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute("course") @Valid Courses course,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam(value = "base64Image", required = false) String base64Image,
                             Model model) {
        if (!file.isEmpty()) {
            try {
                base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                course.setImage(base64Image);
            } catch (IOException e) {
                throw new RuntimeException("檔案上傳失敗", e);
            }
        } else {
            course.setImage(base64Image);
        }

        coursesService.saveCourse(course, file);
        return "redirect:/courses/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("courseId") int courseId, Model model) {
        Courses course = coursesService.getCourseById(courseId);

        if (course.getImage() != null && !course.getImage().isEmpty()) {
            try {
                byte[] decodedImage = Base64.getDecoder().decode(course.getImage());
                String base64Image = Base64.getEncoder().encodeToString(decodedImage);
                course.setImage(base64Image);
            } catch (Exception e) {
                throw new RuntimeException("圖片解碼失敗", e);
            }
        }

        model.addAttribute("course", course);
        return "courses/courseform";
    }

    @PostMapping("/deleteCourse")
    public ResponseEntity<Map<String, String>> deleteCourse(@RequestParam(value = "courseId", required = false) Integer courseId,
                                                            @RequestParam(value = "selectedCourses", required = false) List<Integer> selectedCourses) {
        Map<String, String> response = new HashMap<>();
        if (courseId != null) {
            // 判斷是否有綁定報名資料
            if (coursesService.hasEnrollments(courseId)) {
                // 如果有綁定報名資料，返回錯誤響應
                response.put("error", "此課程仍有報名資訊，請先刪除報名資料");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                // 如果沒有綁定報名資料，執行刪除操作
                coursesService.deleteCourse(courseId);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else if (selectedCourses != null && !selectedCourses.isEmpty()) {
            // 批量刪除課程，逐一檢查是否有綁定報名資料
            for (Integer selectedCourseId : selectedCourses) {
                if (coursesService.hasEnrollments(selectedCourseId)) {
                    // 如果有綁定報名資料，返回錯誤響應
                    response.put("error", "所選課程中有課程仍有報名資訊，請先刪除報名資料");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }
            // 如果沒有綁定報名資料，執行批量刪除操作
            coursesService.deleteSelectedCourses(selectedCourses);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        // 如果請求不合法，返回錯誤響應
        response.put("error", "請提供有效的課程ID或所選課程");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }



    @PostMapping("/deleteSelectedCourses")
    public String deleteSelectedCourses(@RequestParam("selectedCourses") List<Integer> selectedCourses) {
        coursesService.deleteSelectedCourses(selectedCourses);
        return "redirect:/courses/list";
    }
    
    @PostMapping("/search")
    public String search(@RequestParam("searchInput") String searchInput, Model model) {
        // 檢查 searchInput 是否為有效的課程名稱
        if (isValidCourseName(searchInput)) {
            // 執行模糊搜尋邏輯，並將結果放入 model 中
            List<Courses> searchResults = coursesService.searchCourses(searchInput);
            model.addAttribute("coursesList", searchResults);
        } else {
            // 處理不合法的輸入
            model.addAttribute("error", "請輸入有效的課程名稱");
        }

        // 返回課程列表頁面
        return "courses/list";
    }

    // 驗證課程名稱是否合法
    private boolean isValidCourseName(String courseName) {
        // 在此加入您的驗證邏輯，例如檢查是否為空或其他規則
        // 範例：檢查課程名稱是否為空
    	return courseName != null && courseName.matches(".*[\\u4e00-\\u9fa5].*");
    }

    @PostMapping("/checkCourseName")
    @ResponseBody
    public Map<String, Boolean> checkCourseName(@RequestParam("courseName") String courseName) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", isValidCourseName(courseName));
        return response;
    }

 // 前台使用者的課程列表
    @GetMapping("/user/list")
    public String userCoursesList( Model model) {
    
        List<Courses> userCoursesList = coursesService.getAllCourses(); 
        model.addAttribute("userCoursesList", userCoursesList); // 使用與後台管理頁面相同的模型屬性名稱
        return "courses/userlist";
    }
    
    @GetMapping("/details/{courseID}")
    public String showCourseDetails(@PathVariable Integer courseID, Model model) {
        // 根據 courseId 從資料庫或其他地方獲取特定課程的詳細資訊
        Courses course = coursesService.getCourseById(courseID);

        // 將課程資訊放入 model 中，以便在 JSP 頁面中使用
        model.addAttribute("details", Collections.singletonList(course));
        
        // 返回到 details.jsp 頁面
        return "courses/details";
    }


}
