package com.joyfulgarden.utils;



import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import com.joyfulgarden.model.Activity;
import java.io.OutputStream;
import java.util.List;

public class ExcelExportUtility {

    public static void exportActivitiesToExcel(OutputStream outputStream, String sheetName, List<Activity> activities) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet(sheetName);

            // 创建表头
            XSSFRow headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Activity ID");
            headerRow.createCell(1).setCellValue("Activity Name");
            headerRow.createCell(2).setCellValue("Activity Date");
            headerRow.createCell(3).setCellValue("Activity Location");
            headerRow.createCell(4).setCellValue("Activity Content");

            // 填充数据
            int rowNum = 1;
            for (Activity activity : activities) {
                XSSFRow row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(activity.getActivityId());
                row.createCell(1).setCellValue(activity.getActivityName());
                row.createCell(2).setCellValue(activity.getActivityDate());
                row.createCell(3).setCellValue(activity.getActivityLocation());
                row.createCell(4).setCellValue(activity.getActivityContent());
            }

            // 将工作簿写入输出流
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
