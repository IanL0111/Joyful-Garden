package com.joyfulgarden.service;

import com.joyfulgarden.model.Enrollments;
import com.joyfulgarden.model.EnrollmentsRepository;
import com.joyfulgarden.model.Students;
import com.joyfulgarden.model.StudentsRepository;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentsService {

	@Autowired
	private final EnrollmentsRepository enrollmentsRepository;

	@Autowired
	private final StudentsRepository studentsRepository;

	public EnrollmentsService(EnrollmentsRepository enrollmentsRepository, StudentsRepository studentsRepository) {
		this.enrollmentsRepository = enrollmentsRepository;
		this.studentsRepository = studentsRepository;
	}

	public List<Enrollments> getAllEnrollments() {
		return enrollmentsRepository.findAll();
	}

	public Enrollments getEnrollmentsById(Integer enrollmentID) {
		Optional<Enrollments> op = enrollmentsRepository.findById(enrollmentID);
		if (op.isPresent())
			return op.get();
		else
			return null;
	}

	public Enrollments saveEnrollment(Enrollments enrollment) {
		return enrollmentsRepository.save(enrollment);
	}

	public void deleteEnrollment(Integer enrollmentID) {
		enrollmentsRepository.deleteById(enrollmentID);
	}

	public Students saveStudent(@Valid Students students) {
		return studentsRepository.save(students);
	}

	public void saveStudent(@Valid Enrollments enrollment) {
		Students student = enrollment.getStudent();
		if (student != null) {
			studentsRepository.save(student);
		} else {
			System.out.println("Enrollments 對象或學生對象為空，無法保存到資料庫。");
			// 進行錯誤處理或日誌記錄，你可能需要拋出一個適當的異常
		}
	}

	public Optional<Enrollments> getByCourseIDAndStudentID(Integer courseID, Integer studentID) {
		return enrollmentsRepository.findByCourseIDAndStudentID(courseID, studentID);
	}

	public void deleteEnrollmentById(Integer enrollmentID) {
		enrollmentsRepository.deleteById(enrollmentID);
	}

	public void deleteSelectedEnrollments(List<Integer> selectedEnrollments) {
		List<Enrollments> existingEnrollments = enrollmentsRepository.findAllById(selectedEnrollments);
		if (!existingEnrollments.isEmpty()) {
			enrollmentsRepository.deleteAll(existingEnrollments);
			System.out.println("成功刪除多筆報名，ID: " + selectedEnrollments);
		} else {
			System.out.println("找不到要刪除的報名，ID: " + selectedEnrollments);
			throw new RuntimeException("找不到要刪除的報名，ID: " + selectedEnrollments);
		}

	}

	public Integer getStudentIdByEmail(String email) {
		Students existingStudent = studentsRepository.findByEmail(email);
		return existingStudent != null ? existingStudent.getStudentID() : null;
	}

	public Students getStudentByEmail(String email) {
		return studentsRepository.findByEmail(email);
	}

	public boolean checkDuplicateEnrollment(Integer courseID, String email) {
		Students student = studentsRepository.findByEmail(email);
        if (student != null) {
            Optional<Enrollments> existingEnrollment = enrollmentsRepository.findByCourseIDAndStudentID(courseID,
                    student.getStudentID());
            return existingEnrollment.isPresent();
        } else {
            return false;
        }
    
	}

	public List<Enrollments> searchCourses(String searchInput) {
		return enrollmentsRepository.findByCoursesCourseNameContaining(searchInput);
	}
	


}
