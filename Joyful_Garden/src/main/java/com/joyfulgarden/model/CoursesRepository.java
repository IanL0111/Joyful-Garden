package com.joyfulgarden.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {
	/**
	 * 通過課程名稱進行精確查詢
	 *
	 * @param courseName 課程名稱
	 * @return 符合條件的課程列表
	 */
	List<Courses> findByCourseName(String courseName);
	/**
	 * 通過包含指定關鍵字的課程名稱進行模糊查詢
	 *
	 * @param searchInput 搜尋關鍵字
	 * @return 符合條件的課程列表
	 */
	List<Courses> findByCourseNameContaining(String searchInput);

	/**
	 * 通過課程編號查詢課程
	 *
	 * @param courseID 課程編號
	 * @return 符合條件的課程
	 */
	Optional<Courses> findByCourseID(Integer courseID);

	
}
