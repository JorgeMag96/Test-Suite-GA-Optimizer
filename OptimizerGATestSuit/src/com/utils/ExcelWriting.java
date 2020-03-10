package com.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelWriting {

	private static Workbook wb;
	private static Sheet sh;
	private static FileInputStream fis;
	private static FileOutputStream fos;
	private static Row row;
	private static Cell cell;
	
	public static Sheet getSheet(String sheetName) throws Exception{
		fis = new FileInputStream("results/ExcelResults.xlsx");
		wb = WorkbookFactory.create(fis);
		return wb.getSheet(sheetName);
	}
	
	public static void writeData(Sheet sh, List<Object> data) {
		try {
			
			// Clear content of sheet
			sh.forEach(e -> {
				sh.removeRow(e);
			});
			
			
			fos = new FileOutputStream("results/ExcelResults.xlsx");
			
			wb.write(fos);
			fos.flush();
			fos.close();
			System.out.println("Done");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			//initializeWorkBook();
			fis = new FileInputStream("results/ExcelResults.xlsx");
			wb = WorkbookFactory.create(fis);
			sh = wb.getSheet("Sheet1");
			
			int numRows = sh.getLastRowNum();
			
			System.out.println("Number of rows = "+numRows);
			
			row = sh.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue("Test Value");
			
			System.out.println(cell.getStringCellValue());
			fos = new FileOutputStream("results/ExcelResults.xlsx");
			
			wb.write(fos);
			fos.flush();
			fos.close();
			System.out.println("Done");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
