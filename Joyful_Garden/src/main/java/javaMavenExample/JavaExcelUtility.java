package javaMavenExample;

import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JavaExcelUtility {
	
	public static void CreateExcel(String pathofExcel,String sheetname) {
		
		//create a blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		//create a sheet
		XSSFSheet sheet = workbook.createSheet(sheetname);
		
		
		//lets create a HashMap for data values
		
		Map<String, Object[]> data = new LinkedHashMap<String,Object[]>();
		
		
		//lets add some values
		data.put("1",new Object[] {"Emp_ID","Emp_Name","Emp_Designation","DOB"});
		data.put("2", new Object[]{121,"Rajendran","Manager","1970-05-01"});
		data.put("3", new Object[]{221,"SunderRajan","Senior Manager","1965-04-02"});
		data.put("4", new Object[]{352,"AllWin","Senior Consultant","1902-06-02"});
		data.put("5", new Object[]{426,"Mydeen kasim","Consultant","1994-03-07"});
		
		//lets create a dataset and wr will write a loop to write the values to excel
		
		Set<String> keySet = data.keySet();
		int rownum = 0; //row initializer
		
		for(String key:keySet) {
			//lets create a row
			XSSFRow row = sheet.createRow(rownum++);
			Object[] objArray = data.get(key);
			
			int cellnum = 0;
			
			for(Object obj:objArray) {
				
				//lets create a cell fro the corresponding rows
				XSSFCell cell = row.createCell(cellnum++);
				
				if(obj instanceof String) {
					cell.setCellValue((String)obj);
				}
				else if(obj instanceof Integer) {
					cell.setCellValue((Integer)obj);
				}
				
			}
			
		}
		
		try {
			FileOutputStream out = new FileOutputStream(pathofExcel);
			workbook.write(out);
			//close the output componnets
			out.close();
			//Lets print the output confirmation
			System.out.println("Workbook create with a sheetName :-" +sheetname+"created succesfully in the path :-"+pathofExcel);
			//close the workbook
			workbook.close();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
			
	public static void main(String[]args) {
			String pathOfExcel = System.getProperty("user.dir")+"\\ExternalFiles\\Employee.xlsx";
			String sheeName = "EmployeeDetails";
			System.out.println(pathOfExcel);
			System.out.println(sheeName);
			CreateExcel(pathOfExcel, sheeName);

	}	
}
