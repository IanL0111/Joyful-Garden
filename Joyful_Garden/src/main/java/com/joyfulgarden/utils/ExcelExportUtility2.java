package com.joyfulgarden.utils;



import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;

import com.joyfulgarden.model.ASStudent;

import java.io.OutputStream;
import java.util.List;

public class ExcelExportUtility2 {

    public static void exportActivitiesToExcel(OutputStream outputStream, String sheetName, List<ASStudent> asStudents) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);

            // 创建表头
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("活動內容");
            headerRow.createCell(1).setCellValue("學生內容");
            headerRow.createCell(2).setCellValue("活動日期");
            headerRow.createCell(3).setCellValue("信箱");
            headerRow.createCell(4).setCellValue("手機");

            // 填充数据
            int rowNum = 1;
            for (ASStudent asStudent : asStudents) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(asStudent.getActivity().getActivityName());;
                row.createCell(1).setCellValue(asStudent.getStudent().getName()); // 假设 Student 类有 getName() 方法
                row.createCell(2).setCellValue(asStudent.getActivity().getActivityDate());  // 假设 Activity 类有 getActivityName() 方法
                row.createCell(3).setCellValue(asStudent.getStudent().getEmail());
                row.createCell(4).setCellValue(asStudent.getStudent().getPhone()); // 假设 Activity 类有 getActivityDate() 方法
            }

            // 将工作簿写入输出流
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
