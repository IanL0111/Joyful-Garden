package com.joyfulgarden.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.joyfulgarden.model.Enrollments;
import com.joyfulgarden.model.Students;
import com.joyfulgarden.service.EnrollmentsService;

import jakarta.validation.Valid;

@Controller
public class EnrollmentsController {

    @Autowired
    private final EnrollmentsService enrollmentsService;

    @Autowired
    private JavaMailSender javaMailSender;

    public EnrollmentsController(EnrollmentsService enrollmentsService) {
        this.enrollmentsService = enrollmentsService;
    }

    @GetMapping("/enrollment-form/{courseID}")
    public String showEnrollmentForm(Model model, @PathVariable("courseID") Integer courseID,
            @RequestParam(value = "coursePrice", required = false) BigDecimal price) {
        model.addAttribute("courseID", courseID);
        model.addAttribute("coursePrice", price);
        model.addAttribute("enrollment", new Enrollments());
        return "enrollment/enrollment-form";
    }

    @PostMapping("/getEnrollmentData")
    @ResponseBody
    public Enrollments getEnrollmentData() {
        Enrollments enrollmentData = enrollmentsService.getEnrollmentsById(1);
        return enrollmentData;
    }

    @PostMapping("/enroll")
    public ModelAndView enrollStudent(@ModelAttribute("enrollment") @Valid Enrollments enrollment,
            BindingResult bindingResult, @ModelAttribute("students") Students students,
            @RequestParam("courseID") Integer courseID, @RequestParam("coursePrice") BigDecimal coursePrice) throws jakarta.mail.MessagingException {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            if (bindingResult.hasFieldErrors("students.email")) {
                modelAndView.setViewName("enrollment/enrollment-form");
                return modelAndView;
            }

            modelAndView.setViewName("enrollment/enrollment-form");
        } else {
            enrollment.setEnrollmentDate(LocalDate.now());

            Students savedStudent = enrollmentsService.getStudentByEmail(students.getEmail());
            if (savedStudent == null) {
                savedStudent = enrollmentsService.saveStudent(students);
            }

            Integer studentId = savedStudent.getStudentID();

            enrollment.setStudentID(studentId);
            enrollment.setCourseID(courseID);
            enrollment.setPaymentAmount(coursePrice);

            Optional<Enrollments> existingEnrollment = enrollmentsService.getByCourseIDAndStudentID(courseID,
                    studentId);
            if (existingEnrollment.isPresent()) {
                modelAndView.setViewName("enrollment/enrollment-form");
                modelAndView.addObject("error", "您已經報名過此課程！");
                return modelAndView;
            } else {
                enrollmentsService.saveEnrollment(enrollment);
                modelAndView.setViewName("redirect:/enrollment/enrollment-success");
            }

            try {
                sendEnrollmentConfirmationEmail(enrollment, students.getEmail());
            } catch (MessagingException e) {
                modelAndView.addObject("error", "無法發送郵件通知");
            }
        }

