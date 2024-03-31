package com.joyfulgarden.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.joyfulgarden.model.Courses;
import com.joyfulgarden.model.CoursesRepository;

@Service
public class CoursesService {

	@Autowired
	private final CoursesRepository coursesRepository;

	public CoursesService(CoursesRepository coursesRepository) {
		this.coursesRepository = coursesRepository;
	}

	public List<Courses> getAllCourses() {
		return coursesRepository.findAll();
	}

	public Courses getCourseById(Integer courseID) {
		Optional<Courses> op = coursesRepository.findById(courseID);
		if (op.isPresent()) {
			return op.get();
		} else {
			return null;
		}
	}

	public Courses saveCourse(Courses courses, MultipartFile file) {
		try {
			if (file != null && !file.isEmpty()) {
				String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
				courses.setImage(base64Image);
			}
			return coursesRepository.save(courses);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("處理檔案上傳錯誤: " + e.getMessage());
		}
	}

	public void deleteCourse(Integer courseID) {
		Courses existingCourse = coursesRepository.findById(courseID).orElse(null);
		if (existingCourse != null) {
			coursesRepository.deleteById(courseID);
		} else {
			throw new RuntimeException("找不到要刪除的課程，ID: " + courseID);
		}
	}

	public void deleteSelectedCourses(List<Integer> selectedCourses) {
		List<Courses> existingCourses = coursesRepository.findAllById(selectedCourses);
		if (!existingCourses.isEmpty()) {
			coursesRepository.deleteAll(existingCourses);
			System.out.println("成功刪除多筆課程，ID: " + selectedCourses);
		} else {
			System.out.println("找不到要刪除的課程，ID: " + selectedCourses);
			throw new RuntimeException("找不到要刪除的課程，ID: " + selectedCourses);
		}
	}

	public List<Courses> searchCourses(String searchInput) {
		// 使用Repository的findByCourseNameContaining方法執行模糊搜尋
		return coursesRepository.findByCourseNameContaining(searchInput);
	}

	public String getCourseNameById(Integer courseID) {
		Optional<Courses> courseOptional = coursesRepository.findById(courseID);
		return courseOptional.map(Courses::getCourseName).orElse("未知課程");
	}
	
	public void deleteCourseById(Integer courseID) {
        // 在此添加業務邏輯，判斷是否需要刪除相關的外鍵資料
        if (needToDeleteEnrollments(courseID)) {
            // 如果需要刪除外鍵資料，先執行刪除相關的外鍵資料
            deleteEnrollmentsByCourseId(courseID);
        }
        // 刪除主鍵課程資料
        coursesRepository.deleteById(courseID);
    }

    private boolean needToDeleteEnrollments(Integer courseID) {
        // 根據業務邏輯判斷是否需要刪除相關的外鍵資料
        // 返回 true 表示需要刪除外鍵資料，返回 false 表示不需要刪除
   
        return true;
    }

    private void deleteEnrollmentsByCourseId(Integer courseID) {
        // 根據 courseId 刪除相關的外鍵資料
        // 這裡省略刪除外鍵資料的相關邏輯，開發者需要根據具體需求實現該方法
    }

	public boolean hasEnrollments(Integer id) {
	
		return false;
	}

}