        return modelAndView;
    }

    private void sendEnrollmentConfirmationEmail(Enrollments enrollment, String recipientEmail) throws MessagingException, jakarta.mail.MessagingException {
        jakarta.mail.internet.MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // 設置收件人和主題
        helper.setTo(recipientEmail);
        helper.setSubject("Joyful Garden 課程報名確認通知");

        // 建立包含報名資訊的 HTML 內容
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<p>親愛的學員，您已成功報名課程</p>");
        
        // 檢查並添加課程名稱
//        String courseID = enrollment.getCourseID() != null ? enrollment.getCourseID().toString() : "未知課程";
//        htmlContent.append("<p>課程編號：" + courseID + "</p>");
        
        // 檢查並添加報名時間
        String enrollmentDate = enrollment.getEnrollmentDate() != null ? enrollment.getEnrollmentDate().toString() : "未知時間";
        htmlContent.append("<p>報名時間：" + enrollmentDate + "</p>");
        
        // 檢查並添加報名價格
        String paymentAmount = enrollment.getPaymentAmount() != null ? enrollment.getPaymentAmount().toString() : "未知金額";
        htmlContent.append("<p>報名價格：" + paymentAmount + "</p>");
        
        // 添加 Facebook 和 Instagram 的 QR Code
        htmlContent.append("<p>請掃描以下 QR Code 關注我們的社交媒體平台：</p>");
        htmlContent.append("<img src='cid:444996' alt='QRcode'  width=\"200\" height=\"200\" />"); 
       
        // 其他內容
        htmlContent.append("<p>我們將盡快與您聯繫！</p>");
        htmlContent.append("<p>期待與您的見面！</p>");
        htmlContent.append("<p>敬上，</p>");
        htmlContent.append("<p>Joyful Garden</p>");

        // 將 HTML 內容設置為郵件內容
        helper.setText(htmlContent.toString(), true);


        FileSystemResource image = new FileSystemResource(new File("src/main/resources/static/image/444996.jpg"));
        helper.addInline("444996", image);

        // 發送郵件
        javaMailSender.send(message);        
    }



    @GetMapping("/enrollment/enrollment-success")
    public String showEnrollmentSuccessPage() {
        return "enrollment/enrollment-success";
    }

    @GetMapping("/enrollment-management")
    public String showEnrollmentManagement(Model model) {
        List<Enrollments> enrollmentsList = enrollmentsService.getAllEnrollments();

        enrollmentsList.sort(Comparator.comparing(Enrollments::getEnrollmentID).reversed());

        model.addAttribute("enrollmentsList", enrollmentsList);

        for (Enrollments enrollment : enrollmentsList) {
            Optional<Enrollments> optionalEnrollment = enrollmentsService
                    .getByCourseIDAndStudentID(enrollment.getCourseID(), enrollment.getStudentID());
            optionalEnrollment.ifPresent(additionalInfoEnrollment -> {
                model.addAttribute("courseName", additionalInfoEnrollment.getCourse().getCourseName());
                model.addAttribute("studentName", additionalInfoEnrollment.getStudent().getStudentName());
            });
        }

        return "enrollment/enrollment-management";

    }



	@GetMapping("/updateEnrollment/{enrollmentID}")
	public String showUpdateEnrollmentForm(@PathVariable("enrollmentID") Integer enrollmentID, Model model) {
		Enrollments enrollment = enrollmentsService.getEnrollmentsById(enrollmentID);
		model.addAttribute("enrollment", enrollment);
		return "enrollment/enrollment-update-form";
	}

	@PostMapping("/updateEnrollment/{enrollmentID}")
	public ModelAndView updateEnrollment(@PathVariable("enrollmentID") Integer enrollmentID, ModelAndView modelAndView,
			@RequestParam("courseID") Integer courseID, @RequestParam("studentID") Integer studentID,
			@RequestParam("enrollmentDate") LocalDate enrollmentDate, @RequestParam("paymentDate") Date paymentDate,
			@RequestParam("paymentStatus") String paymentStatus,
			@RequestParam("paymentAmount") BigDecimal paymentAmount) {

		Enrollments e = enrollmentsService.getEnrollmentsById(enrollmentID);
		e.setEnrollmentDate(enrollmentDate);
		e.setPaymentDate(paymentDate);
		e.setPaymentStatus(paymentStatus);
		e.setPaymentAmount(paymentAmount);
		enrollmentsService.saveEnrollment(e);
		modelAndView.setViewName("redirect:/enrollment-management");

		return modelAndView;
	}

	@PostMapping("/deleteEnrollment")
	public String deleteEnrollment(@RequestParam(value = "enrollmentId", required = false) Integer enrollmentId,
			@RequestParam(value = "selectedEnrollments", required = false) List<Integer> selectedEnrollments) {
		if (enrollmentId != null) {
			enrollmentsService.deleteEnrollmentById(enrollmentId);
		} else if (selectedEnrollments != null && !selectedEnrollments.isEmpty()) {
			enrollmentsService.deleteSelectedEnrollments(selectedEnrollments);
		}
		return "redirect:/enrollment-management";
	}

	@PostMapping("/deleteSelectedEnrollments")
	public String deleteSelectedEnrollments(@RequestParam("selectedEnrollments") List<Integer> selectedEnrollments) {
		enrollmentsService.deleteSelectedEnrollments(selectedEnrollments);
		return "redirect:/enrollment-management";

	}

	@PostMapping("/checkStudentExistence")
	@ResponseBody
	public ResponseEntity<?> checkExistingUser(@RequestParam("email") String email) {
		Students existingStudent = enrollmentsService.getStudentByEmail(email);
		if (existingStudent != null) {
			// 如果學生已存在，返回相應的回應，並附帶學生ID
			return ResponseEntity.ok().body("existing:" + existingStudent.getStudentID());
		} else {
			// 如果學生不存在，返回相應的回應
			return ResponseEntity.ok().body("not-existing");
		}
	}

	@PostMapping("/checkDuplicateEnrollment")
	@ResponseBody
	public ResponseEntity<?> checkDuplicateEnrollment(@RequestParam("courseID") Integer courseID,
			@RequestParam("email") String email) {
		boolean isDuplicate = enrollmentsService.checkDuplicateEnrollment(courseID, email);
		if (isDuplicate) {
			// 如果存在相同課程的報名，返回相應的回應
			return ResponseEntity.ok().body("duplicate");
		} else {
			// 如果不存在相同課程的報名，返回相應的回應
			return ResponseEntity.ok().body("not-duplicate");
		}
	}

	// 根據課程名稱進行模糊搜尋
	@PostMapping("/enrollments/search")
	public String searchEnrollments(@RequestParam("searchInput") String searchInput, Model model) {
	    // 檢查 searchInput 是否為有效的課程名稱
	    if (isValidCourseName(searchInput)) {
	        // 執行模糊搜尋邏輯，並將結果放入 model 中
	        List<Enrollments> searchResults = enrollmentsService.searchCourses(searchInput);
	        model.addAttribute("enrollmentsList", searchResults);
	    } else {
	        // 處理不合法的輸入
	        model.addAttribute("error", "請輸入有效的課程名稱");
	    }

	    return "enrollment/enrollment-management";
	}

	private boolean isValidCourseName(String courseName) {
	    return courseName != null && courseName.matches(".*[\\u4e00-\\u9fa5].*");
	}


	@PostMapping("/checkCourseName")
	@ResponseBody
	public Map<String, Boolean> checkCourseName(@RequestParam("courseName") String courseName) {
		Map<String, Boolean> response = new HashMap<>();
		response.put("valid", isValidCourseName(courseName));
		return response;
	}

	@GetMapping("/exportEnrollments")
	public ResponseEntity<byte[]> exportEnrollments(@RequestParam("format") String format) {
	    List<Enrollments> enrollments = enrollmentsService.getAllEnrollments();

	    // 檢查匯出格式的有效性
	    if (!isValidExportFormat(format)) {
	        return ResponseEntity.badRequest().build();
	    }

	    // 生成匯出檔案
	    byte[] exportedData = generateExportedData(enrollments, format);

	    // 設置 HTTP 響應頭
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
	    
	    // 更改檔案的副檔名
	    String fileName = "enrollments." + format;
	    if ("excel".equalsIgnoreCase(format)) {
	        fileName = "enrollments.xlsx"; 
	    }
	    
	    headers.setContentDispositionFormData("attachment", fileName);

	    return new ResponseEntity<>(exportedData, headers, HttpStatus.OK);
	}

	// 檢查匯出格式的有效性
	private boolean isValidExportFormat(String format) {
	    return "csv".equalsIgnoreCase(format) || "excel".equalsIgnoreCase(format);
	}

	// 生成匯出檔案的邏輯，與您原來的邏輯保持一致
	private byte[] generateExportedData(List<Enrollments> enrollments, String format) {
	    // 根據所需格式生成匯出檔案
	    // 這裡僅為示例，實際邏輯需要根據需求自行實現
	    if ("csv".equalsIgnoreCase(format)) {
	        return generateCsv(enrollments);
	    } else if ("excel".equalsIgnoreCase(format)) {
	        return generateExcel(enrollments);
	    } else {
	        // 如果不支持的格式，返回空檔案
	        return new byte[0];
	    }
	}

	// 生成 CSV 檔案的邏輯，與您原來的邏輯保持一致
	private byte[] generateCsv(List<Enrollments> enrollments) {
	    // 生成 CSV 格式的匯出檔案

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    // 將 enrollments 轉換為 CSV 格式並寫入 outputStream 中

	    return outputStream.toByteArray();
	}

	// 生成 Excel 檔案的邏輯，與您原來的邏輯保持一致
	private byte[] generateExcel(List<Enrollments> enrollments) {
	    // 生成 Excel 格式的匯出檔案
	    try (XSSFWorkbook workbook = new XSSFWorkbook()) {
	        XSSFSheet sheet = workbook.createSheet("Enrollments");

	        // 創建標題列
	        XSSFRow headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("報名編號");
	        headerRow.createCell(1).setCellValue("學生姓名");
	        headerRow.createCell(2).setCellValue("課程名稱");
	        headerRow.createCell(3).setCellValue("報名日期");
	        headerRow.createCell(4).setCellValue("支付狀態");
	        headerRow.createCell(5).setCellValue("支付金額");
	        headerRow.createCell(6).setCellValue("支付日期");
	        headerRow.createCell(7).setCellValue("電子信箱");
	        headerRow.createCell(8).setCellValue("行動電話");

	        // 填充數據列
	        int rowNum = 1;
	        for (Enrollments enrollment : enrollments) {
	            XSSFRow row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(enrollment.getEnrollmentID());
	            row.createCell(1).setCellValue(enrollment.getStudent().getStudentName());
	            row.createCell(2).setCellValue(enrollment.getCourse().getCourseName());
	            row.createCell(3).setCellValue(enrollment.getEnrollmentDate().toString());
	            row.createCell(4).setCellValue(enrollment.getPaymentStatus());
	            row.createCell(5).setCellValue(enrollment.getPaymentAmount().toString());
	            
	            // 檢查 paymentDate 是否為空值
	            if (enrollment.getPaymentDate() != null) {
	                row.createCell(6).setCellValue(enrollment.getPaymentDate().toString());
	            } else {
	                row.createCell(6).setCellValue("N/A"); // 如果 paymentDate 為空值，設置為 N/A
	            }
	            
	            row.createCell(7).setCellValue(enrollment.getStudent().getEmail());
	            row.createCell(8).setCellValue(enrollment.getStudent().getContactNumber());
	        }

	        // 自動調整列寬
	        for (int i = 0; i < 9; i++) {
	            sheet.autoSizeColumn(i);
	        }

	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        workbook.write(outputStream);
	        return outputStream.toByteArray();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new byte[0];
	    }
	}


}
